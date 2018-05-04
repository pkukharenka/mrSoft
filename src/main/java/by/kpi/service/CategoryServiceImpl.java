package by.kpi.service;

import by.kpi.domain.Category;
import by.kpi.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Pyotr Kukharenka
 * @since 27.04.2018
 */

@Service
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

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
}
