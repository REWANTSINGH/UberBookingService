package com.example.uberbookingservice.Apis;

import com.example.uberbookingservice.Dto.DriverLocationDto;
import com.example.uberbookingservice.Dto.NearByDriversRequestDto;
import com.example.uberbookingservice.Dto.RideRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UberSocketApi {

    @POST("/api/socket/newride")
    Call<Boolean> raiseRideRequest(@Body RideRequestDto requestDto);



}
