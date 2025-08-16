package com.example.uberbookingservice.Dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearByDriversRequestDto {
    private Double latitude;
    private Double longitude;
}
