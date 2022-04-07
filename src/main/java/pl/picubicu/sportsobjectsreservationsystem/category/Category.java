package pl.picubicu.sportsobjectsreservationsystem.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.picubicu.sportsobjectsreservationsystem.sportobject.SportObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Set;

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

    @JsonBackReference
    @ManyToMany(mappedBy = "categories")
    private Set<SportObject> sportObjects;

}
