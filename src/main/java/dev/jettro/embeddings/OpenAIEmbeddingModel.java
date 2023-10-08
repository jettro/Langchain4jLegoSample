package dev.jettro.embeddings;

/**
 * Class used to define the OpenAI embedding model to be used.
 */
public class OpenAIEmbeddingModel implements EmbeddingModel{
    public static final String TEXT_EMBEDDING_ADA_002 = "text-embedding-ada-002";

    private final String modelName;
    private final String apiKey;
    private boolean logRequests = false;
    private boolean logResponses = false;

    public OpenAIEmbeddingModel(String apiKey) {
        this(apiKey, TEXT_EMBEDDING_ADA_002, false);
    }

    public OpenAIEmbeddingModel(String apiKey, boolean log) {
        this(apiKey, TEXT_EMBEDDING_ADA_002, log);
    }

    public OpenAIEmbeddingModel(String apiKey, String modelName, boolean log) {
        this.modelName = modelName;
        this.apiKey = apiKey;
        if (log) {
            this.logRequests = true;
            this.logResponses = true;
        }
    }

    @Override
    public String getName() {
        return modelName;
    }

    /**
     * Get the API key to be used to access the OpenAI API.
     * @return String containing the API key
     */
    public String getApiKey() {
        return apiKey;
    }

    public boolean isLogRequests() {
        return logRequests;
    }

    public boolean isLogResponses() {
        return logResponses;
    }
}
