package domanjie.dev.encoder.entropyEncoder;

import domanjie.dev.utils.OutOfBoundsException;

public class DcHuffmanEntropyEncoder {
    private  int pred=0;
    private final HuffmanCodeAndCodeSizeTables tables ;
    public DcHuffmanEntropyEncoder(int[] bitList, int[] huffVal) {
        tables =new HuffmanCodeAndCodeSizeTables(bitList, huffVal);
    }

    public EncodedVar encode(int val) {
        var diff= val-pred;
        pred=val;
        var category =csize(diff);
        var huffCode=tables.getHuffmanCodeForSymbol(category);
        var huffSize=tables.getHuffmanCodeSizeForSymbol(category);

        //this gets the additional bits to be concatenated
        //with the huffman-coded category and is for uniquely
        // representing a value in that category
        var additionalBits=0;
        if(diff<0)
            additionalBits=diff-1 & getBitmask(category);
        else
            additionalBits=diff & getBitmask(category);
        var  encodedVal= (huffCode<<category)|additionalBits;
        var  encodedValSize= category+huffSize;
        return new EncodedVar(encodedVal, encodedValSize);
    }
    private int getBitmask(int category){
        return ((1<<category) -1);
    }

    int csize(int val){
        var absVal=Math.abs(val);
        if(absVal==0)
            return 0;
        else if(absVal==1)
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
        else if (absVal>=1024 &&absVal<=2047 )
            return 11;
        else
            throw new OutOfBoundsException("val:"+ val+" out of bounds" );
    }

}
