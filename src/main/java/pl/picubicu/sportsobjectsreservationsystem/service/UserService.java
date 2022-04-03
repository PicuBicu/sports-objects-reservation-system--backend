package pl.picubicu.sportsobjectsreservationsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.picubicu.sportsobjectsreservationsystem.dto.UserResponseDto;
import pl.picubicu.sportsobjectsreservationsystem.exception.UserNotFoundException;
import pl.picubicu.sportsobjectsreservationsystem.model.User;
import pl.picubicu.sportsobjectsreservationsystem.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.picubicu.sportsobjectsreservationsystem.message.SystemMessage.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDto> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(UserResponseDto::fromUser)
                .collect(Collectors.toList());
    }

    public String changeUserStatusByEmail(String email, Boolean isActivated) {
        Optional<User> wantedUser = this.userRepository.findByEmail(email);
        if (wantedUser.isPresent()) {
            wantedUser.get().setIsActivated(isActivated);
            this.userRepository.save(wantedUser.get());
            return USER_ACTIVATION_STATUS_CHANGE;
        }
        throw new UserNotFoundException(USER_NOT_FOUND);
    }

    @Transactional
    public String deleteUserByEmail(String email) {
        this.userRepository.deleteByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        return USER_DELETED;
    }

    public List<UserResponseDto> getAllUserWithStatus(Boolean isActivated) {
        return this.userRepository.findAllByIsActivated(isActivated)
                .orElse(new ArrayList<>())
                .stream()
                .map(UserResponseDto::fromUser)
                .collect(Collectors.toList());
    }
}
