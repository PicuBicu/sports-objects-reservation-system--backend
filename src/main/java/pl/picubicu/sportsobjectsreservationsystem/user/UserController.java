package pl.picubicu.sportsobjectsreservationsystem.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import static pl.picubicu.sportsobjectsreservationsystem.custom.SystemMessage.USER_DELETED;

@SecurityRequirement(name = "bearerAuth")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/user")
@RestController
@RolesAllowed(value = "ADMIN")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public List<UserResponseDto> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/activated/{isActivated}")
    public List<UserResponseDto> getAllUserWithStatus(@PathVariable("isActivated") Boolean isActivated) {
        return this.userService.getAllUserWithStatus(isActivated);
    }

    @DeleteMapping("{email}")
    public String deleteUser(@PathVariable("email") String email) {
        log.info(USER_DELETED);
        return this.userService.deleteUserByEmail(email);
    }

    @PutMapping("/")
    public String changeUserStatus(@RequestParam("email") String email,
                                   @RequestParam("isActivated") Boolean isActivated) {
        return this.userService.changeUserStatusByEmail(email, isActivated);
    }
}
