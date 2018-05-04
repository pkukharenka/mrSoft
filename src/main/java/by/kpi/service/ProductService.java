package by.kpi.service;

import by.kpi.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Описание основных методов бизнес логики с объектами типа Product
 *
 * @author Pyotr Kukharenka
 * @since 27.04.2018
 */

public interface ProductService {

    Product save(Product product);
    List<Product> saveAll(List<Product> products);
    void delete(Product product);
    List<Product> findAll();
    Optional<Product> findById(Long id);
}
