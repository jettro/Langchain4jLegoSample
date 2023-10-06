package dev.jettro;

import dev.jettro.csv.CSVToDocumentReader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmbeddingsEvaluator {
    private static final Logger logger = LoggerFactory.getLogger(EmbeddingsEvaluator.class);
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;

    public EmbeddingsEvaluator(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
        this.embeddingStore = new InMemoryEmbeddingStore<>();
    }

    public void initEmbeddingStore(String pathToCsv) {

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(500, 0))
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        ingestor.ingest(CSVToDocumentReader.readCSV("name", pathToCsv));
    }

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


    public static EmbeddingsEvaluator buildWithModel(boolean largeModel) {
        EmbeddingModel embeddingModel;
        if (largeModel) {
            embeddingModel = initLargeModel();
        } else {
            embeddingModel = initMiniModel();
        }

        return new EmbeddingsEvaluator(embeddingModel);
    }

    public static EmbeddingModel initMiniModel() {
        return new AllMiniLmL6V2QuantizedEmbeddingModel();
    }

    private static EmbeddingModel initLargeModel() {
        String apiKey = System.getenv("OPENAI_API_KEY");

        return OpenAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("text-embedding-ada-002")
                .maxRetries(1)
                .logRequests(false)
                .logResponses(false)
                .build();
    }

}
