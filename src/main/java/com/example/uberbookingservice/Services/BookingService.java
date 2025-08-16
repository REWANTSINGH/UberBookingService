package com.example.uberbookingservice.Services;

import com.example.uberbookingservice.Dto.CreateBookingDto;
import com.example.uberbookingservice.Dto.CreateBookingResponseDto;
import com.example.uberprojectentityservice.models.Booking;

public interface BookingService {


    public CreateBookingResponseDto createBooking(CreateBookingDto booking);
}
