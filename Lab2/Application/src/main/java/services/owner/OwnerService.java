package services.owner;

import dto.OwnerDto;

import java.time.LocalDate;

public interface OwnerService {
    Long createOwner(String name, LocalDate birthdate);

    OwnerDto getOwnerById(Long id);

    void updateName(Long id, String newName);

    void addCat(Long id, Long catId);

    void removeOwner(Long id);
}
