package core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/** Integration test for {@link NumberFormatter}. */
public class NumberFormatterTest {

  @Test
  public void testSmallNumbers() {
    assertEquals("0", NumberFormatter.formatCount(0));
    assertEquals("1", NumberFormatter.formatCount(1));
    assertEquals("12", NumberFormatter.formatCount(12));
  }

  @Test
  public void testThousands() {
    assertEquals("1k", NumberFormatter.formatCount(1_000));
    assertEquals("1,1k", NumberFormatter.formatCount(1_100));
    assertEquals("12k", NumberFormatter.formatCount(12_345));
  }

  @Test
  public void testMillions() {
    assertEquals("1M", NumberFormatter.formatCount(1_000_000));
    assertEquals("1,2M", NumberFormatter.formatCount(1_234_567));
    assertEquals("12M", NumberFormatter.formatCount(12_345_678));
  }

  @Test
  public void testBillions() {
    assertEquals("1B", NumberFormatter.formatCount(1_000_000_000));
    assertEquals("1,2B", NumberFormatter.formatCount(1_234_567_890));
    assertEquals("11B", NumberFormatter.formatCount(11_234_567_890L));
  }

  @Test
  public void testEdgeCases() {
    assertEquals("999", NumberFormatter.formatCount(999));
    assertEquals("1k", NumberFormatter.formatCount(1_000));
    assertEquals("999k", NumberFormatter.formatCount(999_999));
    assertEquals("1M", NumberFormatter.formatCount(1_000_000));
    assertEquals("1000B", NumberFormatter.formatCount(1_000_000_000_000L));
  }
}
