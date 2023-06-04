package ru.practicum.shareit.item.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

@Entity
@Table(name = "items")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String name;
    @Column
    String description;
    @Column(name = "is_available")
    boolean isAvailable;
    @ManyToOne
    @ToString.Exclude
    User user;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
