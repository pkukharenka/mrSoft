package by.kpi.web;

import by.kpi.MrTestApplication;
import by.kpi.domain.Category;
import by.kpi.domain.Product;
import by.kpi.repository.CategoryRepository;
import by.kpi.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MrTestApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void initData() {
        this.categoryRepository.save(new Category(1L, "first"));
        this.productRepository.deleteAllInBatch();
    }

    @Test
    public void whenFindAllProductsThenReturnOne() throws Exception {
        final Product product = new Product(1L, "name", 1, 2d, "description", new Category(1L));
        this.productRepository.save(product);

        this.mvc.perform(get("/product/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void whenAddNewProductThenReturnThisProduct() throws Exception {
        final Product product = new Product(2L, "name", 1, 2d, "description", new Category(1L));

        this.mvc.perform(post("/product/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.mapper.writeValueAsString(product))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.count", is(1)))
                .andExpect(jsonPath("$.category.name", is("first")));
        assertThat(this.productRepository.findAll().size(), is(1));
    }

    @Test
    public void whenDeleteProductThenCountOfProductsIsOne() throws Exception {
        final List<Product> products = Lists.newArrayList(
                new Product(1L, "name1", 1, 2d, "description1", new Category(1L)),
                new Product(2L, "name2", 1, 2d, "description2", new Category(1L))
        );
        this.productRepository.saveAll(products);

        this.mvc.perform(post("/product/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.mapper.writeValueAsString(1L))
        )
                .andExpect(status().isOk());
        assertThat(this.productRepository.findAll().size(), is(1));
    }
}