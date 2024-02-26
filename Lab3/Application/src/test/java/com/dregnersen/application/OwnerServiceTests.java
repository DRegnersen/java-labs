package com.dregnersen.application;

import com.dregnersen.application.dto.OwnerDto;
import com.dregnersen.application.services.owner.impl.OwnerServiceImpl;
import com.dregnersen.dataaccess.entities.Cat;
import com.dregnersen.dataaccess.entities.Owner;
import com.dregnersen.dataaccess.repositories.OwnerRepository;
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
public class OwnerServiceTests {
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

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Test
    public void createOwner_OwnerCreatedAndHasValidInfo() {
        // Arrange
        Mockito.when(ownerRepository.findById(Mockito.isA(Long.class))).thenReturn(Optional.of(IMITATED_OWNER));

        // Act
        ownerService.createOwner(IMITATED_OWNER.getName(), IMITATED_OWNER.getBirthdate());
        OwnerDto ownerDto = ownerService.getOwnerById(IMITATED_ID);

        // Assert
        Assertions.assertEquals(ownerDto.id(), IMITATED_OWNER.getId());
        Assertions.assertEquals(ownerDto.name(), IMITATED_OWNER.getName());
        Assertions.assertEquals(ownerDto.birthdate(), IMITATED_OWNER.getBirthdate());
    }

    private static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
    }
}
