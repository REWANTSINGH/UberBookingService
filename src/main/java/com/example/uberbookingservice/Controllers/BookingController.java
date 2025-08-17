package com.example.uberbookingservice.Controllers;

import com.example.uberbookingservice.Dto.CreateBookingDto;
import com.example.uberbookingservice.Dto.CreateBookingResponseDto;
import com.example.uberbookingservice.Dto.UpdateBookingResponseDto;
import com.example.uberbookingservice.Dto.UpdateBookingRequestDto;
import com.example.uberbookingservice.Services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{bookingId}")
    public ResponseEntity<UpdateBookingResponseDto> updateBooking(@RequestBody UpdateBookingRequestDto updateBookingRequestDto, @PathVariable Long bookingId){

        return new ResponseEntity<>(bookingService.updateBooking(updateBookingRequestDto, bookingId), HttpStatus.OK);

    }


}
