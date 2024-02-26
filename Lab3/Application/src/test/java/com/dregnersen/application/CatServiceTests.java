package com.dregnersen.application;

import com.dregnersen.application.dto.CatDto;
import com.dregnersen.application.services.cat.impl.CatServiceImpl;
import com.dregnersen.dataaccess.entities.Cat;
import com.dregnersen.dataaccess.repositories.CatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CatServiceTests {
    private static final long IMITATED_ID = 1;
    private static final Cat IMITATED_CAT;

    static {
        IMITATED_CAT = new Cat();
        IMITATED_CAT.setId(IMITATED_ID);
        IMITATED_CAT.setName("Barsik");
        IMITATED_CAT.setColor("Beige");
        IMITATED_CAT.setBreed("Dvoroviy");
        IMITATED_CAT.setBirthdate(LocalDate.parse("21-10-2003", getDateTimeFormatter()));
        IMITATED_CAT.setFriends(new ArrayList<>());
    }

    @Mock
    private CatRepository catRepository;

    @InjectMocks
    private CatServiceImpl catService;

    @Test
    public void createCat_catCreatedAndHasValidInfo() {
        // Arrange
        Mockito.when(catRepository.findById(Mockito.isA(Long.class))).thenReturn(Optional.of(IMITATED_CAT));

        // Act
        catService.createCat(
                IMITATED_CAT.getName(),
                IMITATED_CAT.getColor(),
                IMITATED_CAT.getBreed(),
                IMITATED_CAT.getBirthdate());
        CatDto catDto = catService.getCatById(IMITATED_ID);

        // Assert
        Assertions.assertEquals(catDto.id(), IMITATED_CAT.getId());
        Assertions.assertEquals(catDto.name(), IMITATED_CAT.getName());
        Assertions.assertEquals(catDto.breed(), IMITATED_CAT.getBreed());
        Assertions.assertEquals(catDto.birthdate(), IMITATED_CAT.getBirthdate());
    }

    private static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
    }
}
