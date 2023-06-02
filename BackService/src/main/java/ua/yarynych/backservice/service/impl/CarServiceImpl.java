package ua.yarynych.backservice.service.impl;

import org.springframework.stereotype.Service;
import ua.yarynych.backservice.entity.car.CarExpanded;
import ua.yarynych.backservice.service.CarService;

@Service
public class CarServiceImpl implements CarService {

    private final CarExpanded car = new CarExpanded("yarinich.vitalya@gmail.com", "Peugeout", "508", "Wagon", "1.6 Diesel", "6.5/100", "0.7");

    public CarExpanded getInfo(String header) {
        return car;
    }
}
