package pl.picubicu.sportsobjectsreservationsystem.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.picubicu.sportsobjectsreservationsystem.user.UserResponseDto;
import pl.picubicu.sportsobjectsreservationsystem.user.UserAlreadyExistsException;
import pl.picubicu.sportsobjectsreservationsystem.user.UserMismatchPasswordException;
import pl.picubicu.sportsobjectsreservationsystem.user.UserNotActivatedException;
import pl.picubicu.sportsobjectsreservationsystem.user.UserNotFoundException;
import pl.picubicu.sportsobjectsreservationsystem.address.Address;
import pl.picubicu.sportsobjectsreservationsystem.user.User;
import pl.picubicu.sportsobjectsreservationsystem.address.AddressRepository;
import pl.picubicu.sportsobjectsreservationsystem.user.UserRepository;

import java.util.Optional;

import static pl.picubicu.sportsobjectsreservationsystem.custom.SystemMessage.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public String signUp(RegistrationRequestDto registrationRequestDto) {

        Optional<User> foundUser = userRepository.findByEmail(registrationRequestDto.getEmail());
        if (foundUser.isPresent()) {
            if (!foundUser.get().getIsActivated()) {
                log.warn(USER_NOT_ACTIVATED);
                throw new UserNotActivatedException(USER_NOT_ACTIVATED);
            }
            log.error(USER_ALREADY_EXISTS);
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS);
        }

        Address address = Address.fromRegistrationDto(registrationRequestDto);
        addressRepository.save(address);

        User newUser = User.fromRegistrationDtoWithRole(registrationRequestDto, "CLIENT");
        newUser.setAddress(address);
        newUser.setPassword(bCryptPasswordEncoder
                .encode(registrationRequestDto.getPassword()));
        userRepository.save(newUser);

        return USER_CREATED;
    }

    public UserResponseDto signIn(String email, String password) {
        Optional<User> foundUser = this.userRepository.findByEmail(email);
        if (foundUser.isPresent()) {
            User wantedUser = foundUser.get();
            if (!wantedUser.getIsActivated()) {
                log.warn(USER_NOT_ACTIVATED);
                throw new UserNotActivatedException(USER_NOT_ACTIVATED);
            }
            if (this.bCryptPasswordEncoder.matches(password, wantedUser.getPassword())) {
                return UserResponseDto.fromUser(wantedUser);
            }
            log.error(USER_CREDENTIALS_NOT_VALID);
            throw new UserMismatchPasswordException(USER_CREDENTIALS_NOT_VALID);
        }
        log.warn(USER_NOT_FOUND);
        throw new UserNotFoundException(USER_NOT_FOUND);
    }
}
