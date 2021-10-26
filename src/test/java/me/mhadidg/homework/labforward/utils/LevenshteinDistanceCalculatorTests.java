package me.mhadidg.homework.labforward.utils;

class LevenshteinDistanceCalculatorTests extends AbstractEditDistanceCalculatorTests {

  @Override
  public EditDistanceCalculator createEditDistanceCalculator(Integer threshold) {
    return new LevenshteinDistanceCalculator(threshold);
  }

}