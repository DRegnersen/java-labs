import dao.cat.CatDao;
import dao.cat.impl.StandardCatDao;
import dao.owner.OwnerDao;
import dao.owner.impl.StandardOwnerDao;
import dto.OwnerDto;
import entities.Cat;
import entities.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import services.owner.OwnerService;
import services.owner.impl.StandardOwnerService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

public class TestOwnerService {
    private static final long IMITATED_ID = 1;
    private static final Owner IMITATED_OWNER;

    static {
        IMITATED_OWNER = new Owner();
        IMITATED_OWNER.setId(IMITATED_ID);
        IMITATED_OWNER.setName("Mishanya");
        IMITATED_OWNER.setBirthdate(LocalDate.parse("20-12-2002", getDateTimeFormatter()));
        IMITATED_OWNER.setCats(new ArrayList<>());
    }

    private static final long IMITATED_CAT_ID = 1;
    private static final Cat IMITATED_CAT;

    static {
        IMITATED_CAT = new Cat();
        IMITATED_CAT.setId(IMITATED_CAT_ID);
        IMITATED_CAT.setName("Barsik");
        IMITATED_CAT.setBreed("Dvoroviy");
        IMITATED_CAT.setBirthdate(LocalDate.parse("21-10-2003", getDateTimeFormatter()));
    }

    static {
        IMITATED_OWNER.addCat(IMITATED_CAT);
        IMITATED_CAT.setOwner(IMITATED_OWNER);
    }

    private final OwnerDao<Owner, Long> dao;

    {
        dao = Mockito.mock(StandardOwnerDao.class);
        Mockito.when(dao.create(Mockito.isA(Owner.class))).thenReturn(IMITATED_ID);
        Mockito.when(dao.findById(Mockito.isA(Long.class))).thenReturn(Optional.of(IMITATED_OWNER));
        Mockito.doNothing().when(dao).update(Mockito.isA(Owner.class));
        Mockito.doNothing().when(dao).delete(Mockito.isA(Owner.class));
    }

    private final CatDao<Cat, Long> catDao;

    {
        catDao = Mockito.mock(StandardCatDao.class);
        Mockito.when(catDao.create(Mockito.isA(Cat.class))).thenReturn(IMITATED_ID);
        Mockito.when(catDao.findById(Mockito.isA(Long.class))).thenReturn(Optional.of(IMITATED_CAT));
        Mockito.doNothing().when(catDao).update(Mockito.isA(Cat.class));
        Mockito.doNothing().when(catDao).delete(Mockito.isA(Cat.class));
    }

    @Test
    public void createOwner_OwnerCreatedAndHasValidInfo() {
        // Arrange
        OwnerService service = new StandardOwnerService(dao, catDao);

        // Act
        Long id = service.createOwner(IMITATED_OWNER.getName(), IMITATED_OWNER.getBirthdate());
        OwnerDto ownerDto = service.getOwnerById(id);

        // Assert
        Assertions.assertEquals(id, IMITATED_ID);
        Assertions.assertEquals(IMITATED_OWNER.getName(), ownerDto.name());
        Assertions.assertEquals(IMITATED_OWNER.getBirthdate(), ownerDto.birthdate());
    }

    private static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
    }
}
