package pl.picubicu.sportsobjectsreservationsystem.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.picubicu.sportsobjectsreservationsystem.address.Address;
import pl.picubicu.sportsobjectsreservationsystem.address.AddressRepository;
import pl.picubicu.sportsobjectsreservationsystem.config.JwtTokenUtil;
import pl.picubicu.sportsobjectsreservationsystem.config.MyUserDetails;
import pl.picubicu.sportsobjectsreservationsystem.user.User;
import pl.picubicu.sportsobjectsreservationsystem.user.UserAlreadyExistsException;
import pl.picubicu.sportsobjectsreservationsystem.user.UserMismatchPasswordException;
import pl.picubicu.sportsobjectsreservationsystem.user.UserNotActivatedException;
import pl.picubicu.sportsobjectsreservationsystem.user.UserRepository;
import pl.picubicu.sportsobjectsreservationsystem.user.UserResponseDto;
import pl.picubicu.sportsobjectsreservationsystem.user.role.Role;
import pl.picubicu.sportsobjectsreservationsystem.user.role.RoleNotFoundException;
import pl.picubicu.sportsobjectsreservationsystem.user.role.RoleRepository;

import java.util.List;
import java.util.Optional;

import static pl.picubicu.sportsobjectsreservationsystem.custom.SystemMessage.USER_ALREADY_EXISTS;
import static pl.picubicu.sportsobjectsreservationsystem.custom.SystemMessage.USER_CREATED;
import static pl.picubicu.sportsobjectsreservationsystem.custom.SystemMessage.USER_NOT_ACTIVATED;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final RoleRepository roleRepository;

    public String signUp(RegistrationDto registrationDto) {

        Optional<User> foundUser = userRepository.findByEmail(registrationDto.getEmail());
        if (foundUser.isPresent()) {
            if (!foundUser.get().getIsActivated()) {
                log.warn(USER_NOT_ACTIVATED);
                throw new UserNotActivatedException(USER_NOT_ACTIVATED);
            }
            log.error(USER_ALREADY_EXISTS);
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS);
        }

        Address address = Address.fromRegistrationDto(registrationDto);
        addressRepository.save(address);

        User newUser = User.fromRegistrationDtoWithRole(registrationDto);

        String roleName = "ROLE_USER";
        Optional<Role> role = roleRepository.findByName(roleName);

        if (role.isEmpty()) {
            throw new RoleNotFoundException("Role with name " + roleName + " does not exists");
        }

        newUser.setAddress(address);
        newUser.setPassword(bCryptPasswordEncoder
                .encode(registrationDto.getPassword()));
        newUser.setRoles(List.of(role.get()));
        userRepository.save(newUser);

        return USER_CREATED;
    }

    public UserResponseDto signIn(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            password
                    )
            );
            MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
            UserResponseDto userDto = UserResponseDto.fromUser(user.getUser());
            userDto.setJwtToken(jwtTokenUtil.generateToken(user));
            return userDto;
        } catch (BadCredentialsException exception) {
            log.error(exception.getMessage());
            throw new UserMismatchPasswordException(exception.getMessage());
        }
    }
}
