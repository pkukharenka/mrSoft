package by.kpi.service;

import by.kpi.domain.Category;
import by.kpi.domain.Product;
import by.kpi.repository.ProductRepository;
import by.kpi.utils.CsvTransform;
import by.kpi.web.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Pyotr Kukharenka
 * @since 27.04.2018
 */

@Slf4j
@Transactional
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CsvTransform<ProductDto> csv = new CsvTransform<>(ProductDto.class);

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public List<Product> saveAll(MultipartFile multipartFile) {
        List<Product> products = this.csv.csvToBean(multipartFile)
                .stream()
                .map(productDto -> new Product(
                        productDto.getId(),
                        productDto.getName(),
                        productDto.getCount(),
                        productDto.getPrice(),
                        productDto.getDescription(),
                        new Category(productDto.getCategoryId())))
                .collect(Collectors.toList());
        return this.productRepository.saveAll(products);
    }

    @Override
    public void delete(Product product) {
        this.productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }
}
