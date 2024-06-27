package gift;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/products")
public class ProductAdminController {

  private final ProductService productService;
  private final OptionService optionService; // OptionService 추가

  @Autowired
  public ProductAdminController(ProductService productService, OptionService optionService) {
    this.productService = productService;
    this.optionService = optionService;
  }

  @GetMapping
  public String listProducts(Model model) {
    model.addAttribute("products", productService.getAllProducts());
    return "product-list";
  }

  @GetMapping("/new")
  public String newProductForm(Model model) {
    model.addAttribute("product", new Product(productService.getNextId()));
    return "add-product-form";
  }

  @PostMapping("/add")
  public String addProduct(@ModelAttribute Product product) {
    productService.addProduct(product);
    return "redirect:/products";
  }

  @GetMapping("/edit/{id}")
  public String editProductForm(@PathVariable Long id, Model model) {
    Product product = productService.getProductById(id);
    if (product != null) {
      model.addAttribute("product", product);
      return "edit-product-form";
    } else {
      return "redirect:/products";
    }
  }

  @PostMapping("/edit/{id}")
  public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
    productService.updateProduct(id, product);
    return "redirect:/products";
  }

  @PostMapping("/delete/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return "redirect:/products";
  }

  // 옵션 관련 기능 추가

  @GetMapping("/{productId}/options")
  public String listOptions(@PathVariable Long productId, Model model) {
    Product product = productService.getProductById(productId);
    if (product != null) {
      model.addAttribute("product", product);
      model.addAttribute("options", product.getOptions());
      return "option-list";
    } else {
      return "redirect:/products";
    }
  }

  @GetMapping("/{productId}/options/new")
  public String newOptionForm(@PathVariable Long productId, Model model) {
    Product product = productService.getProductById(productId);
    if (product != null) {
      model.addAttribute("product", product);
      model.addAttribute("option", new Option());
      return "option-form";
    } else {
      return "redirect:/products";
    }
  }

  @PostMapping("/{productId}/options/add")
  public String addOption(@PathVariable Long productId, @ModelAttribute Option option) {
    Product product = productService.getProductById(productId);
    List<Option> initOptions = new ArrayList<>();
    initOptions.add(option);
    if (product != null) {
      product.setOptions(initOptions);
      productService.updateProduct(productId, product);
    }
    return "redirect:/products/{productId}/options";
  }

  @GetMapping("/{productId}/options/edit/{optionId}")
  public String editOptionForm(@PathVariable Long productId, @PathVariable Long optionId, Model model) {
    Option option = optionService.getOptionById(productId, optionId);
    if (option != null) {
      model.addAttribute("product", productService.getProductById(productId));
      model.addAttribute("option", option);
      return "option-form";
    } else {
      return "redirect:/products/{productId}/options";
    }
  }

  @PostMapping("/{productId}/options/edit/{optionId}")
  public String updateOption(@PathVariable Long productId, @PathVariable Long optionId, @ModelAttribute Option updatedOption) {
    optionService.updateOption(productId, optionId, updatedOption);
    return "redirect:/products/{productId}/options";
  }

  @PostMapping("/{productId}/options/delete/{optionId}")
  public String deleteOption(@PathVariable Long productId, @PathVariable Long optionId) {
    optionService.deleteOption(productId, optionId);
    return "redirect:/products/{productId}/options";
  }
}
