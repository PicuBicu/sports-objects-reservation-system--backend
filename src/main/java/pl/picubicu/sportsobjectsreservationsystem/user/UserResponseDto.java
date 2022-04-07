package pl.picubicu.sportsobjectsreservationsystem.user;

import lombok.Builder;
import lombok.Data;
import pl.picubicu.sportsobjectsreservationsystem.address.Address;

@Builder
@Data
public class UserResponseDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String role;
    private Address address;

    public static UserResponseDto fromUser(User user) {
        return UserResponseDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .address(user.getAddress())
                .build();
    }
}
