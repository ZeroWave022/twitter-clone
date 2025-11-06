package core.util;

import java.util.Locale;

/**
 * Utility class for formatting large numbers into a readable string with
 * suffixes: - "k" for thousands - "M" for millions - "B" for billions.
 * 
 * <p>
 * Numbers are floored and decimals are shown only for smaller ranges:
 * 
 * For <10k, <10M, <10B -> one decimal
 * 
 * For >=10k, >=10M, >=10B -> no decimal
 * </p>
 */
public abstract class NumberFormatter {
  private static final double EPSILON = 1e-9;

  /**
   * Formats a count into a readable string with suffixes (k, M, B).
   *
   * @param count the number to format
   * @return a formatted string, e.g., "1.1k", "12M", "1.2B"
   */
  public static String formatCount(long count) {
    if (count < 1_000) {
      return String.format(Locale.US, "%d", count);
    }

    if (count < 1_000_000) {
      double value = count / 1_000.0;
      if (count < 10_000) {
        return formatDecimal(value) + "k";
      } else {
        return String.format(Locale.US, "%.0fk", (double) Math.floor(value));
      }
    }

    if (count < 1_000_000_000) {
      double value = count / 1_000_000.0;
      if (count < 10_000_000) {
        return formatDecimal(value) + "M";
      } else {
        return String.format(Locale.US, "%.0fM", (double) Math.floor(value));
      }
    }

    double value = count / 1_000_000_000.0;
    if (count < 10_000_000_000L) {
      return formatDecimal(value) + "B";
    } else {
      return String.format(Locale.US, "%.0fB", (double) Math.floor(value));
    }
  }

  /**
   * Floors a double value to one decimal and returns as string. Removes the
   * decimal if the value is a whole number. Example 1.0k -> 1k
   * 
   * <p>
   * Epsilon is introduced as a margain of error since a conversion from double to
   * long is inherently flawed/lossy
   * </p>
   *
   * @param value the value to format
   * @return a string with at most one decimal
   */
  private static String formatDecimal(double value) {
    double floored = Math.floor(value * 10) / 10.0;

    if (Math.abs(floored - Math.round(floored)) < EPSILON) { // check if whole number
      return String.format(Locale.US, "%.0f", (double) Math.round(floored));
    } else {
      return String.format(Locale.US, "%.1f", floored);
    }
  }
}
