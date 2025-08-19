package com.example.uberbookingservice.Dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {
    private Long passengerId;
//    private ExactLocation startLocation;
//    private ExactLocation endLocation;
    private List<Long> driverId;

    private Long bookingId;
}
