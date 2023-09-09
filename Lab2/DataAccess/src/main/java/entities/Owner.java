package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Owners")
@Getter
@Setter
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private LocalDate birthdate;

    @OneToMany(mappedBy = "owner")
    private List<Cat> cats;

    public void addCat(Cat cat) {
        cats.add(cat);
    }
}
