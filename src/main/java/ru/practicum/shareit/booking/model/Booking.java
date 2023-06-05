package ru.practicum.shareit.booking.model;

import lombok.Data;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "bookings")

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "start_date")
    @NotNull
    @FutureOrPresent
    private LocalDateTime start;
    @Column(name = "end_date")
    @NotNull
    @Future
    private LocalDateTime end;
    @ManyToOne
    Item item;
    @ManyToOne
    User booker;
    @Enumerated(EnumType.STRING)
    BookingStatus status;
}
