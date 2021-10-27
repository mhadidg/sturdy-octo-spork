package me.mhadidg.homework.labforward.notebook;

import me.mhadidg.homework.labforward.utils.EditDistanceCalculator;
import me.mhadidg.homework.labforward.utils.LevenshteinDistanceCalculator;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Mustapha Hadid
 */
@Service
public class NotebookService {

  public static final int SIMILARITY_THRESHOLD = 1;

  private final EditDistanceCalculator distanceCalculator;

  public NotebookService() {
    this.distanceCalculator = new LevenshteinDistanceCalculator(SIMILARITY_THRESHOLD);
  }

  /**
   * Calculates the frequency of the given {@link String word} along with a set of
   * similar words. Duplicates are removed from similar words.
   *
   * @param wordList must not be {@literal null}
   * @param word     must not be {@literal null} or blank
   * @return An instance of {@link FrequencyDTO}
   * @throws NullPointerException     in case an argument with null value provided
   * @throws IllegalArgumentException in case the {@link String word} argument was
   *                                  empty or blank
   */
  public FrequencyDTO calculateWordFrequency(Collection<String> wordList, String word) {
    Validate.notNull(wordList, "wordList cannot be null!");
    Validate.notBlank(word, "word cannot be null or blank!");

    AtomicLong frequency = new AtomicLong(0);
    Set<String> similarWords = ConcurrentHashMap.newKeySet();

    String trimmedWord = word.trim();

    wordList.stream().parallel()
        .forEach(it -> {
          Integer diff = distanceCalculator.apply(it.trim(), trimmedWord);
          if (diff == 0) {
            frequency.incrementAndGet();
          } else if (diff != -1) {
            similarWords.add(it);
          }
        });

    return FrequencyDTO.of(frequency.get(), similarWords);
  }

}
