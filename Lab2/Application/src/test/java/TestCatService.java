import dao.cat.CatDao;
import dao.cat.impl.StandardCatDao;
import dto.CatDto;
import entities.Cat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import services.cat.CatService;
import services.cat.impl.StandardCatService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

public class TestCatService {
    private static final long IMITATED_ID = 1;
    private static final Cat IMITATED_CAT;

    static {
        IMITATED_CAT = new Cat();
        IMITATED_CAT.setId(IMITATED_ID);
        IMITATED_CAT.setName("Barsik");
        IMITATED_CAT.setBreed("Dvoroviy");
        IMITATED_CAT.setBirthdate(LocalDate.parse("21-10-2003", getDateTimeFormatter()));
        IMITATED_CAT.setFriends(new ArrayList<>());
    }

    private final CatDao<Cat, Long> dao;

    {
        dao = Mockito.mock(StandardCatDao.class);
        Mockito.when(dao.create(Mockito.isA(Cat.class))).thenReturn(IMITATED_ID);
        Mockito.when(dao.findById(Mockito.isA(Long.class))).thenReturn(Optional.of(IMITATED_CAT));
        Mockito.doNothing().when(dao).update(Mockito.isA(Cat.class));
        Mockito.doNothing().when(dao).delete(Mockito.isA(Cat.class));
    }

    @Test
    public void createCat_catCreatedAndHasValidInfo() {
        // Arrange
        CatService service = new StandardCatService(dao);

        // Act
        Long id = service.createCat(IMITATED_CAT.getName(), IMITATED_CAT.getBreed(), IMITATED_CAT.getBirthdate());
        CatDto catDto = service.getCatById(id);

        // Assert
        Assertions.assertEquals(id, IMITATED_ID);
        Assertions.assertEquals(catDto.name(), IMITATED_CAT.getName());
        Assertions.assertEquals(catDto.breed(), IMITATED_CAT.getBreed());
        Assertions.assertEquals(catDto.birthdate(), IMITATED_CAT.getBirthdate());
    }

    private static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
    }
}
