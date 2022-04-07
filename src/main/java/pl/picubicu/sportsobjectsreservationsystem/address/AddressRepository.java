package pl.picubicu.sportsobjectsreservationsystem.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.picubicu.sportsobjectsreservationsystem.address.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
