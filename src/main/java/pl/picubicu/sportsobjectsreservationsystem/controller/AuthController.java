package pl.picubicu.sportsobjectsreservationsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.picubicu.sportsobjectsreservationsystem.AuthService;
import pl.picubicu.sportsobjectsreservationsystem.dto.RegistrationRequestDto;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody RegistrationRequestDto registrationRequestDto) {
        log.info(registrationRequestDto.toString());
        this.authService.signUp(registrationRequestDto);
    }
}
