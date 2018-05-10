package by.kpi.service;

import by.kpi.domain.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Описание основных методов бизнес логики с объектами типа Category
 *
 * @author Pyotr Kukharenka
 * @since 27.04.2018
 */
public interface CategoryService {

    Category save(Category category);
    void delete(Category category);
    List<Category> findAll();
    Optional<Category> findById(Long id);
    List<Category> saveAll(MultipartFile multipartFile);

}
