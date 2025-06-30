package domanjie.dev.encoder.entropyEncoder;

import java.util.ArrayList;
import java.util.List;

public class EntropyEncodedDataStore {
    private final List<Byte> byteList= new ArrayList<>();
    private  int nextBitIndex=0;

    /*packs bitCount number of bits of val  into the byte array
     */
    public void  addBits(int val,int  bitCount){
        for(int i=bitCount-1;i>= 0 ; i--){
           var bit =  (val>>i)&1;
           if(nextBitIndex%8==0){
               byteList.add((byte)0x00);
           }
           var lastByte= byteList.remove(byteList.size()-1);
           lastByte = (byte) (lastByte | (bit  << (7- nextBitIndex%8)));
           byteList.add(lastByte);
           nextBitIndex++;
           if(nextBitIndex%8==0){
               if (byteList.get(byteList.size()-1) == (byte) (0xff)){
                   byteList.add((byte) 0x00);
                   nextBitIndex+=8;
               }
           }
        }
    };
    public List<Byte> getData(){
        if(nextBitIndex%8!=0){
            addBits(0xff,  8-nextBitIndex %8);
        }
        return byteList;
    }
}
