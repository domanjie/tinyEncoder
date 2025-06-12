package domanjie.dev.jpeg;

import domanjie.dev.encoder.SamplingFactor;


public record ImageComponent(int label,
                             SamplingFactor samplingFactor,
                             QuantTable   quantTable,
                             HuffmanTable acEntropyEncodingTable,
                             HuffmanTable dcEntropyEncodingTable, int[][]data ) {
}
