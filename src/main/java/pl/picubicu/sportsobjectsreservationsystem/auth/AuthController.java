package pl.picubicu.sportsobjectsreservationsystem.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.picubicu.sportsobjectsreservationsystem.custom.CustomResponse;
import pl.picubicu.sportsobjectsreservationsystem.user.UserResponseDto;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public CustomResponse signUp(@Valid @RequestBody RegistrationDto registrationDto) {
        log.info(registrationDto.toString());
        String message = this.authService.signUp(registrationDto);
        log.info(message);
        return new CustomResponse(message);
    }

    @PostMapping("/sign-in")
    public UserResponseDto signIn(@Valid @RequestBody LoginDto loginDto) {
        log.info(loginDto.toString());
        UserResponseDto userResponseDto = this.authService.signIn(loginDto.getEmail(), loginDto.getPassword());
        log.info("{} has been logged in", userResponseDto.getEmail());
        return userResponseDto;
    }

}
