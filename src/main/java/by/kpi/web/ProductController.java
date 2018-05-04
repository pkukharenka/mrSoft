package by.kpi.web;

import by.kpi.domain.Product;
import by.kpi.service.ProductService;
import by.kpi.utils.CsvTransform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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
    private static final String FILE_NAME = "products.csv";
    private final CsvTransform<Product> csv = new CsvTransform<>(Product.class);

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

    @GetMapping(path = "/download")
    public ResponseEntity<ByteArrayResource> download(HttpServletRequest request) {
        List<Product> products = this.productService.findAll();
        this.csv.beanToCsv(FILE_NAME, products);
        Path path = Paths.get(FILE_NAME);
        String contentType = request.getServletContext().getMimeType(FILE_NAME);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        ResponseEntity<ByteArrayResource> response = null;
        try {
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            response = ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .contentLength(resource.contentLength())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                    .body(resource);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return response;

    }

    @PostMapping(path = "/upload")
    public List<Product> upload(@RequestPart(value = "file") MultipartFile multipartFile) {
        return this.productService.saveAll(this.csv.csvToBean(multipartFile));
    }

}
