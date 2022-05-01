package pl.picubicu.sportsobjectsreservationsystem.user;

import lombok.Builder;
import lombok.Data;
import pl.picubicu.sportsobjectsreservationsystem.address.Address;
import pl.picubicu.sportsobjectsreservationsystem.user.role.Role;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
@Data
public class UserResponseDto {

    private String jwtToken;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Collection<String> role;
    private Address address;

    public static UserResponseDto fromUser(User user) {
        return UserResponseDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()))
                .address(user.getAddress())
                .build();
    }
}
