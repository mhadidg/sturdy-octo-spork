package me.mhadidg.homework.labforward.utils;

/**
 * Interface for edit distance algorithms.
 *
 * <p>
 * Edit distance is a way of quantifying how dissimilar two strings (e.g., words) are
 * to one another by counting the minimum number of operations required to transform
 * one string into the other.
 * </p>
 *
 * <p>
 * This code has been adapted (with modifications) from Apache Commons Lang 3.3.
 * </p>
 *
 * @author Mustapha Hadid
 */
public interface EditDistanceCalculator {

  /**
   * Compares two CharSequences.
   *
   * @param left  the first CharSequence
   * @param right the second CharSequence
   * @return The similarity score between two CharSequences
   */
  Integer apply(String left, String right);

}
