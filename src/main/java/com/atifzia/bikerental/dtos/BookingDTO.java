package com.atifzia.bikerental.dtos;

import java.time.LocalDateTime;
import com.atifzia.bikerental.models.Bike;
import lombok.Data;

@Data
public class BookingDTO {
    private Long id;
    private Bike bike;
    private LocalDateTime createdDate;
    private UserResponseDTO bookedBy;
}
