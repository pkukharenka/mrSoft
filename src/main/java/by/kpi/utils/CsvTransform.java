package by.kpi.utils;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Утилитный класс для парсинга объектов в (из) csv.
 *
 * @author Pyotr Kukharenka
 * @since 02.05.2018
 */

@Slf4j
public class CsvTransform<T> {

    private final Class<T> entityClass;

    public CsvTransform(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Парсит объекты типа T в csv файл.
     *
     * @param filename - имя файла, который будет создан
     * @param objects  - список обхектов для парсинга.
     */
    public void beanToCsv(final String filename, List<T> objects) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(filename))) {
            StatefulBeanToCsv<T> toCsv = new StatefulBeanToCsvBuilder<T>(writer).build();
            toCsv.write(objects);
            log.info("Csv write to file {} is successful. Write - {} records.", filename, objects.size());
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Возвращает список объектов типа Т, полученных из csv файла
     *
     * @param multipartFile - файл, полученный от клиента.
     * @return - список объектов необходимого типа
     */
    public List<T> csvToBean(final MultipartFile multipartFile) {
        List<T> list = new ArrayList<>();
        final File file = this.convertMultipartFile(multipartFile);
        try (Reader reader = Files.newBufferedReader(Paths.get(file.getName()))) {
            CsvToBean<T> fromCsv = new CsvToBeanBuilder<T>(reader).withType(this.entityClass).build();
            list = fromCsv.parse();
            log.info("Read from file {} is successful. Read - {} records.", file.getName(), list.size());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * Возвращает объект типа File, полученный из загружаемого пользователем
     * файла.
     *
     * @param multipartFile - файл, загружаемый пользователем
     * @return - объект типа File
     */
    private File convertMultipartFile(final MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
            log.info("MultiPart file is successful converted to File {}. File size - {}, file absolute path - {}.", file.getName(), file.length(),
                    file.getAbsolutePath());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return file;
    }
}
