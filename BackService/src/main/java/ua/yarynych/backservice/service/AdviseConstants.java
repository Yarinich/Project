package ua.yarynych.backservice.service;

public interface AdviseConstants {
    String EXTREMELY_HIGH_TEMPERATURE = "The temperature is extremely high. It is necessary to cool down the cabin temperature of the car to at least 40 degrees in order to be safe in it.";
    String TURN_OFF_CONDITIONER = "It is better to turn off the air conditioner. The temperature and air humidity in the cabin are comfortable for these weather conditions.";
    String KEEP_CONDITIONER_OFF = "Do not turn on the air conditioner in the car. The temperature and air humidity in the cabin are comfortable for these weather conditions.";
    String EXTREMELY_LOW_TEMPERATURE = "The temperature is extremely low. It is necessary to heat the cabin of the car to at least 0 degrees in order to be safe in it.";
    String ERROR_IN_READING_VALUES = "Error happend during reading values";
    String START_WORK_PROPERTIES = "Please, choose temperature in cabin (conditioner settings), what you wish to have, for starting work.";
    String ERROR_READ_VALUES = "Something wrong with connection to car sensors. Please check if it work in your car.";
}
