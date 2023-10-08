package dev.jettro.embeddings;

import java.util.List;

public class SearchResults {
    private final List<SearchResult> results;
    private final String searchTerm;

    public SearchResults(List<SearchResult> results, String searchTerm) {
        this.results = results;
        this.searchTerm = searchTerm;
    }

    public List<SearchResult> getResults() {
        return results;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    @Override
    public String toString() {
        return "SearchResults{" +
                "results=" + results +
                ", searchTerm='" + searchTerm + '\'' +
                '}';
    }
}
