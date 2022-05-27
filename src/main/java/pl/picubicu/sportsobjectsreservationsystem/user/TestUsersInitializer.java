package pl.picubicu.sportsobjectsreservationsystem.user;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.picubicu.sportsobjectsreservationsystem.address.Address;
import pl.picubicu.sportsobjectsreservationsystem.user.role.Role;
import pl.picubicu.sportsobjectsreservationsystem.user.role.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TestUsersInitializer implements CommandLineRunner {

    private static final int NUM_OF_ACCOUNTS = 30;
    private final String PASSWORD = "zaq1@WSX";
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        createRoles();
        createUsers();
    }

    private void createRoles() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            Role userRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            userRole.setName("ROLE_USER");
            roleRepository.save(adminRole);
            roleRepository.save(userRole);
        }
    }

    private void createUsers() {
        if (userRepository.count() == 0) {
            Faker faker = new Faker();
            List<User> userList = new ArrayList<>();
            Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
            Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
            createAdminAccount(List.of(adminRole.get(), userRole.get()));
            for (int i = 0; i < NUM_OF_ACCOUNTS; i++) {
                userList.add(createRandomUser(faker, userRole.get()));
            }
            userRepository.saveAllAndFlush(userList);
        }
    }

    private void createAdminAccount(List<Role> roles) {
        userRepository.save(User.builder()
                .firstName("Piotr")
                .lastName("Błasiak")
                .isActivated(true)
                .phoneNumber("600472848")
                .email("championello@gmail.com")
                .password(passwordEncoder.encode("zaq1!@WSX"))
                .roles(roles)
                .address(createRandomAddress(new Faker()))
                .build());
    }

    private User createRandomUser(Faker faker, Role userRole) {
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("pl-PL"), new RandomService());
        String firstName = faker.address().firstName();
        return User.builder()
                .firstName(firstName)
                .lastName(faker.witcher().character())
                .email(fakeValuesService.bothify(firstName + "????##@gmail.com"))
                .isActivated(true)
                .phoneNumber(fakeValuesService.regexify("[0-9]{7,9}"))
                .address(createRandomAddress(faker))
                .password(passwordEncoder.encode("zaq1!@WSX"))
                .roles(List.of(userRole))
                .build();
    }

    private Address createRandomAddress(Faker faker) {
        return Address.builder()
                .localNumber(faker.address().buildingNumber())
                .cityName(faker.address().cityName())
                .streetName(faker.address().streetName())
                .streetNumber(faker.address().streetAddressNumber())
                .build();
    }
}
