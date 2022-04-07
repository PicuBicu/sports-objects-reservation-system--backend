package pl.picubicu.sportsobjectsreservationsystem.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.picubicu.sportsobjectsreservationsystem.auth.RegistrationRequestDto;
import pl.picubicu.sportsobjectsreservationsystem.sportobject.SportObjectDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Address")
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

    public static Address fromSportObjectDto(SportObjectDto request) {
        return Address.builder()
                .streetName(request.getStreetName())
                .streetNumber(request.getStreetNumber())
                .localNumber(request.getLocalNumber())
                .cityName(request.getCityName())
                .build();

    }
}
