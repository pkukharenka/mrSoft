package by.kpi.web;

import by.kpi.domain.Category;
import by.kpi.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenFindAllCategoriesThenReturnAllRecords() throws Exception {
        final List<Category> categories = Lists.newArrayList(
                new Category(1L, "name1"),
                new Category(2L, "name2")
        );

        given(this.categoryService.findAll()).willReturn(categories);

        this.mvc.perform(get("/category/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(categories.get(0).getName())))
                .andExpect(content().string(this.mapper.writeValueAsString(categories)));
    }

    @Test
    public void whenAddNewCategoryThenReturnSameCategory() throws Exception {
        final Category category = new Category(1L, "name");

        given(this.categoryService.save(category)).willReturn(category);

        this.mvc.perform(MockMvcRequestBuilders.post("/category/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.mapper.writeValueAsString(category))
        )
                .andExpect(status().isOk())
                .andExpect(content().string(this.mapper.writeValueAsString(category)));
    }

    @Test
    public void whenDeleteCategoryThenVerifyOneInvoke() throws Exception {
        final Category category = new Category(1L, "name");

        given(this.categoryService.findById(1L)).willReturn(Optional.of(category));
        doNothing().when(this.categoryService).delete(category);

        this.mvc.perform(post("/category/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.mapper.writeValueAsString(1L)))
                .andExpect(status().isOk());

        verify(this.categoryService, times(1)).delete(category);
    }
}