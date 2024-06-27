package gift;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private ProductService productService;
  private OptionService optionService;

  @Autowired
  public ProductController(ProductService productService, OptionService optionService) {
    this.productService = productService;
    this.optionService = optionService;
  }

  @GetMapping
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    Product product = productService.getProductById(id);
    if (product != null) {
      return ResponseEntity.ok(product);
    }
    return ResponseEntity.notFound().build();
  }


  @PostMapping
  public Product addProduct(@RequestBody Product product) {
    return productService.addProduct(product);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable Long id,
    @RequestBody Product updatedProduct) {
    Product product = productService.updateProduct(id, updatedProduct);
    if (product != null) {
      return ResponseEntity.ok(product);
    }
    return ResponseEntity.notFound().build();
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    Product product = productService.deleteProduct(id);
    if (product != null) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  // Option 관련 메소드 추가
  @GetMapping("/{productId}/options")
  public ResponseEntity<List<Option>> getOptionsByProductId(@PathVariable Long productId) {
    Product product = productService.getProductById(productId);
    if (product != null) {
      return ResponseEntity.ok(product.getOptions());
    }
    return ResponseEntity.notFound().build();
  }


  @PostMapping("/{productId}/options")
  public ResponseEntity<Option> addOptionToProduct(@PathVariable Long productId,
    @RequestBody Option option) {
    Product product = productService.getProductById(productId);
    if (product != null) {
      product.getOptions().add(option);
      productService.updateProduct(productId, product);
      return ResponseEntity.ok(option);
    }
    return ResponseEntity.notFound().build();
  }


  @PutMapping("/{productId}/options/{optionId}")
  public ResponseEntity<Option> updateOption(@PathVariable Long productId,
    @PathVariable Long optionId, @RequestBody Option updatedOption) {
    Product product = productService.getProductById(productId);
    List<Option> options = product.getOptions();
    for (int i = 0; i < options.size(); i++) {
      if (options.get(i).getId().equals(optionId)) {
        options.set(i, updatedOption);
        productService.updateProduct(productId, product);
        return ResponseEntity.ok(updatedOption);
      }
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{productId}/options/{optionId}")
  public ResponseEntity<Void> deleteOption(@PathVariable Long productId,
    @PathVariable Long optionId) {
    Product product = productService.getProductById(productId);
    if (product != null) {
      List<Option> options = product.getOptions();
      options.removeIf(option -> option.getId().equals(optionId));
      productService.updateProduct(productId, product);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}
