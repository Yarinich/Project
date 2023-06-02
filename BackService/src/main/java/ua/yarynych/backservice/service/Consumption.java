package ua.yarynych.backservice.service;

public interface Consumption {
    Double CONDITIONING_CONSUMPTION = 1.15;
    Double CONDITIONING_VENTILATION_CONSUMPTION = 1.2;
    Double VENTILATION_CONSUMPTION = 1.05;
    Double HEATING_CONSUMPTION = 1.1;
    Double HEATING_VENTILATION_CONSUMPTION = 1.15;
    Double RECIRCULATION_CONSUMPTION = 1.05;
    Double DEMISTING_CONSUMPTION = 1.12;

    Integer AIR_CONST = 1000;
    Double AIR_RO = 1.204;

    Double WAGON = 3.5;
    Double SEDAN = 3.0;
    Double HATCHBACK = 2.5;
    Double CROSSOVER = 3.5;
    Double SUV = 4.5;
    Double MINIVAN = 5.0;
    Double SPORT = 2.5;
    Double COUPE = 2.5;
}
