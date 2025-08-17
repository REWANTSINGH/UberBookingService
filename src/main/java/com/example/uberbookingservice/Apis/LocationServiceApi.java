package com.example.uberbookingservice.Apis;

import com.example.uberbookingservice.Dto.DriverLocationDto;
import com.example.uberbookingservice.Dto.NearByDriversRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LocationServiceApi {

    @POST("/api/location/nearby/drivers")
    Call<DriverLocationDto[]> getNearByDrivers(@Body NearByDriversRequestDto requestDto);

}
