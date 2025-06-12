package domanjie.dev.encoder.entropyEncoder;

import domanjie.dev.utils.OutOfBoundsException;

public class AcHuffmanEntropyEncoder  {
    private  final HuffmanCodeAndCodeSizeTablesGenerator generator;
    public AcHuffmanEntropyEncoder(int[] bitList, int[] huffVal) {
        generator=new HuffmanCodeAndCodeSizeTablesGenerator(bitList, huffVal);

    }
    public EncodedVar encode(int runOfZeros, int val) {
        if(val==0){
            int symbol;
            if(runOfZeros ==0){
                //Symbol for End of block
                symbol = 0x00;
            }else if(runOfZeros==15){
                //symbol for run of 16 zeros
                symbol= 0xF0;
            } else {
                throw new IllegalArgumentException("Encoding run of " + runOfZeros+1 + "not allowed");
            }
            var huffCode=generator.getHuffmanCodeForSymbol(symbol);
            var huffSize=generator.getHuffmanCodeSizeForSymbol(symbol);
            return new EncodedVar(huffCode, huffSize);
        }
        var category = cSize(val);
        var rc= runOfZeros<<4|category;
        var huffCode = generator.getHuffmanCodeForSymbol(rc);
        var huffSize = generator.getHuffmanCodeSizeForSymbol(rc);
        //this gets the additional bits to be concatenated
        //with the huffman-coded val
        var additionalBits=0;
        if(val<0)
            additionalBits= val-1 & getBitmask(category);
        else
            additionalBits= val   & getBitmask(category);
        var encodedVal=(huffCode<<category)|additionalBits;
        var encodedValSize=huffSize+category;
        return new EncodedVar(encodedVal, encodedValSize);
    }
    private int getBitmask(int category){
        return ((1<<category) -1);
    }

    int cSize(int val){
        var absVal=Math.abs(val);
        if(absVal==1)
            return 1;
        else if (absVal>=2 &&absVal<=3)
            return 2;
        else if (absVal>=4 &&absVal<=7)
            return 3;
        else if (absVal>=8 &&absVal<=15)
            return 4;
        else if(absVal>=16 &&absVal<=31)
            return 5;
        else if (absVal>=32 &&absVal<=63)
            return 6;
        else if (absVal>=64 &&absVal<=127)
            return 7;
        else if(absVal>=128 &&absVal<=255)
            return 8;
        else if(absVal>=256 && absVal<=511)
            return 9;
        else if (absVal>=512 &&absVal<=1023)
            return 10;
        else
            throw new OutOfBoundsException("val:"+ val+" out of bounds" );
    }

}
