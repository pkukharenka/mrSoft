package by.kpi.service;

import by.kpi.domain.Category;
import by.kpi.repository.CategoryRepository;
import by.kpi.utils.CsvTransform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @author Pyotr Kukharenka
 * @since 27.04.2018
 */

@Service
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private final CsvTransform<Category> csv = new CsvTransform<>(Category.class);

    @Override
    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        this.categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return this.categoryRepository.findById(id);
    }

    @Override
    public List<Category> saveAll(MultipartFile multipartFile) {
        List<Category> categories = this.csv.csvToBean(multipartFile);
        return this.categoryRepository.saveAll(categories);
    }
}
