package by.kpi.utils;

import by.kpi.domain.Category;
import by.kpi.domain.Product;
import org.assertj.core.util.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class CsvTransformTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private final CsvTransform<Category> csv = new CsvTransform<>(Category.class);

    @Test
    public void whenConvertBeanToCsvThenFileExist() throws IOException {
        final List<Category> categories = Lists.newArrayList(
                new Category(1L, "name1"),
                new Category(2L, "name2")
        );
        final Path filename = this.folder.newFile("categories.csv").toPath();
        this.csv.beanToCsv(filename, categories);
        assertTrue(Files.exists(filename));
    }

    @Test
    public void whenConvertCsvToBeanThenTwoRecords() throws IOException {
        final InputStream is = ClassLoader.getSystemResourceAsStream("test.csv");
        final MockMultipartFile multipartFile = new MockMultipartFile("file", "test.csv", "text/csv", is);
        final List<Category> categories = this.csv.csvToBean(multipartFile);
        assertThat(categories.size(), is(2));
        assertThat(categories.get(0).getName(), is("name1"));
    }

    @Test
    public void whenConvertCsvToBeanAndBeanToCsvThenResultMatch() throws IOException {
        final List<Category> categories = Lists.newArrayList(
                new Category(1L, "name1"),
                new Category(2L, "name2")
        );
        final Path filename = this.folder.newFile("categories.csv").toPath();
        this.csv.beanToCsv(filename, categories);

        final InputStream is = new FileInputStream(filename.toFile());
        final MockMultipartFile multipartFile = new MockMultipartFile("file", filename.toFile().getName(), "text/csv", is);
        final List<Category> actual = this.csv.csvToBean(multipartFile);
        assertThat(actual.size(), is(2));
        assertThat(actual.get(0).getName(), is("name1"));
        assertThat(actual.get(1).getName(), is("name2"));
    }
}