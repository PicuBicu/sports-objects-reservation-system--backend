package pl.picubicu.sportsobjectsreservationsystem;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.picubicu.sportsobjectsreservationsystem.dto.RegistrationRequestDto;
import pl.picubicu.sportsobjectsreservationsystem.model.User;
import pl.picubicu.sportsobjectsreservationsystem.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public void signUp(RegistrationRequestDto registrationRequestDto) {
        User user = User.builder()
                .firstName(registrationRequestDto.getFirstName())
                .lastName(registrationRequestDto.getLastName())
                .password(bCryptPasswordEncoder.encode(registrationRequestDto.getPassword()))
                .build();
        userRepository.save(user);
    }
}
