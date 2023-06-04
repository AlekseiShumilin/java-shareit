package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.BookingDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ItemDto {
    Long id;
    @NotNull
    @NotBlank
    String name;
    @NotNull
    @NotBlank
    String description;
    @NotNull
    Boolean available;
    BookingDto nextBooking = null;
    BookingDto lastBooking = null;

}
