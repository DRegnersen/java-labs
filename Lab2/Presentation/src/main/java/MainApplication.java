import config.Strategy;
import config.HibernateConfig;
import controllers.CatController;
import controllers.OwnerController;
import dao.cat.CatDao;
import dao.cat.impl.StandardCatDao;
import dao.owner.OwnerDao;
import dao.owner.impl.StandardOwnerDao;
import dto.CatDto;
import entities.Cat;
import entities.Owner;
import jakarta.persistence.EntityManagerFactory;
import models.cat.AddFriendModel;
import models.cat.CreateCatModel;
import models.owner.AddCatModel;
import models.owner.CreateOwnerModel;
import services.cat.impl.StandardCatService;
import services.owner.impl.StandardOwnerService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class MainApplication {
    private MainApplication() {
    }

    public static void main(String[] args) {
        HibernateConfig config = HibernateConfig.getBuilder()
                .withHost("localhost")
                .withPort(5432)
                .withDbName("JavaTech")
                .withUsername("postgres")
                .withPassword("02svk2003")
                .withStrategy(Strategy.CREATE)
                .withManagedClasses(Cat.class, Owner.class)
                .withSqlShowingValue(true).build();

        EntityManagerFactory managerFactory = config.createEntityManagerFactory();

        CatDao<Cat, Long> catDao = new StandardCatDao(managerFactory.createEntityManager());
        OwnerDao<Owner, Long> ownerDao = new StandardOwnerDao(managerFactory.createEntityManager());

        CatController catController = new CatController(new StandardCatService(catDao));
        OwnerController ownerController = new OwnerController(new StandardOwnerService(ownerDao, catDao));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        Long barsikId = catController.createCat(new CreateCatModel(
                "Barsik",
                "Dvoroviy",
                LocalDate.parse("21-10-2003", formatter)));

        Long stepanId = ownerController.createOwner(new CreateOwnerModel(
                "Mishanya",
                LocalDate.parse("20-12-2002", formatter)));

        ownerController.addCat(new AddCatModel(stepanId, barsikId));

        Long garfieldId = catController.createCat(new CreateCatModel(
                "Garfield",
                "Reddish",
                LocalDate.parse("08-03-2010", formatter)));

        catController.addFriend(new AddFriendModel(barsikId, garfieldId));

        CatDto barsikDto = catController.getCat(barsikId);
        System.out.println("<Cat with id " + barsikId + " info>");
        System.out.println("Name: " + barsikDto.name());
        System.out.println("Breed: " + barsikDto.breed());
        System.out.println("Birthdate: " + barsikDto.birthdate());
        System.out.println("Friends (id):");
        for (Long friendId : barsikDto.friends()) {
            System.out.println(friendId);
        }
    }
}
