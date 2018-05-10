package by.kpi.web;

import by.kpi.domain.Category;
import by.kpi.domain.Product;
import by.kpi.service.CategoryService;
import by.kpi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * TODO comments
 *
 * @author Pyotr Kukharenka
 * @since 10.05.2018
 */

@RestController
@Slf4j
@RequestMapping("/load")
public class LoadController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public LoadController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @PostMapping("/")
    public String uploadFiles(@RequestPart("files") MultipartFile[] files) {
        for (MultipartFile file : files) {
            if ("category.csv".equalsIgnoreCase(file.getOriginalFilename())) {
                this.categoryService.saveAll(file);
            } else if ("product.csv".equalsIgnoreCase(file.getOriginalFilename())) {
                this.productService.saveAll(file);
            }
        }
        return null;
    }

}
