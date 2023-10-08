package dev.jettro;

import dev.jettro.embeddings.EmbeddingsEvaluator;
import dev.jettro.embeddings.LocalEmbeddingModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmbeddingsEvaluatorTest {
    @Test
    void buildWithModel() {
        EmbeddingsEvaluator evaluator = EmbeddingsEvaluator.buildWithModel(
                new LocalEmbeddingModel(LocalEmbeddingModel.ALL_MINI_LML6V2_QUANTIZED)
        );

        assertNotNull(evaluator);
    }
}