package pl.picubicu.sportsobjectsreservationsystem.auth;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class LoginDto {
    @Email(message = "Email is not valid")
    private String email;
    private String password;
}
