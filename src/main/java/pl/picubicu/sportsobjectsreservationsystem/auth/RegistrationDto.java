package pl.picubicu.sportsobjectsreservationsystem.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegistrationDto {
    @NotBlank
    private String firstName;
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;
    @NotBlank(message = "Street name cannot be empty")
    private String streetName;
    @NotBlank(message = "Street number cannot be empty")
    private String streetNumber;
    @NotBlank(message = "Local number cannot be empty")
    private String localNumber;
    @NotBlank(message = "City name cannot be empty")
    private String cityName;
    @Pattern(message = "Should contain at least 7 do 9 digits",
            regexp = "[0-9]{7,9}")
    private String phoneNumber;
    @Email(message = "Should be well formatted email")
    private String email;
    @Pattern(message = "Password must contain at least one digit, one lowercase and uppercase letter, one special character and contain at least 8 to 20 characters.\n",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
    private String password;
}
