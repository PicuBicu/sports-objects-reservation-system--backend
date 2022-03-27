package pl.picubicu.sportsobjectsreservationsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.picubicu.sportsobjectsreservationsystem.dto.UserResponseDto;
import pl.picubicu.sportsobjectsreservationsystem.service.AuthService;
import pl.picubicu.sportsobjectsreservationsystem.dto.RegistrationRequestDto;

import static pl.picubicu.sportsobjectsreservationsystem.message.SystemMessage.USER_CREATED;
import static pl.picubicu.sportsobjectsreservationsystem.message.SystemMessage.USER_LOGGED_SUCCESSFULLY;

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

    @GetMapping("/sign-in/email/{email}/password/{password}")
    public UserResponseDto signIn(@PathVariable("email") String email,
                                  @PathVariable("password") String password) {
        log.info("Email {} Password {}", email, password);
        UserResponseDto userResponseDto = this.authService.signIn(email, password);
        log.info(USER_LOGGED_SUCCESSFULLY);
        return userResponseDto;
    }
}
