package pl.picubicu.sportsobjectsreservationsystem.user;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.picubicu.sportsobjectsreservationsystem.address.Address;
import pl.picubicu.sportsobjectsreservationsystem.user.role.Role;
import pl.picubicu.sportsobjectsreservationsystem.user.role.RoleRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TestUsersInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private static final int NUM_OF_ACCOUNTS = 30;

    @Override
    public void run(String... args) throws Exception {

        if (!userRepository.findByEmail("championello0@gmail.com").isPresent()) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
            Role userRole = roleRepository.findByName("ROLE_USER").get();
            for (int i = 0; i < NUM_OF_ACCOUNTS; i++) {
                User user = User.builder()
                        .firstName("Piotr")
                        .email("championello" + i + "@gmail.com")
                        .isActivated(true)
                        .lastName("Błasiak")
                        .phoneNumber("999111222")
                        .address(Address.builder()
                                .cityName("A")
                                .localNumber("A")
                                .cityName("A")
                                .streetNumber("A")
                                .build()
                        )
                        .password(passwordEncoder.encode("zaq1@WSX"))
                        .roles(List.of(adminRole, userRole))
                        .build();
                userRepository.save(user);
            }
        }


    }
}
