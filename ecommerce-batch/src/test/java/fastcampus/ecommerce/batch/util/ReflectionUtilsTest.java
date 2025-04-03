package fastcampus.ecommerce.batch.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReflectionUtilsTest {

  private static class TestClass {

    private String stringField;
    private int intField;
    public static final String CONSTANT = "constant";
  }

  @Test
  void testGetFieldNames() {
    List<String> fieldNames = ReflectionUtils.getFieldNames(TestClass.class);

    assertThat(fieldNames).hasSize(2)
        .contains("stringField", "intField")
        .doesNotContain("CONSTANT");
  }
}