package dev.jettro.embeddings;

import dev.jettro.csv.CSVToDocumentReader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.*;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static dev.jettro.embeddings.LocalEmbeddingModel.*;

public class EmbeddingsEvaluator {
    private static final Logger logger = LoggerFactory.getLogger(EmbeddingsEvaluator.class);
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final dev.langchain4j.model.embedding.EmbeddingModel embeddingModel;

    /**
     * Constructor to create the evaluator with the given model. An in memory store is used.
     * @param embeddingModel model to be used
     */
    private EmbeddingsEvaluator(dev.langchain4j.model.embedding.EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
        this.embeddingStore = new InMemoryEmbeddingStore<>();
    }

    /**
     * Initialize the embedding store with the given CSV file.
     * @param pathToCsv path to the CSV file to be used
     */
    public void initEmbeddingStore(String pathToCsv) {

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(500, 0))
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        ingestor.ingest(CSVToDocumentReader.readCSV("name", pathToCsv));
    }

    /**
     * Search for the given search term in the initialized embedding store.
     * @param searchTerm term to search for
     * @return SearchResults containing the results
     */
    public SearchResults search(String searchTerm) {
        Embedding content = embeddingModel.embed(searchTerm).content();
        List<EmbeddingMatch<TextSegment>> mostRelevant = embeddingStore.findRelevant(content, 4);

        logger.debug("Search term: {}", searchTerm);

        List<SearchResult> list = mostRelevant.stream().map(stringEmbeddingMatch -> {
            logger.debug("{} - {}", stringEmbeddingMatch.score().toString(), stringEmbeddingMatch.embedded().text());
            return new SearchResult(stringEmbeddingMatch.embedded().text(), stringEmbeddingMatch.score());
        }).toList();

        return new SearchResults(list, searchTerm);
    }

    /**
     * Build the evaluator with the model to be used.
     * @param embeddingModel model to be used
     * @return EmbeddingsEvaluator
     */
    public static EmbeddingsEvaluator buildWithModel(dev.jettro.embeddings.EmbeddingModel embeddingModel) {
        return switch (embeddingModel) {
            case OpenAIEmbeddingModel model -> new EmbeddingsEvaluator(initOpenAIModel(model));
            case LocalEmbeddingModel model -> new EmbeddingsEvaluator(initLocalModel(model));
            default -> throw new IllegalStateException("Unexpected value: " + embeddingModel);
        };
    }

    private static dev.langchain4j.model.embedding.EmbeddingModel initLocalModel(LocalEmbeddingModel model) {
        return switch (model.getName()) {
            case ALL_MINI_LML6V2_QUANTIZED -> new AllMiniLmL6V2QuantizedEmbeddingModel();
            case ALL_MINI_LML6V2 -> new AllMiniLmL6V2EmbeddingModel();
            case BGE_SMALL_EN -> new BgeSmallEnEmbeddingModel();
            case BGE_SMALL_EN_QUANTIZED -> new BgeSmallEnQuantizedEmbeddingModel();
            case ONNX -> new OnnxEmbeddingModel(model.getName());
            default -> throw new IllegalStateException("Unexpected value: " + model);
        };
    }

    private static dev.langchain4j.model.embedding.EmbeddingModel initOpenAIModel(OpenAIEmbeddingModel model) {
        return OpenAiEmbeddingModel.builder()
                .apiKey(model.getApiKey())
                .modelName(model.getName())
                .maxRetries(1)
                .logRequests(model.isLogRequests())
                .logResponses(model.isLogResponses())
                .build();
    }

}
