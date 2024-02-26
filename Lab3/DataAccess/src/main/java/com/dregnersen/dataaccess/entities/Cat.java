package com.dregnersen.dataaccess.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Cats")
@Getter
@Setter
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String name;
    private LocalDate birthdate;
    private String color;
    private String breed;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany
    @JoinTable(name = "Friends",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<Cat> friends;

    public void addFriend(Cat friend) {
        friends.add(friend);
    }
}
