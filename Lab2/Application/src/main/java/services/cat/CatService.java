package services.cat;

import dto.CatDto;

import java.time.LocalDate;

public interface CatService {
    Long createCat(String name, String breed, LocalDate birthdate);

    CatDto getCatById(Long id);

    void updateName(Long id, String newName);

    void addFriend(Long id, Long friendId);

    void removeCat(Long id);
}
