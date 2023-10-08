package dev.jettro.embeddings;

public class LocalEmbeddingModel implements EmbeddingModel {
    public final static String ALL_MINI_LML6V2_QUANTIZED = "all-mini-lm-l6-v2-quantized";
    public final static String ALL_MINI_LML6V2 = "all-mini-lm-l6-v2";
    public final static String BGE_SMALL_EN_QUANTIZED = "bge-small-en-quantized";
    public final static String BGE_SMALL_EN = "bge-small-en";
    public final static String ONNX = "onnx";

    private final String modelName;

    public LocalEmbeddingModel(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String getName() {
        return modelName;
    }
}
