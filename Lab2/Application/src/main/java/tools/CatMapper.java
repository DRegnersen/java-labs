package tools;

import dto.CatDto;
import entities.Cat;

public final class CatMapper {
    private CatMapper() {
    }

    public static CatDto map(Cat cat) {
        return new CatDto(
                cat.getId(),
                cat.getName(),
                cat.getBreed(),
                cat.getBirthdate(),
                (cat.getFriends() != null) ?
                        cat.getFriends().stream().map(Cat::getId).toArray(Long[]::new) :
                        new Long[0]);
    }
}
