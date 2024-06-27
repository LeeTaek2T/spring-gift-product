package gift;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductControllerTest {

  private ProductController productController;
  private List<Option> options;
  private List<Option> options2;
  @BeforeEach
  public void setUp() {
    ProductService productService = new ProductService();
    productController = new ProductController(productService);
    options=new ArrayList<>();
    options2=new ArrayList<>();
    Option optionA = new Option(1L,"A타입",300,"쓴 맛");
    Option optionB = new Option(2L,"B타입",400,"신 맛");
    options.add(optionA);
    options.add(optionB);
    Option optionC = new Option(1L,"두유",500,"두유로 변경");
    Option optionD = new Option(1L,"물 반 우유 반",500,"물 반 우유 반으로 변경");
    options2.add(optionC);
    options2.add(optionD);
  }

  @Test
  public void testGetAllProducts() {


    // 제품 추가
    Product product1 = new Product(1L, "Product 1", 100, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",options);
    Product product2 = new Product(2L, "Product 2", 200, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",null);
    productController.addProduct(product1);
    productController.addProduct(product2);

    // getAllProducts() 호출
    List<Product> returnedProducts = productController.getAllProducts();

    // 반환된 제품 리스트 검증
    assertEquals(2, returnedProducts.size());
    assertEquals(product1.getId(), returnedProducts.get(0).getId());
    assertEquals(product1.getName(), returnedProducts.get(0).getName());
    assertEquals(product1.getPrice(), returnedProducts.get(0).getPrice());
    assertEquals(product1.getImageUrl(), returnedProducts.get(0).getImageUrl());
    assertEquals(product1.getOptions(), returnedProducts.get(0).getOptions());
    assertEquals(product2.getId(), returnedProducts.get(1).getId());
    assertEquals(product2.getName(), returnedProducts.get(1).getName());
    assertEquals(product2.getPrice(), returnedProducts.get(1).getPrice());
    assertEquals(product2.getImageUrl(), returnedProducts.get(1).getImageUrl());
    assertEquals(product2.getOptions(), returnedProducts.get(1).getOptions());

  }

  @Test
  public void testGetProductById() {
    // 제품 추가
    Product product = new Product(1L, "Product 1", 1000, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",options);
    productController.addProduct(product);

    // getProductById() 호출 - 존재하는 제품 ID
    ResponseEntity<Product> responseEntity = productController.getProductById(1L);

    // 반환된 ResponseEntity 검증
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // 상태 코드가 200 OK인지 확인

    // 반환된 제품 검증
    Product returnedProduct = responseEntity.getBody();
    assertEquals(product.getId(), returnedProduct.getId());
    assertEquals(product.getName(), returnedProduct.getName());
    assertEquals(product.getPrice(), returnedProduct.getPrice());
    assertEquals(product.getImageUrl(), returnedProduct.getImageUrl());
  }

  @Test
  public void testAddProduct() {
    Product newProduct = new Product(1L, "아이스 카페 아메리카노 T", 4500, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",options);

    Product addedProduct = productController.addProduct(newProduct);

    assertNotNull(addedProduct);
    assertNotNull(addedProduct.getId());
    assertEquals("아이스 카페 아메리카노 T", addedProduct.getName());
    assertEquals(4500, addedProduct.getPrice());
    assertEquals("https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg", addedProduct.getImageUrl());
    assertEquals(options.get(0).getName(), addedProduct.getOptions().get(0).getName());

  }

  @Test
  void testUpdateProduct() {
    // 기존 제품 추가
    Product existingProduct = new Product(1L, "아이스 카페 아메리카노 T", 4500, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",options);
    productController.addProduct(existingProduct);

    // 업데이트할 제품 정보
    Product updatedProduct = new Product(1L, "카페라떼 T", 6000, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",options2);
    // 제품 업데이트 요청
    ResponseEntity<Product> response = productController.updateProduct(1L, updatedProduct);
    // 업데이트된 제품 받아오기
    Product returnedProduct = response.getBody();

    assertNotNull(returnedProduct);
    assertEquals(1L, returnedProduct.getId());
    assertEquals("카페라떼 T", returnedProduct.getName());
    assertEquals(6000, returnedProduct.getPrice());
    assertEquals("https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg", returnedProduct.getImageUrl());
    assertEquals(options2.get(0).getName(), returnedProduct.getOptions().get(0).getName());
  }

  @Test
  public void testDeleteProduct() {
    // 제품 추가
    Product product = new Product(1L, "아이스 카페 아메리카노 T", 4500, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",options);
    productController.addProduct(product);

    // deleteProduct() 호출 - 존재하는 제품 ID
    ResponseEntity<Void> responseEntity = productController.deleteProduct(1L);

    // 반환된 ResponseEntity 검증
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode()); // 상태 코드가 204 NO CONTENT인지 확인

    // ProductService를 통해 제품이 삭제되었는지 확인
    ResponseEntity<Product> response = productController.getProductById(1L);
  }
}
