package pl.picubicu.sportsobjectsreservationsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.picubicu.sportsobjectsreservationsystem.dto.SportObjectDto;
import pl.picubicu.sportsobjectsreservationsystem.exception.CategoryNotFoundException;
import pl.picubicu.sportsobjectsreservationsystem.exception.SportObjectNotFoundException;
import pl.picubicu.sportsobjectsreservationsystem.model.Address;
import pl.picubicu.sportsobjectsreservationsystem.model.Category;
import pl.picubicu.sportsobjectsreservationsystem.model.SportObject;
import pl.picubicu.sportsobjectsreservationsystem.repository.AddressRepository;
import pl.picubicu.sportsobjectsreservationsystem.repository.CategoryRepository;
import pl.picubicu.sportsobjectsreservationsystem.repository.SportObjectRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SportObjectService {
    private final SportObjectRepository sportObjectRepository;
    private final AddressRepository addressRepository;
    private final CategoryRepository categoryRepository;

    public String addNewSportObject(SportObjectDto sportObjectDto) {
        List<Category> categories = categoryRepository.findAllById(sportObjectDto.getCategoriesIds());
        log.info("Found categories {}", categories);
        if (!categories.isEmpty()) {
            Address newAddress = Address.fromSportObjectDto(sportObjectDto);
            addressRepository.save(newAddress);
            SportObject sportObject = SportObject.fromSportObjectDto(sportObjectDto);
            sportObject.setAddress(newAddress);
            sportObject.setCategories(new HashSet<>(categories));
            sportObjectRepository.save(sportObject);
            return String.format("Sport object with name %s has been created", sportObjectDto.getName());
        }
        throw new CategoryNotFoundException(String.format("There is no categories with given ids %s", sportObjectDto.getCategoriesIds()));
    }

    public String deleteSportObjectById(Long id) {
        Optional<SportObject> sportObject = sportObjectRepository.findById(id);
        if (sportObject.isPresent()) {
            sportObjectRepository.deleteById(id);
            return String.format("Deleted sport object with id %d", id);
        }
        throw new SportObjectNotFoundException(String.format("Sport object with id %d has not been found", id));
    }

    public List<SportObject> getAllSportObjects() {
        return sportObjectRepository.findAll();
    }

    public SportObject getAllSportObjectsById(Long id) {
        Optional<SportObject> sportObject = sportObjectRepository.findById(id);
        if (sportObject.isPresent()) {
            return sportObject.get();
        }
        throw new SportObjectNotFoundException(String.format("Sport object with id %d has not been found", id));
    }
}
