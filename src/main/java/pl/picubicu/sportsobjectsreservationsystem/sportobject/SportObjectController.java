package pl.picubicu.sportsobjectsreservationsystem.sportobject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.picubicu.sportsobjectsreservationsystem.custom.CustomResponse;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/sport-object")
@RestController
public class SportObjectController {

    private final SportObjectService sportObjectService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/")
    public CustomResponse addSportObject(@Valid @RequestBody SportObjectDto sportObjectDto) {
        log.info(sportObjectDto.toString());
        String message = sportObjectService.addNewSportObject(sportObjectDto);
        log.info(message);
        return new CustomResponse(message);
    }

    @PutMapping("/{id}")
    public CustomResponse updateSportObject(@PathVariable Long id, @Valid @RequestBody SportObjectDto sportObjectDto) {
        log.info("Update {}", sportObjectDto.toString());
        sportObjectService.updateSportObjectById(id, sportObjectDto);
        String message = String.format("Sport object with id %d has been updated", id);
        log.info(message, id);
        return new CustomResponse(message);
    }

    @DeleteMapping("/{id}")
    public CustomResponse deleteSportObject(@PathVariable("id") Long id) {
        log.info("Sport object id to be deleted {}", id);
        String message = sportObjectService.deleteSportObjectById(id);
        log.info(message);
        return new CustomResponse(message);
    }

    @GetMapping("/")
    public List<SportObject> getAllSportObjects() {
        log.info("Fetching all sport objects");
        return sportObjectService.getAllSportObjects();
    }

    @GetMapping("/{id}")
    public SportObject getSportObjectById(@PathVariable Long id) {
        log.info("Fetching sport object with id {}", id);
        return sportObjectService.getAllSportObjectsById(id);
    }

    @GetMapping("/category")
    public List<SportObject> getSportObjectsByCategoryId(@RequestParam("categoryId") Long id) {
        log.info("Fetch sport object with category id {}", id);
        return sportObjectService.getSportObjectsByCategoryId(id);
    }
}
