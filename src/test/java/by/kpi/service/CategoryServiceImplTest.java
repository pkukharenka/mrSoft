package by.kpi.service;

import by.kpi.domain.Category;
import by.kpi.repository.CategoryRepository;
import by.kpi.utils.CsvTransform;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CategoryServiceImplTest {

   @TestConfiguration
   static class CategoryServiceImplTestConfiguration {

       @Bean
       public CategoryService categoryService() {
           return new CategoryServiceImpl();
       }
   }

    @Autowired
    private CategoryService service;

    @MockBean
    private CategoryRepository repository;

    @Test
    public void whenAddNewCategoryThenReturnThisCategory() {
        final Category category = new Category(1L, "name");

        when(this.repository.save(category)).thenReturn(category);
        final Category actual = this.service.save(category);

        assertThat(actual.getName(), is("name"));
        verify(this.repository, times(1)).save(category);
    }

    @Test
    public void whenFindCategoryByIdThenReturnSameCategory() {
        final Category category = new Category(1L, "name");

        when(this.repository.findById(category.getId())).thenReturn(Optional.of(category));
        final Optional<Category> actual = this.service.findById(1L);

        assertTrue(actual.isPresent());
        assertThat(actual.get().getName(), is("name"));
        verify(this.repository, times(1)).findById(1L);
    }

    @Test
    public void whenFindAllCategoriesThenReturnTwoRecords() {
        final List<Category> categories = Lists.newArrayList(
                new Category(1L, "name1"),
                new Category(2L, "name2")
        );

        when(this.repository.findAll()).thenReturn(categories);
        final List<Category> actual = this.service.findAll();

        assertThat(actual.size(), is(2));
        assertThat(actual.get(0).getName(), is("name1"));
        assertThat(actual.get(1).getName(), is("name2"));
        verify(this.repository, times(1)).findAll();
    }

    @Test
    public void whenDeleteCategoryThenOneTimeInvokeDelete() {
        final Category category = new Category(1L, "name");

        doNothing().when(this.repository).delete(category);
        this.service.delete(category);

        verify(this.repository, times(1)).delete(category);
    }

    @Test
    public void whenSaveAllCategoriesThenReturnTwoRecords() throws IOException {
        final CsvTransform<Category> csv = new CsvTransform<>(Category.class);
        final InputStream is = ClassLoader.getSystemResourceAsStream("test.csv");
        final MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", is);
        final List<Category> categoriesFromCsv = csv.csvToBean(file);
        final List<Category> expectCategories = Lists.newArrayList(
                new Category(1L, "name1"),
                new Category(2L, "name2")
        );

        when(this.repository.saveAll(categoriesFromCsv)).thenReturn(expectCategories);
        final List<Category> actual = this.service.saveAll(file);

        assertThat(actual.size(), is(2));
        assertThat(actual.get(0).getName(), is("name1"));
        assertThat(actual.get(1).getName(), is("name2"));
        verify(this.repository, times(1)).saveAll(categoriesFromCsv);
    }
}