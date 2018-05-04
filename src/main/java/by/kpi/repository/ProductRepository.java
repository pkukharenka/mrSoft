package by.kpi.repository;

import by.kpi.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий, позволяющий осуществлять основные манипуляции с
 * объектами типа Product в базе данных
 *
 * @author Pyotr Kukharenka
 * @since 27.04.2018
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
