package domanjie.dev.encoder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubSamplerTest {
    @Test
    public void shouldReturnBlockSampledByTheInputSamplingFactorsRelativeToMaxSamplingFactorsCase1 (){
       var inputComponent=  new int[][]{
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
        };

       assertEquals(inputComponent.length, 24);
       assertEquals(inputComponent[0].length, 16);
       var maxHorizontalSamplingFactor= 2;
       var maxVerticalSamplingFactor=3;
       var componentSf= new SamplingFactor(1,2);
       var expectedOutput= new int[][]{
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
               {2, 2, 2, 2, 2, 2, 2, 2},
       };
       assertEquals(expectedOutput.length, 16);
       assertEquals(expectedOutput[0].length, 8);
       var actualOutput=SubSampler.subSampleComponent(inputComponent,componentSf, maxHorizontalSamplingFactor, maxVerticalSamplingFactor );
       assertArrayEquals (expectedOutput, actualOutput);
    };
    @Test
    public void shouldReturnBlockSampledByTheInputSamplingFactorsRelativeToMaxSamplingFactorsCase2(){
        var inputComponent=  new int[][]{
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
        };

        assertEquals(inputComponent.length, 24);
        assertEquals(inputComponent[0].length, 16);
        var maxHorizontalSamplingFactor= 2;
        var maxVerticalSamplingFactor=3;
        var componentSf= new SamplingFactor(2,1);
        var expectedOutput= new int[][]{
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },

        };
        var actualOutput=SubSampler.subSampleComponent(inputComponent,componentSf, maxHorizontalSamplingFactor, maxVerticalSamplingFactor );
        assertArrayEquals (expectedOutput, actualOutput);
    };
    @Test
    public void shouldReturnSampledBlockEqualToTheInputBlockWhenInputSamplingFactorsMatchMaxSamplingFactors(){
        var inputComponent=  new int[][]{
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
                {2,4, 2, 4, 2, 4, 2 ,4 ,2,4, 2, 4, 2, 4, 2 ,4 },
        };

        assertEquals(inputComponent.length, 24);
        assertEquals(inputComponent[0].length, 16);
        var maxHorizontalSamplingFactor= 2;
        var maxVerticalSamplingFactor=3;
        var componentSf= new SamplingFactor(2,3);
        var actualOutput=SubSampler.subSampleComponent(inputComponent,componentSf, maxHorizontalSamplingFactor, maxVerticalSamplingFactor );
        assertArrayEquals (inputComponent, actualOutput);
    };



}