package ua.yarynych.backservice.service;

import ua.yarynych.backservice.entity.car.CarExpanded;

public interface CarService {
    CarExpanded getInfo(String header);
}
