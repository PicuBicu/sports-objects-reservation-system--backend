package pl.picubicu.sportsobjectsreservationsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@NoArgsConstructor
@Data
@Entity(name = "Category")
@Table(name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(name = "category_name_unique_constraint",
                        columnNames = {
                                "name"
                        })
        })
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
