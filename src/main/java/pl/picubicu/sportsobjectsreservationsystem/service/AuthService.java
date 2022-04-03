package pl.picubicu.sportsobjectsreservationsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.picubicu.sportsobjectsreservationsystem.dto.RegistrationRequestDto;
import pl.picubicu.sportsobjectsreservationsystem.dto.UserResponseDto;
import pl.picubicu.sportsobjectsreservationsystem.exception.UserAlreadyExistsException;
import pl.picubicu.sportsobjectsreservationsystem.exception.UserMismatchPasswordException;
import pl.picubicu.sportsobjectsreservationsystem.exception.UserNotActivatedException;
import pl.picubicu.sportsobjectsreservationsystem.exception.UserNotFoundException;
import pl.picubicu.sportsobjectsreservationsystem.model.Address;
import pl.picubicu.sportsobjectsreservationsystem.model.User;
import pl.picubicu.sportsobjectsreservationsystem.repository.AddressRepository;
import pl.picubicu.sportsobjectsreservationsystem.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;

import static pl.picubicu.sportsobjectsreservationsystem.message.SystemMessage.*;

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
