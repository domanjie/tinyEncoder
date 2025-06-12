package domanjie.dev.jpeg;

public class JpegMarker {

    public static final int SOF1=0xffC0;//start of frame marker for BaseLine DCT in non-differential, Huffman Coding process
    public static final int DHT=0xffC4;//define human table
    public static final int SOI=0xFFD8;//start of image
    public static final int EOI=0xFFD9;//end of image
    public static final int SOS=0xFFDA;//start of scan
    public static final int DQT=0xFFDB;//define quantization table
    public static final int DNL=0xFFDC;//define number of lines
    public static final int DRI=0xFFDD;//define restart interval
    public static final int DHP=0xFFDE;//define hierarchical progression
    public static final int EXP=0XFFDF;//expand reference components

    //Restart interval termination 0 through 7
    public static final int RST0=0XFFD0;
    public static final int RST1=0XFFD1;
    public static final int RST2=0XFFD2;
    public static final int RST3=0XFFD3;
    public static final int RST4=0XFFD4;
    public static final int RST5=0XFFD5;
    public static final int RST6=0XFFD6;
    public static final int RST7=0XFFD7;





}
