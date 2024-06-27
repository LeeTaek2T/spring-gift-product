package gift;

import java.util.List;

public class Product {
  private Long id;
  private String name;
  private int price;
  private String imageUrl; // 새로 추가된 필드

  private List<Option> Options;

  public Product(Long id) {this.id=id;}
  public Product(){}

  public Product(Long id, String name, int price, String imageUrl, List<Option> options) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.imageUrl = imageUrl;
    this.Options=options;
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

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public List<Option> getOptions(){return this.Options;}

  public void setOptions(List<Option> options){this.Options=options;}
}
