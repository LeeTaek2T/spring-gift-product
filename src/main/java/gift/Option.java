package gift;

public class Option {
  private Long id;
  private String name;
  private int additionalPrice; // 옵션에 대한 추가 가격
  private String description;

  public Option(){};
  public Option(Long id, String name, int additionalPrice, String description) {
    this.id = id;
    this.name = name;
    this.additionalPrice = additionalPrice;
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAdditionalPrice() {
    return additionalPrice;
  }

  public void setAdditionalPrice(int additionalPrice) {
    this.additionalPrice = additionalPrice;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
