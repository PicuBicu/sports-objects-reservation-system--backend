package pl.picubicu.sportsobjectsreservationsystem.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ReservationInitializer implements CommandLineRunner {

    private final ReservationStatusRepository repository;

    @Override
    public void run(String... args) throws Exception {
        List<String> statusNames = List.of("REQUESTED", "APPROVED", "CANCELED", "CONFIRMED", "FINALIZED");
        for (String statusName : statusNames) {
            createRoleStatusIfNotExists(statusName);
        }
    }

    private void createRoleStatusIfNotExists(String categoryName) {
        repository.findByName(categoryName).ifPresentOrElse((statusName) -> {},
                () -> repository.save(new ReservationStatus(categoryName))
        );
    }

}
