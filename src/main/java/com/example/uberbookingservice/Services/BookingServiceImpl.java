package com.example.uberbookingservice.Services;

import com.example.uberbookingservice.Dto.CreateBookingDto;
import com.example.uberbookingservice.Dto.CreateBookingResponseDto;
import com.example.uberbookingservice.Dto.DriverLocationDto;
import com.example.uberbookingservice.Dto.NearByDriversRequestDto;
import com.example.uberbookingservice.Repositories.BookingRepo;
import com.example.uberbookingservice.Repositories.PassengerRepo;
import com.example.uberprojectentityservice.models.Booking;
import com.example.uberprojectentityservice.models.BookingStatus;
import com.example.uberprojectentityservice.models.Passenger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements  BookingService {

    private final PassengerRepo passengerRepo;
    private final BookingRepo bookingRepo;
    private final RestTemplate restTemplate;

    private final String LOCATION_SERVICE="http://localhost:7777";

    public BookingServiceImpl(PassengerRepo passengerRepo, BookingRepo bookingRepo) {
        this.passengerRepo = passengerRepo;
        this.bookingRepo = bookingRepo;
        this.restTemplate =new RestTemplate();
    }

    @Override
    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails) {
        Optional<Passenger> passenger = passengerRepo.findById(bookingDetails.getPassengerId());
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(bookingDetails.getStartLocation())
                .endLocation(bookingDetails.getEndLocation())
                .passenger(passenger.get())
                .build();

        Booking newBooking=bookingRepo.save(booking);

        NearByDriversRequestDto requestDto=NearByDriversRequestDto.builder()
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .longitude(bookingDetails.getStartLocation().getLongitude())
                .build();

        ResponseEntity<DriverLocationDto[]> result=restTemplate.postForEntity(LOCATION_SERVICE+"/api/location/nearby/drivers",requestDto ,DriverLocationDto[].class);

        if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            List<DriverLocationDto> driverLocations = Arrays.asList(result.getBody());
            driverLocations.forEach(driverLocationDto -> {
                System.out.println("Driver ID: " + driverLocationDto.getDriverId() +
                        ", Latitude: " + driverLocationDto.getLatitude() +
                        ", Longitude: " + driverLocationDto.getLongitude());
            });
        }

        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
//                .driver(Optional.of(newBooking.getDriver()))
                .build();

    }
}
