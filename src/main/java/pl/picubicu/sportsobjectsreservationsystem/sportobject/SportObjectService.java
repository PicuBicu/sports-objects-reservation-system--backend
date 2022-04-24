package pl.picubicu.sportsobjectsreservationsystem.sportobject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.picubicu.sportsobjectsreservationsystem.address.Address;
import pl.picubicu.sportsobjectsreservationsystem.address.AddressRepository;
import pl.picubicu.sportsobjectsreservationsystem.category.Category;
import pl.picubicu.sportsobjectsreservationsystem.category.CategoryNotFoundException;
import pl.picubicu.sportsobjectsreservationsystem.category.CategoryRepository;

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

    public List<SportObject> getSportObjectsByCategoryId(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return sportObjectRepository.findSportObjectByCategoriesContaining(category.get());
        }
        throw new CategoryNotFoundException(String.format("There is no category with an id %d", id));
    }

    public void updateSportObjectById(Long id, SportObjectDto sportObjectDto) {
        SportObject sportObj = sportObjectRepository.findById(id).map(sportObject -> {
            sportObject.setName(sportObjectDto.getName());
            sportObject.setImageName(sportObjectDto.getImageName());
            sportObject.getAddress().setStreetName(sportObjectDto.getStreetName());
            sportObject.getAddress().setLocalNumber(sportObjectDto.getLocalNumber());
            sportObject.getAddress().setStreetNumber(sportObjectDto.getStreetNumber());
            sportObject.getAddress().setCityName(sportObjectDto.getCityName());
            List<Category> categories = categoryRepository.findAllById(sportObjectDto.getCategoriesIds());
            if (categories.isEmpty()) {
                throw new CategoryNotFoundException("There is not categories with given ids = " +
                        sportObjectDto.getCategoriesIds());
            }
            sportObject.setCategories(new HashSet<>(categories));
            return sportObject;
        }).orElseThrow(() -> {
            throw new SportObjectNotFoundException("Sport object with id " + id + " does not exits");
        });
        sportObjectRepository.save(sportObj);
    }
}
