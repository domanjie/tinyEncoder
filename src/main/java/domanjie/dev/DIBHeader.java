package domanjie.dev;


import domanjie.dev.bmp.Compression;

public record DIBHeader(int imageHeight, int imageWidth ,short bitPerPixel , Compression compressionMethod ) {
}
