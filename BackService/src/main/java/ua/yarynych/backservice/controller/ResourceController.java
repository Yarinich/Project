package ua.yarynych.backservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.yarynych.backservice.entity.car.CarExpanded;
import ua.yarynych.backservice.service.AdviceService;
import ua.yarynych.backservice.service.CarService;

import java.util.List;
import java.util.Map;


@RestController
public class ResourceController {

    @Autowired
    AdviceService adviceService;

    @Autowired
    CarService carService;


    @GetMapping("/recommendation")
    public Map<String, List<String>> createRecommendation(@RequestHeader(value = "Authorization") String authorizationHeader) {
        return adviceService.createRecommendation(authorizationHeader);
    }

    @GetMapping("/refresh")
    public Map<String, List<String>> refreshRecommendation(@RequestHeader(value = "Authorization") String authorizationHeader) {
        return adviceService.refreshRecommendation(authorizationHeader);
    }

    @GetMapping("/getCarInfo")
    public CarExpanded getCarInfo(@RequestHeader(value = "Authorization") String authorizationHeader) {
        return carService.getInfo(authorizationHeader);
    }
}
