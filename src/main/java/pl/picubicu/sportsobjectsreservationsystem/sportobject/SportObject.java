package pl.picubicu.sportsobjectsreservationsystem.sportobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.picubicu.sportsobjectsreservationsystem.address.Address;
import pl.picubicu.sportsobjectsreservationsystem.category.Category;
import pl.picubicu.sportsobjectsreservationsystem.reservation.Reservation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "sport_objects")
@Entity(name = "SportObject")
public class SportObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageName;

    @OneToOne
    private Address address;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "sport_object_id"))
    @Column(nullable = false)
    private Set<Category> categories;

    @OneToMany(mappedBy = "sportObject")
    private List<Reservation> reservations;

    public static SportObject fromSportObjectDto(SportObjectDto sportObjectDto) {
        return SportObject.builder()
                .name(sportObjectDto.getName())
                .imageName(sportObjectDto.getImageName())
                .build();
    }
}
