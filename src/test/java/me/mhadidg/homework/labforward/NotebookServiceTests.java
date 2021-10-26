package me.mhadidg.homework.labforward;

import me.mhadidg.homework.labforward.notebook.FrequencyDTO;
import me.mhadidg.homework.labforward.notebook.NotebookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {NotebookService.class})
class NotebookServiceTests {

  @Autowired
  NotebookService service;

  @Test
  public void shouldTrimGivenWord() {
    List<String> wordList = List.of("word");
    FrequencyDTO frequency = service.calculateWordFrequency(wordList, " word ");

    assertThat(frequency).isNotNull();
    assertThat(frequency.getSimilarWords()).isEmpty();
  }

  @Test
  public void shouldTrimEachWordOnGivenWordList() {
    List<String> wordList = List.of(" word ");
    FrequencyDTO frequency = service.calculateWordFrequency(wordList, "word");

    assertThat(frequency).isNotNull();
    assertThat(frequency.getSimilarWords()).isEmpty();
  }

  @Test
  public void shouldFailOnInvalidArgumentValues() {
    assertThatNullPointerException().isThrownBy(() -> service.calculateWordFrequency(List.of(), null));
    assertThatNullPointerException().isThrownBy(() -> service.calculateWordFrequency(null, "word"));
    assertThatIllegalArgumentException().isThrownBy(() -> service.calculateWordFrequency(List.of(), ""));
    assertThatIllegalArgumentException().isThrownBy(() -> service.calculateWordFrequency(List.of(), " "));
  }

  @Test
  public void shouldNotIncludeExactMatchInSimilarWords() {
    List<String> wordList = List.of("word");
    FrequencyDTO frequency = service.calculateWordFrequency(wordList, "word");

    assertThat(frequency).isNotNull();
    assertThat(frequency.getSimilarWords()).isEmpty();
  }

  @Test
  public void shouldCalculateFrequencyBasedOnExactMatch() {
    List<String> wordList = List.of("word", "words", "wordiest", "word");
    FrequencyDTO frequency = service.calculateWordFrequency(wordList, "word");

    assertThat(frequency).isNotNull();
    assertThat(frequency.getFrequency()).isEqualTo(2);
  }

  @Test
  public void shouldIncludeSimilarWordsWithEditDistanceEqualsToOne() {
    List<String> wordList = List.of("word", "wording", "wod", "words", "word's");
    FrequencyDTO frequency = service.calculateWordFrequency(wordList, "word");

    assertThat(frequency).isNotNull();
    assertThat(frequency.getSimilarWords()).containsExactlyInAnyOrder("wod", "words");
  }

  @Test
  public void shouldNotEncounterConcurrencyIssueOnLargeWordList() {
    String word = "pneumonoultramicroscopicsilicovolcanoconiosis";
    Set<String> wordList = generateOneEditDistanceVariants(word);

    FrequencyDTO frequency = service.calculateWordFrequency(wordList, word);

    assertThat(frequency.getFrequency() + frequency.getSimilarWords().size()).isEqualTo(wordList.size());
  }

  private Set<String> generateOneEditDistanceVariants(String word) {
    int[] letters = IntStream.rangeClosed('a', 'z').toArray();

    Set<String> result = new HashSet<>(word.length() * letters.length);
    char[] wordChars = word.toCharArray();

    for (int i = 0; i < word.length(); i++) {
      for (int letter : letters) {
        char[] chars = Arrays.copyOf(wordChars, wordChars.length);
        chars[i] = (char) letter;
        result.add(String.valueOf(chars));
      }
    }

    return result;
  }

}