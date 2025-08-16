package com.example.uberbookingservice.Controllers;

import com.example.uberbookingservice.Dto.CreateBookingDto;
import com.example.uberbookingservice.Dto.CreateBookingResponseDto;
import com.example.uberbookingservice.Services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<CreateBookingResponseDto> createBooking(@RequestBody CreateBookingDto createBookingDto){

        return new ResponseEntity<>(bookingService.createBooking(createBookingDto), HttpStatus.OK);

    }

}
