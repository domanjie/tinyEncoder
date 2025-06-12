package domanjie.dev.encoder;

public class SubSampler {

    public static int[][] subSampleComponent(int[][] component, SamplingFactor componentSf, int maxHorizontalSamplingFactor, int maxVerticalSamplingFactor){
        var noOfColumnsSampledArray = componentSf.horizontalSamplingFactor() * component[0].length / maxHorizontalSamplingFactor;
        var noOfRowsSampledArray = componentSf.verticalSamplingFactor() * component.length / maxVerticalSamplingFactor;
        var sampledArray=new int[noOfRowsSampledArray][noOfColumnsSampledArray];
        for(int i =0; i<  component.length;i+= maxVerticalSamplingFactor){
            for (int j=0 ;j <component[0].length;j+=maxHorizontalSamplingFactor){

                var sampledArrRowIndex=i/maxVerticalSamplingFactor * componentSf.verticalSamplingFactor();
                var sampledArrColIndex=j/maxHorizontalSamplingFactor * componentSf.horizontalSamplingFactor();
                for (int a=i; a<i+componentSf.verticalSamplingFactor();  a++){
                    for(int b=j; b<j+componentSf.horizontalSamplingFactor();b++){
                        sampledArray[sampledArrRowIndex][sampledArrColIndex]  = component[a][b];
                        sampledArrColIndex++;

                    }
                    sampledArrColIndex=j/maxHorizontalSamplingFactor * componentSf.horizontalSamplingFactor();
                    sampledArrRowIndex++;
                }
            }
        }
        return sampledArray;
    }

}
