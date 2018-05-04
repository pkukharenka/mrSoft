package by.kpi.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс-сущность продукт
 *
 * @author Pyotr Kukharenka
 * @since 27.04.2018
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName
    private Long id;

    @Column(name= "PRODUCT_NAME", nullable = false)
    @CsvBindByName
    private String name;

    @Column(name = "COUNT", nullable = false)
    @CsvBindByName
    private int count;

    @Column(name = "PRICE", nullable = false)
    @CsvBindByName
    private double price;

    @Column(name = "DESCRIPTION", length = 1000)
    @CsvBindByName
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID")
    @CsvBindByName
    private Category category;
}
