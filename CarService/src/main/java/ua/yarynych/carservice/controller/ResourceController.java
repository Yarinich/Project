package ua.yarynych.carservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.yarynych.carservice.entity.Car;
import ua.yarynych.carservice.service.CarStateService;

@RestController
public class ResourceController {

    @Autowired
    private CarStateService carStateService;

    @PostMapping("/resources")
    public Car getResourcesPost() {
        return carStateService.getCarState();
    }

    @GetMapping("/resources")
    public Car getResourcesGet() {
        return carStateService.getCarState();
    }
}
