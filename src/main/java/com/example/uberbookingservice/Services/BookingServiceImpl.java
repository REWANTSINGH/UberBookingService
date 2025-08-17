package com.example.uberbookingservice.Services;

import com.example.uberbookingservice.Apis.LocationServiceApi;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements  BookingService {

    private final PassengerRepo passengerRepo;
    private final BookingRepo bookingRepo;
    private final RestTemplate restTemplate;

    private final LocationServiceApi locationServiceApi;

//    private final String LOCATION_SERVICE="http://localhost:7777";

    public BookingServiceImpl(PassengerRepo passengerRepo, BookingRepo bookingRepo, LocationServiceApi locationServiceApi) {
        this.passengerRepo = passengerRepo;
        this.bookingRepo = bookingRepo;
        this.locationServiceApi = locationServiceApi;
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

        processNearbyDriversAsync(requestDto);


//


//        ResponseEntity<DriverLocationDto[]> result=restTemplate.postForEntity(LOCATION_SERVICE+"/api/location/nearby/drivers",requestDto ,DriverLocationDto[].class);
//
//        if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
//            List<DriverLocationDto> driverLocations = Arrays.asList(result.getBody());
//            driverLocations.forEach(driverLocationDto -> {
//                System.out.println("Driver ID: " + driverLocationDto.getDriverId() +
//                        ", Latitude: " + driverLocationDto.getLatitude() +
//                        ", Longitude: " + driverLocationDto.getLongitude());
//            });
//        }

        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
//                .driver(Optional.of(newBooking.getDriver()))
                .build();

    }

    private void processNearbyDriversAsync(NearByDriversRequestDto nearByDriversRequestDto) {
        Call<DriverLocationDto[]> call = locationServiceApi.getNearByDrivers(nearByDriversRequestDto);
        // Asynchronously execute the call
        call.enqueue(new Callback<DriverLocationDto[]>() {
            @Override
            public void onResponse(Call<DriverLocationDto[]> call, Response<DriverLocationDto[]> response) {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (response.isSuccessful() && response.body() != null) {
                    List<DriverLocationDto> driverLocations = Arrays.asList(response.body());
                    driverLocations.forEach(driverLocationDto -> {
                        System.out.println("Driver ID: " + driverLocationDto.getDriverId() +
                                ", Latitude: " + driverLocationDto.getLatitude() +
                                ", Longitude: " + driverLocationDto.getLongitude());
                    });
                } else {
                    System.out.println("Failed to fetch nearby drivers: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DriverLocationDto[]> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
