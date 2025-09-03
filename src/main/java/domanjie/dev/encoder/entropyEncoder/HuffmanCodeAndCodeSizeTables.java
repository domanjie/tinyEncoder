package domanjie.dev.encoder.entropyEncoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HuffmanCodeAndCodeSizeTables {
    private  final Map<Integer, Integer> EHUFCO=new HashMap<>();
    private final Map<Integer ,Integer> EHUFSI=new HashMap<>();
    public HuffmanCodeAndCodeSizeTables(int[] bitList, int[] huffVal){
        var code=0;
        var huffValIndex=0;
        for (int i = 0; i<bitList.length; i++){
            if (bitList[i]>0){
                for(int j = 0; j <bitList[i];j++){
                    EHUFCO.put(huffVal[huffValIndex], code);
                    EHUFSI.put(huffVal[huffValIndex], i+1);
                    code++;
                    huffValIndex++;
                }
            }
            code<<=1;
        }
    }
    public Integer getHuffmanCodeForSymbol(int symbol){
        return this.EHUFCO.get(symbol);
    }
    public Integer getHuffmanCodeSizeForSymbol(int symbol){
        return this.EHUFSI.get(symbol);
    }
    public Map<Integer, Integer> getHuffmanCodeSizeTable(){
        return Collections.unmodifiableMap(this.EHUFSI);
    }
    public Map<Integer, Integer> getHuffmanCodeTable(){
        return Collections.unmodifiableMap(this.EHUFCO);
    }
}
