package pl.picubicu.sportsobjectsreservationsystem.sportobject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@ToString
@Getter
@Setter
public class SportObjectDto {
    @NotBlank(message = "Sport object cannot be empty")
    private String name;
    @NotBlank(message = "Sport object cannot be initialized without image")
    private String imageName; // TODO: need to be file in near future
    @NotBlank(message = "Street name cannot be empty")
    private String streetName;
    @NotBlank(message = "Street number cannot be empty")
    private String streetNumber;
    @NotBlank(message = "Local number cannot be empty")
    private String localNumber;
    @NotBlank(message = "City name cannot be empty")
    private String cityName;
    @NotNull(message = "At least one category must be selected for sport object")
    private Set<Long> categoriesIds;
}
