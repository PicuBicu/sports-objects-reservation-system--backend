package pl.picubicu.sportsobjectsreservationsystem.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReservationInitializer implements CommandLineRunner {

    private final ReservationStatusRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.save(new ReservationStatus("REQUESTED"));
        repository.save(new ReservationStatus("APPROVED"));
        repository.save(new ReservationStatus("CANCELED"));
        repository.save(new ReservationStatus("CONFIRMED"));
        repository.save(new ReservationStatus("FINALIZED"));
    }

}
