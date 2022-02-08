package com.weatherinfoapp.WeatherInfoDemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class WeatherController {
    @Autowired
    CacheManager cacheManager;

    Logger logger = LoggerFactory.getLogger(WeatherController.class);


    @GetMapping("/home")
    public String weatherHome(){
        return "Welcome to weather info application!";
    }
    @GetMapping(value="/weatherinfo/getbycoordinates",produces = "application/json")
    @Cacheable(value = "wbcoord")

    public String getWeatherInfoByLatLon(@RequestParam String lat,@RequestParam String lon)
    {
        //evictAllCaches();
        RestTemplate restTemp = new RestTemplate();
        System.out.println("Calling Weather API for the given coordinates="+lat+":"+lon);
        logger.info("Calling Weather API for the given coordinates="+lat+":"+lon);
        String uri = "https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&exclude=hourly,daily&appid=ffa6f13ea40a22452bba3be726315d3f";
        return restTemp.getForObject(uri,String.class);
    }

    @GetMapping(value="/weatherinfo/getbycity/{city}",produces = "application/json")
    @Cacheable(value = "wbcountry")
    public String getWeatherInfoByCity(@PathVariable("city") String city)
    {
        RestTemplate restTemp = new RestTemplate();
        System.out.println("Calling Weather API for the given city="+city);
        logger.info("Calling Weather API for the given city="+city);
        String uri = "api.openweathermap.org/data/2.5/weather?q="+city+"&appkey=ffa6f13ea40a22452bba3be726315d3f";
        return restTemp.getForObject(uri,String.class,city);
    }

    @Scheduled(fixedRate = 2 * 60 * 60 * 1000)
    @CacheEvict(value = "wbcoord", allEntries = true)
    public void clearCache() {
    }
}
