package com.example.uberbookingservice.Controllers;

import com.example.uberbookingservice.Apis.LocationServiceApi;
import com.example.uberbookingservice.Apis.UberSocketApi;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Autowired
    EurekaClient eurekaClient;

    private String getServiceUrl(String serviceName) {
        return eurekaClient.getNextServerFromEureka(serviceName, false).getHomePageUrl();
    }

    @Bean
    public LocationServiceApi locationServiceApi() {
        return new Retrofit.Builder()
                .baseUrl(getServiceUrl("UBERPROJECTLOCATIONSERVICE"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LocationServiceApi.class);
    }

    @Bean
    public UberSocketApi  uberSocketApi() {
        return new Retrofit.Builder()
                .baseUrl(getServiceUrl("UBERSOCKETSERVER"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UberSocketApi.class);
    }


//    @Bean
//    public Retrofit retrofit(){
//        return new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
}
