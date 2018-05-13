package by.kpi.web;

import by.kpi.domain.Product;
import by.kpi.service.ProductService;
import by.kpi.utils.CsvTransform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Pyotr Kukharenka
 * @since 27.04.2018
 */

@RestController
@CrossOrigin
@RequestMapping("/product")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private static final String FILE_NAME = "product.csv";
    private final CsvTransform<ProductDto> csv = new CsvTransform<>(ProductDto.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> getProducts() {
        return this.productService.findAll();
    }

    @PostMapping("/")
    public Product save(@RequestBody Product product) {
        return this.productService.save(product);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody Long id) {
        final Optional<Product> product = this.productService.findById(id);
        product.ifPresent(this.productService::delete);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download() throws IOException {
        List<ProductDto> products = this.productService.findAll().stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getCount(),
                        product.getPrice(),
                        product.getDescription(),
                        product.getCategory().getId()))
                .collect(Collectors.toList());
        this.csv.beanToCsv(FILE_NAME, products);
        Path path = Paths.get(FILE_NAME);
        Resource resource = new ByteArrayResource(Files.readAllBytes(path));
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .headers(headers)
                .body(resource);
    }

    @PostMapping("/upload")
    public List<Product> uploadFiles(@RequestPart("file") MultipartFile file) {
        String[] fileParts = file.getOriginalFilename().split("\\.");
        if (!fileParts[fileParts.length - 1].equalsIgnoreCase("csv")) {
            throw new RuntimeException("File {} has invalid extension.");
        }
        return this.productService.saveAll(file);
    }

}
