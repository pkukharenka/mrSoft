package by.kpi.repository;

import by.kpi.domain.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Репозиторий, позволяющий осуществлять основные манипуляции с
 * объектами типа Category в базе данных
 *
 * @author Pyotr Kukharenka
 * @since 27.04.2018
 */

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
