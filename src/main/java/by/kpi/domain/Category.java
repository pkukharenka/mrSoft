package by.kpi.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс-сущность категории
 *
 * @author Pyotr Kukharenka
 * @since 27.04.2018
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@BatchSize(size = 10)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName
    private Long id;

    @Column(name = "CATEGORY_NAME", nullable = false, length = 100, unique = true)
    @CsvBindByName
    private String name;

}
