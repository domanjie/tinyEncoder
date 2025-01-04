package domanjie.dev;

import domanjie.dev.bmp.BitMapFileProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        File file =new File(args[0]);

        byte[] byteArr;
        try {
        byteArr= Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}