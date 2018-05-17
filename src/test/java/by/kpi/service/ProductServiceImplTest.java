package by.kpi.service;

import by.kpi.domain.Category;
import by.kpi.domain.Product;
import by.kpi.repository.ProductRepository;
import by.kpi.utils.CsvTransform;
import by.kpi.web.ProductDto;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @TestConfiguration
    static class ProductServiceImplTestConfiguration {

        @Bean
        public ProductService productService() {
            return new ProductServiceImpl();
        }
    }

    @Autowired
    private ProductService service;

    @MockBean
    private ProductRepository repository;

    @Test
    public void whenAddNewProductThenReturnNewProduct() {
        final Product product = new Product(1L, "name", 1, 2d, "description", new Category(1L));
        given(this.repository.save(product)).willReturn(product);
        final Product actual = service.save(product);
        assertThat(actual.getName(), is(product.getName()));
    }

    @Test
    public void whenFindAllThenReturnTwoRecords() {
        final List<Product> products = Lists.newArrayList(
                new Product(1L, "name1", 1, 2d, "description1", new Category(1L)),
                new Product(2L, "name2", 1, 2d, "description2", new Category(1L))
        );
        given(this.repository.findAll()).willReturn(products);
        final List<Product> actualProducts = this.service.findAll();
        assertThat(actualProducts.size(), is(2));
        assertThat(actualProducts.get(0).getName(), is("name1"));
        assertThat(actualProducts.get(1).getName(), is("name2"));
    }

    @Test
    public void whenFindProductByIdThenReturnSameProduct() {
        final Product product = new Product(1L, "name", 1, 2d, "description", new Category(1L));
        given(this.repository.findById(1L)).willReturn(Optional.of(product));
        final Optional<Product> actual = this.service.findById(1L);
        assertTrue(actual.isPresent());
        assertThat(actual.get().getName(), is("name"));
    }

    @Test
    public void whenDeleteThenVerifyOniTimeInvoke() {
        final Product product = new Product();
        doNothing().when(this.repository).delete(product);
        this.service.delete(product);
        verify(this.repository, times(1)).delete(product);
    }

    @Test
    public void whenSaveAllRecordsThenReturnListOfTwoRecords() throws IOException {
        final List<Product> expectProducts = Lists.newArrayList(
                new Product(1L, "name1", 1, 1d, "desc1", new Category(1L)),
                new Product(2L, "name2", 2, 2d, "desc2", new Category(2L))
        );
        final InputStream is = ClassLoader.getSystemResourceAsStream("test_product.csv");
        final MockMultipartFile multipartFile = new MockMultipartFile("file", "test_product.csv", "text/csv", is);
        CsvTransform<ProductDto> csv = new CsvTransform<>(ProductDto.class);
        final List<Product> productsFromCsv = csv.csvToBean(multipartFile)
                .stream()
                .map(productDto -> new Product(
                        productDto.getId(),
                        productDto.getName(),
                        productDto.getCount(),
                        productDto.getPrice(),
                        productDto.getDescription(),
                        new Category(productDto.getCategoryId())))
                .collect(Collectors.toList());

        given(this.repository.saveAll(productsFromCsv)).willReturn(expectProducts);
        final List<Product> actualProducts = this.service.saveAll(multipartFile);
        assertThat(actualProducts.size(), is(2));
        assertThat(actualProducts.get(0).getName(), is("name1"));
        assertThat(actualProducts.get(1).getName(), is("name2"));
    }
}