package pl.picubicu.sportsobjectsreservationsystem.dto;

import lombok.Data;

@Data
public class RegistrationRequestDto {
    private String firstName;
    private String lastName;
    private String password;
}
