package dev.jettro;

import dev.jettro.embeddings.EmbeddingsEvaluator;
import dev.jettro.embeddings.LocalEmbeddingModel;
import dev.jettro.embeddings.OpenAIEmbeddingModel;
import dev.jettro.embeddings.SearchResults;

import static dev.jettro.embeddings.LocalEmbeddingModel.BGE_SMALL_EN_QUANTIZED;

public class RunEmbeddingsComparison {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * Print the results of a search.
     *
     * @param results results to print
     */
    public static void printResults(SearchResults results) {
        System.out.printf(ANSI_GREEN + "______ Search term: %s ______%n" + ANSI_RESET, results.getSearchTerm());
        results.getResults().forEach(searchResult ->
                System.out.printf("%.5f - %s%n", searchResult.getScore(), searchResult.getName()));
    }

    /**
     * Main method to start the application.
     *
     * @param args arguments passed to the application
     */
    public static void main(String[] args) {
//        EmbeddingsEvaluator evaluator = EmbeddingsEvaluator.buildWithModel(new LocalEmbeddingModel(ALL_MINI_LML6V2_QUANTIZED));
//        EmbeddingsEvaluator evaluator = EmbeddingsEvaluator.buildWithModel(new LocalEmbeddingModel(ALL_MINI_LML6V2));
        EmbeddingsEvaluator evaluator = EmbeddingsEvaluator.buildWithModel(new LocalEmbeddingModel(BGE_SMALL_EN_QUANTIZED));
//        EmbeddingsEvaluator evaluator = EmbeddingsEvaluator.buildWithModel(new LocalEmbeddingModel(BGE_SMALL_EN));
//        EmbeddingsEvaluator evaluator = EmbeddingsEvaluator.buildWithModel(
//                new OpenAIEmbeddingModel(System.getenv("OPENAI_API_KEY"), false));
        evaluator.initEmbeddingStore("data/all_brickheadz.csv");

        printResults(evaluator.search("Lord of the rings"));
        printResults(evaluator.search("Dagobert"));
        printResults(evaluator.search("Harry Potter"));
        printResults(evaluator.search("Star Wars"));
    }
}