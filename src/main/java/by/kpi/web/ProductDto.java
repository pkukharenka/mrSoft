package by.kpi.web;


import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO comments
 *
 * @author Pyotr Kukharenka
 * @since 08.05.2018
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @CsvBindByName
    private Long id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private int count;

    @CsvBindByName
    private double price;

    @CsvBindByName
    private String description;

    @CsvBindByName
    private Long categoryId;
}
