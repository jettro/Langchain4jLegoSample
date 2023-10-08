package dev.jettro.embeddings;

/**
 * Interface used to define the methods that should be implemented by an EmbeddingModel.
 */
public interface EmbeddingModel {
    /**
     * Get the name of the model.
     * @return String containing the name of the model
     */
    String getName();
}
