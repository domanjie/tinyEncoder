package domanjie.dev;

import domanjie.dev.encoder.Encoder;
import domanjie.dev.bmp.BitMapFileProcessor;
import domanjie.dev.encoder.SamplingFactor;
import domanjie.dev.jpeg.*;
import domanjie.dev.utils.RGBToYCbCrConverter;
import org.apache.commons.cli.*;
import org.apache.commons.cli.help.HelpFormatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        CommandLineParser cliParser= new DefaultParser();
        var options = getOptions();
        List<SamplingFactor> samplingFactors = new ArrayList<>(
                List.of(Defaults.lumaSamplingFactor,Defaults.cbSamplingFactor, Defaults.crSamplingFactor));
        try {
            var cli=  cliParser.parse(options, args);
            if(cli.hasOption("help")){
                String header = "tinyEncoder - compress an image file to a JPEG file";
                String footer = "Please report issues at https://github.com/domanjie/tinyEncoder/issues";
                var  formatter =  HelpFormatter.builder().setShowSince(false).get();
                formatter.printHelp("tinyEncoder [OPTION]... [FILE]", header,options,footer,false);
                System.exit(0);
            }
            if ( cli.getArgList().size()!=1){
                throw new ParseException("Illegal No of arguments");
            };
            if(cli.hasOption("s")){
               var samplingFactorsStr= cli.getOptionValue("s");
               if(samplingFactorsStr.matches("[1-4x1-4](,[1-4]x[1-4]){0,2}")){
                   throw new ParseException("Invalid sampling factors");
               }
               var factors= samplingFactorsStr.split(",");
               // Perform and index aware for each loop to replace
               // the default sampling factors with the ones received from the cli.
               IntStream.range(0,factors.length)
                       .forEach(i ->{
                           var factor = factors[i].split("x");
                           samplingFactors.set(i,new SamplingFactor(Integer.parseInt(factor[0]),Integer.parseInt(factor[0])));
                       });
           }
           boolean generateEntropyTables=cli.getParsedOptionValue("e",false);
           String  modeOfOperation=cli.getOptionValue("m","sequential");
           int noOfThread= cli.getParsedOptionValue("t",1);
           File file =new File(cli.getArgList().get(0));
           byte[] byteArr;
           byteArr= Files.readAllBytes(file.toPath());
           var encoder =new Encoder(noOfThread);
           var componentData= RGBToYCbCrConverter.convert(BitMapFileProcessor.bytesToBmp(byteArr).getPixelArray());
           var imageComponents = getImageComponents(componentData,samplingFactors);
           var jpegImage = encoder.encode(imageComponents);
           String outputFileName=cli.getParsedOptionValue("o",getBaseName(file.getName())+".jpg");
           jpegImage.toFile(outputFileName);
        } catch (ParseException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Options getOptions() {
        Options options= new Options();
        options.addOption("m","mode",true, "specify the mode of operation.Currently supports only sequential dct." );
        options.addOption("o", "output", true ,"name of output file.");
        options.addOption(Option.builder().type(Integer.class).option("t").longOpt("threads").hasArg(true).desc("Specify no of threads, for parallel encoding.").get());
        options.addOption("s","sample",true, "specify the sampling factor for the sub-sampling phase.");
        options.addOption(Option.builder().option("e").longOpt("generate-entropy-tables").hasArg(false).desc("""
                                                                                             Generate optimal huff-tables ignoring the defaults.
                                                                                             This may make the encoding process slower.
                                                                                             """).type(Boolean.class).get());
        options.addOption(Option.builder().longOpt("help").desc("display this help and exit.").hasArg(false).get());
        return options;
    }

    private static ImageComponent[] getImageComponents(int[][][] componentData, List<SamplingFactor> samplingFactors) {
        var yComponent =new ImageComponent(1,samplingFactors.get(0), Defaults.DEFAULT_LUMA_QUANT_TABLE, Defaults.lumaAcEncodingTable, Defaults.lumaDcEntropyEncodingTable, componentData[0]);
        var cbComponent=new ImageComponent(2,samplingFactors.get(1), Defaults.DEFAULT_CHROMA_QUANT_TABLE, Defaults.chromaAcEncodingTable, Defaults.chromaDcEntropyEncodingTable, componentData[1]);
        var crComponent=new ImageComponent(3,samplingFactors.get(2), Defaults.DEFAULT_CHROMA_QUANT_TABLE, Defaults.chromaAcEncodingTable, Defaults.chromaDcEntropyEncodingTable, componentData[2]);

        return new ImageComponent[]{yComponent,cbComponent,crComponent};
    }

    public static String getBaseName(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return fileName;
        } else {
            return fileName.substring(0, index);
        }
    }

}
