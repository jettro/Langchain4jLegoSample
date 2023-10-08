package dev.jettro.embeddings;

/**
 * Class used to store the result of a search.
 */
public class SearchResult {
    private final String name;
    private final Double score;

    public SearchResult(String name, Double score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public Double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
