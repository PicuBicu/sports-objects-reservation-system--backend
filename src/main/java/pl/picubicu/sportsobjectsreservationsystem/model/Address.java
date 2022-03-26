package pl.picubicu.sportsobjectsreservationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.picubicu.sportsobjectsreservationsystem.dto.RegistrationRequestDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String streetName;
    private String streetNumber;
    private String localNumber;
    private String cityName;

    public static Address fromRegistrationDto(RegistrationRequestDto request) {
        return Address.builder()
                .streetName(request.getStreetName())
                .streetNumber(request.getStreetNumber())
                .localNumber(request.getLocalNumber())
                .cityName(request.getCityName())
                .build();

    }
}
