package fastcampus.ecommerce.batch.util;

import fastcampus.ecommerce.batch.domain.product.ProductStatus;
import fastcampus.ecommerce.batch.dto.ProductUploadCsvRow;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class ProductGenerator {

  private static final Random RANDOM = new Random();

  public static void main(String[] args) {
    String csvFilePath = "data/random_product.csv";
    int recordCount = 10_000_000;

    try (FileWriter fileWriter = new FileWriter(csvFilePath);
        CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.builder()
            .setHeader(
                ReflectionUtils.getFieldNames(ProductUploadCsvRow.class).toArray(String[]::new))
            .build())
    ) {
      for (int i = 0; i < recordCount; i++) {
        printer.printRecord(generateRecord());
        if (i % 100_000 == 0) {
          System.out.println("Generated " + i + " records");
        }
      }

    } catch (IOException e) {
      System.out.println("IO exception!!");
    }
  }

  private static Object[] generateRecord() {
    ProductUploadCsvRow row = randomProductRow();
    return new Object[]{
        row.getSellerId(),
        row.getCategory(),
        row.getProductName(),
        row.getSalesEndDate(),
        row.getSalesEndDate(),
        row.getProductStatus(),
        row.getBrand(),
        row.getManufacturer(),
        row.getSalesPrice(),
        row.getStockQuantity()
    };
  }

  private static ProductUploadCsvRow randomProductRow() {
    String[] CATEGORIES = {"가전", "가구", "패션", "식품", "화장품", "서적", "스포츠", "완구", "음악", "디지털"};
    String[] PRODUCT_NAMES = {"TV", "소파", "셔츠", "햇반", "스킨케어 세트", "소설", "축구공", "레고", "기타", "스마트폰"};
    String[] BRANDS = {"삼성", "LG", "나이키", "아모레퍼시픽", "현대", "BMW", "롯데", "스타벅스", "도미노", "맥도날드"};
    String[] MANUFACTURERS = {"삼성전자", "LG전자", "나이키코리아", "아모레퍼시픽", "현대자동차", "BMW코리아", "롯데제과",
        "스타벅스코리아", "도미노피자", "맥도날드코리아"};
    String[] STATUSES = Arrays.stream(ProductStatus.values()).map(Enum::name)
        .toArray(String[]::new);

    return ProductUploadCsvRow.of(
        randomSellerId(),
        randomChoice(CATEGORIES),
        randomChoice(PRODUCT_NAMES),
        randomDate(2021, 2024),
        randomDate(2025, 2027),
        randomChoice(STATUSES),
        randomChoice(BRANDS),
        randomChoice(MANUFACTURERS),
        randomSalesPrice(),
        randomStockQuantity()
    );
  }

  private static int randomStockQuantity() {
    return RANDOM.nextInt(1, 1001);
  }

  private static int randomSalesPrice() {
    return RANDOM.nextInt(10_000, 500_001);
  }

  private static String randomDate(int startYear, int endYear) {
    int year = RANDOM.nextInt(startYear, endYear + 1);
    int month = RANDOM.nextInt(1, 13);
    int day = RANDOM.nextInt(1, 29);
    return LocalDate.of(year, month, day).toString();
  }

  private static String randomChoice(String[] array) {
    return array[RANDOM.nextInt(array.length)];
  }

  private static Long randomSellerId() {
    return RANDOM.nextLong(1, 101);
  }
}
