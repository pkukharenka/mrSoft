package by.kpi.web;

import by.kpi.domain.Category;
import by.kpi.domain.Product;
import by.kpi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenGetAllProductsThenExpectValidJson() throws Exception {
        final List<Product> products = Lists.newArrayList(
                new Product(1L, "name", 1, 2d, "description", new Category(1L))
        );
        given(this.productService.findAll()).willReturn(products);
        this.mvc.perform(get("/product/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(products.get(0).getName())))
                .andExpect(content().string(this.mapper.writeValueAsString(products)));
    }

    @Test
    public void whenAddNewProductThenExpectThisProduct() throws Exception {
        final Product product = new Product(1L, "name", 1, 2d, "description", new Category(1L));
        given(this.productService.save(product)).willReturn(product);
        this.mvc.perform(post("/product/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.mapper.writeValueAsString(product))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(this.mapper.writeValueAsString(product)));
    }

    @Test
    public void whenDeleteThen() throws Exception {
        final Product product = new Product(1L, "name", 1, 2d, "description", new Category(1L));
        given(this.productService.findById(1L)).willReturn(Optional.of(product));
        doNothing().when(this.productService).delete(product);

        this.mvc.perform(post("/product/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.mapper.writeValueAsString(1)))
                .andExpect(status().isOk());

        verify(this.productService, times(1)).delete(product);
        verify(this.productService, times(1)).findById(1L);
    }


    @Test
    public void whenUploadCsvFileThenReturnListOfProducts() throws Exception {
        final List<Product> products = Lists.newArrayList(
                new Product(1L, "name", 3, 12d, "desc", new Category(3L))
        );
        final InputStream is = ClassLoader.getSystemResourceAsStream("test.csv");
        final MockMultipartFile multipartFile = new MockMultipartFile("file", "test.csv", "text/csv", is);

        given(this.productService.saveAll(multipartFile)).willReturn(products);

        this.mvc.perform(multipart("/product/upload").file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(content().string(this.mapper.writeValueAsString(products)));
    }

    @Test
    public void whenUploadInvalidFileThenException() throws Exception {
        final InputStream is = ClassLoader.getSystemResourceAsStream("invalid.txt");
        final MockMultipartFile multipartFile = new MockMultipartFile("file", "invalid.txt", "text/plain", is);

        assertThatThrownBy(() ->
                this.mvc.perform(multipart("/product/upload").file(multipartFile))).hasCauseInstanceOf(RuntimeException.class);
    }

    @Test
    public void whenDownloadFileThenReturnAllExpectedBytes() throws Exception {
        final List<Product> products = Lists.newArrayList(
                new Product(1L, "name1", 1, 1d, "desc1", new Category(1L)),
                new Product(2L, "name2", 2, 2d, "desc2", new Category(2L))
        );
        given(this.productService.findAll()).willReturn(products);
        Path path = Paths.get(ClassLoader.getSystemResource("test_product.csv").toURI());
        this.mvc.perform(get("/product/download/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"))
                .andExpect(content().bytes(Files.readAllBytes(path)));
    }

}