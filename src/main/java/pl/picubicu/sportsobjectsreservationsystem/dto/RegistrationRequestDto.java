package pl.picubicu.sportsobjectsreservationsystem.dto;

import lombok.Data;

@Data
public class RegistrationRequestDto {
    private String firstName;
    private String lastName;
    private String streetName;
    private String streetNumber;
    private String localNumber;
    private String cityName;
    private String phoneNumber;
    private String email;
    private String password;
}
