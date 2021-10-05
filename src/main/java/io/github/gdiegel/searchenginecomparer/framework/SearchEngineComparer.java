package io.github.gdiegel.searchenginecomparer.framework;

import com.google.common.annotations.VisibleForTesting;
import io.github.gdiegel.searchenginecomparer.adapter.SearchProvider;
import io.github.gdiegel.searchenginecomparer.entity.SearchResult;
import io.github.gdiegel.searchenginecomparer.entity.exception.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

/**
 * Main class for the application, makes the search requests and compares the results
 */
@Profile("!test")
@Component
public class SearchEngineComparer implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(SearchEngineComparer.class);

    @Autowired
    @Qualifier("googleProvider")
    private SearchProvider googleProvider;

    @Autowired
    @Qualifier("bingProvider")
    private SearchProvider bingProvider;

    @Autowired
    private List<String> terms;

    @VisibleForTesting
    protected static void printTotalHits(List<SearchResult> results) {
        results.stream()
            .collect(groupingBy(SearchResult::getTerm))
            .forEach((term, value) -> LOG.info(term + ": " + value.stream().map(SearchResult::format).collect(Collectors.joining(", "))));
    }

    @VisibleForTesting
    protected static String determineWinner(List<SearchResult> results, String engine) {
        return results.stream()
            .filter(searchResult -> searchResult.getEngine().equals(engine))
            .max(comparingLong(SearchResult::getNumberOfHits))
            .orElseThrow(() -> new InvalidDataException("Unable to determine winner"))
            .getTerm();

    }

    @VisibleForTesting
    protected static String determineTotalWinner(List<SearchResult> results) {
        return results.stream()
            .collect(groupingBy(SearchResult::getTerm, summingLong(SearchResult::getNumberOfHits)))
            .entrySet().stream().max(comparingLong(Map.Entry::getValue))
            .orElseThrow(() -> new InvalidDataException("Unable to determine total winner"))
            .getKey();

    }

    @Override
    public void run(String... args) {
        final List<SearchResult> results = new ArrayList<>();
        terms.forEach(term -> {
            results.add(googleProvider.search(term));
            results.add(bingProvider.search(term));
        });

        printTotalHits(results);
        LOG.info("Google winner: {}", determineWinner(results, "google"));
        LOG.info("Bing winner: {}", determineWinner(results, "bing"));
        LOG.info("Total winner: {}", determineTotalWinner(results));
    }
}
