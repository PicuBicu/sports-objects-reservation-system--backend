package pl.picubicu.sportsobjectsreservationsystem.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.picubicu.sportsobjectsreservationsystem.user.User;
import pl.picubicu.sportsobjectsreservationsystem.user.UserRepository;
import pl.picubicu.sportsobjectsreservationsystem.user.role.Role;
import pl.picubicu.sportsobjectsreservationsystem.user.role.RoleRepository;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean
    public CommandLineRunner createAdminAccount() {
        return args -> {
            PasswordEncoder encoder = new BCryptPasswordEncoder(10);
            String email = "admin@gmail.com";
            String password = "zaq1@WSX";
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isEmpty()) {
                log.info("Created admin account with credentials email = {} password {}", email, password);
                User admin = new User();
                admin.setEmail(email);
                admin.setPassword(encoder.encode(password));
                Optional<Role> role = roleRepository.findByName("ROLE_ADMIN");
                if (role.isEmpty()) {
                    log.error("Role should already exists");
                } else {
                    admin.setRoles(List.of(role.get()));
                    userRepository.save(admin);
                }
            } else {
                log.info("Admin account already exists");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
