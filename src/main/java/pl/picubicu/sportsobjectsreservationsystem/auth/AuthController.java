package pl.picubicu.sportsobjectsreservationsystem.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.picubicu.sportsobjectsreservationsystem.user.UserResponseDto;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import static pl.picubicu.sportsobjectsreservationsystem.custom.SystemMessage.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/auth")
@Validated
@RestController
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public String signUp(@Valid @RequestBody RegistrationRequestDto registrationRequestDto) {
        log.info(registrationRequestDto.toString());
        String message = this.authService.signUp(registrationRequestDto);
        log.info(USER_CREATED);
        return message;
    }

    @GetMapping("/sign-in/email/{email}/password/{password}")
    public UserResponseDto signIn(@PathVariable("email") @Email String email,
                                  @PathVariable("password") String password) {
        log.info("Email {} Password {}", email, password);
        UserResponseDto userResponseDto = this.authService.signIn(email, password);
        log.info(USER_LOGGED_SUCCESSFULLY);
        return userResponseDto;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException exp) {
        return EMAIL_NOT_VALID;
    }
}
