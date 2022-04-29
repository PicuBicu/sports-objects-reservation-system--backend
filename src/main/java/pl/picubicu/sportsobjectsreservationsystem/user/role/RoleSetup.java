package pl.picubicu.sportsobjectsreservationsystem.user.role;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RoleSetup implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        List<String> roleNames = new ArrayList<>();
        List<Role> roleList = new ArrayList<>();
        roleNames.add("ROLE_ADMIN");
        roleNames.add( "ROLE_USER");
        for (String roleName : roleNames) {
            Optional<Role> role = roleRepository.findByName(roleName);
            if (role.isEmpty()) {
                Role newRole = new Role();
                newRole.setName(roleName);
                roleList.add(newRole);
            }
        }
        if (!roleList.isEmpty()) {
            roleRepository.saveAll(roleList);
        }
    }
}
