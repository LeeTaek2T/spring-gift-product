package gift;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

  private final ProductService productService;

  @Autowired
  public OptionService(ProductService productService) {
    this.productService = productService;
  }

  public List<Option> getAllOptions(Long productId) {
    Product product = productService.getProductById(productId);
    if (product != null) {
      return product.getOptions();
    }
    return null;
  }

  public Option getOptionById(Long productId, Long optionId) {
    Product product = productService.getProductById(productId);
    for (Option option : product.getOptions()) {
      if (option.getId().equals(optionId)) {
        return option;
      }
    }
    return null;
  }

  public Option addOption(Long productId, Option option) {
    Product product = productService.getProductById(productId);
    if (product != null) {
      product.getOptions().add(option);
      productService.updateProduct(productId, product);
      return option;
    }
    return null;
  }

  public Option updateOption(Long productId, Long optionId, Option updatedOption) {
    Product product = productService.getProductById(productId);
    List<Option> options = product.getOptions();
    for (int i = 0; i < options.size(); i++) {
      if (options.get(i).getId().equals(optionId)) {
        updatedOption.setId(optionId);
        options.set(i, updatedOption);
        productService.updateProduct(productId, product);
        return updatedOption;
      }
    }
    return null;
  }

  public Option deleteOption(Long productId, Long optionId) {
    Product product = productService.getProductById(productId);
    Option optionToDelete = null;
    List<Option> options = product.getOptions();
    for (int i = 0; i < options.size(); i++) {
      if (options.get(i).getId().equals(optionId)) {
        optionToDelete = options.get(i);
        options.remove(i);
        productService.updateProduct(productId, product);
        break;
      }
    }
    return optionToDelete;
  }

}
