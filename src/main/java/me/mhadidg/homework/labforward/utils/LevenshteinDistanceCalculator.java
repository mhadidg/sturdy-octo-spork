package me.mhadidg.homework.labforward.utils;

import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * An algorithm for measuring the difference between two character sequences.
 *
 * <p>
 * This is the number of changes needed to change one sequence into another,
 * where each change is a single character modification (deletion, insertion
 * or substitution).
 * </p>
 *
 * @author Mustapha Hadid
 */
public class LevenshteinDistanceCalculator implements EditDistanceCalculator {

  private final LevenshteinDistance calculator;

  public LevenshteinDistanceCalculator(int threshold) {
    calculator = new LevenshteinDistance(threshold);
  }

  /**
   * Find the Levenshtein distance between two Strings.
   *
   * @param left  the first string, must not be null
   * @param right the second string, must not be null
   * @return result distance, or -1
   * @throws IllegalArgumentException if either String input {@code null}
   */
  @Override
  public Integer apply(String left, String right) {
    return calculator.apply(left, right);
  }

}
