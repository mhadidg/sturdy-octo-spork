package me.mhadidg.homework.labforward.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractEditDistanceCalculatorTests {

  public static final int THRESHOLD = 3;

  private EditDistanceCalculator calculator;

  public abstract EditDistanceCalculator createEditDistanceCalculator(Integer threshold);

  @BeforeEach
  public void setup() {
    calculator = createEditDistanceCalculator(THRESHOLD);
  }

  @Test
  public void shouldReturnZeroOnMatchingWords() {
    assertThat(calculator.apply("word", "word")).isEqualTo(0);
  }

  @Test
  public void shouldBeCaseSensitive() {
    assertThat(calculator.apply("word", "Word")).isEqualTo(1);
  }

  @Test
  public void shouldCountInsertionAsEdit() {
    assertThat(calculator.apply("word", "words")).isEqualTo(1);
  }

  @Test
  public void shouldCountDeletionAsEdit() {
    assertThat(calculator.apply("word", "wod")).isEqualTo(1);
  }

  @Test
  public void shouldCountSubstitutionAsEdit() {
    assertThat(calculator.apply("word", "lord")).isEqualTo(1);
  }

  @Test
  public void shouldBeLimitedToThreshold() {
    assertThat(calculator.apply("word", "word's")).isEqualTo(2);
    assertThat(calculator.apply("word", "wording")).isEqualTo(3);
    assertThat(calculator.apply("word", "wordiest")).isEqualTo(-1);
  }

}