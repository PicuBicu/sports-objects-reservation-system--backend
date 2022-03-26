package pl.picubicu.sportsobjectsreservationsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.picubicu.sportsobjectsreservationsystem.service.AuthService;
import pl.picubicu.sportsobjectsreservationsystem.dto.RegistrationRequestDto;

import static pl.picubicu.sportsobjectsreservationsystem.message.SystemMessage.USER_CREATED;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody RegistrationRequestDto registrationRequestDto) {
        log.info(registrationRequestDto.toString());
        String message = this.authService.signUp(registrationRequestDto);
        log.info(USER_CREATED);
        return message;
    }
}
