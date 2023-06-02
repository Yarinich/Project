package ua.yarynych.backservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ua.yarynych.backservice.entity.car.Car;
import ua.yarynych.backservice.entity.car.CarExpanded;
import ua.yarynych.backservice.entity.car.Conditioner;
import ua.yarynych.backservice.entity.car.features.State;
import ua.yarynych.backservice.service.*;

import java.util.*;


@Service
public class AdviceServiceImpl implements AdviceService, WeatherConstants, AdviseConstants, Consumption {

    private final Map<String, List<String>> recommendations = new HashMap<>();

    @Autowired
    private WebClient localApiClient;

    @Autowired
    private CarService carService;

    public Map<String, List<String>> createRecommendation(String email) {
        System.out.println("lol");
        Mono<Car> car = readCarInfo();
        Map<String, List<String>> recommendations = new HashMap<>();
        recommendations.put("advise", recommendations(Objects.requireNonNull(car.block())));
        return recommendations;
    }

    public Map<String, List<String>> refreshRecommendation(String email) {
        Mono<Car> car = readCarInfo();
        Map<String, List<String>> recommendations = new HashMap<>();
        recommendations.put("advise", recommendations(car.block()));
        return recommendations;
    }

    private Mono<Car> readCarInfo() {
        return localApiClient
                .post()
                .uri("/resources")
                .retrieve()
                .bodyToMono(Car.class);
    }

    private List<String> recommendations(Car car) {
        List<String> recommendations = new ArrayList<>();
        Conditioner conditioner = new Conditioner();
        System.out.println(car.getConditioner().getWorkTemperature());

        if(car.getConditioner().getWorkTemperature() == null) {
            recommendations.add(START_WORK_PROPERTIES);
            return recommendations;
        }

        Integer outerTemperature = car.getOuterSensor().getTemperature();
        Integer innerTemperature = car.getInnerSensor().getTemperature();
        Integer outerHumidity = car.getOuterSensor().getHumidity();
        Integer innerHumidity = car.getInnerSensor().getHumidity();

        if(outerTemperature == null || innerTemperature == null || outerHumidity == null || innerHumidity == null) {
            recommendations.add(ERROR_READ_VALUES);
            return recommendations;
        }

        boolean humidityValidation = Math.abs(innerHumidity - BEST_HUMIDITY) <= Math.abs(outerHumidity - BEST_HUMIDITY);

        if (innerTemperature >= 50) {
            recommendations.add(EXTREMELY_HIGH_TEMPERATURE);
            return recommendations;
        } else if(outerTemperature >= 30 && innerTemperature >= 25) {
            conditioner.setStatus(true);
            if(humidityValidation) {
                conditioner.setState(State.CONDITIONING);
            } else {
                conditioner.setState(State.CONDITIONING_VENTILATION);
            }
            conditioner.setWorkTemperature(calculateBestWorkTemperature(outerTemperature));
            conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
            recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            return recommendations;
        } else if(outerTemperature >= 30 && innerTemperature >= 15) {
            if(humidityValidation) {
                if(conditioner.isStatus()) {
                    recommendations.add(TURN_OFF_CONDITIONER);
                } else {
                    recommendations.add(KEEP_CONDITIONER_OFF);
                }
            } else {
                conditioner.setStatus(true);
                conditioner.setState(State.VENTILATION);
                conditioner.setWorkTemperature(calculateBestWorkTemperature(outerTemperature));
                conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
                recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            }
            return recommendations;
        } else if(outerTemperature >= 30 && innerTemperature >= 10) {
            if(conditioner.isStatus()) {
                recommendations.add(TURN_OFF_CONDITIONER);
            } else {
                recommendations.add(KEEP_CONDITIONER_OFF);
            }
            return recommendations;
        } else if (outerTemperature >= 30 && innerTemperature >= 0) {
            conditioner.setStatus(true);
            if(humidityValidation) {
                conditioner.setState(State.HEATING);
            } else {
                conditioner.setState(State.HEATING_VENTILATION);
            }
            conditioner.setWorkTemperature(calculateBestWorkTemperature(outerTemperature));
            conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
            recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            return recommendations;
        } else if(outerTemperature >= 30) {
            recommendations.add(EXTREMELY_LOW_TEMPERATURE);
            return recommendations;
        }

        if(outerTemperature >= 25 && innerTemperature >= 25) {
            conditioner.setStatus(true);
            if(humidityValidation) {
                conditioner.setState(State.CONDITIONING);
            } else {
                conditioner.setState(State.CONDITIONING_VENTILATION);
            }
            conditioner.setWorkTemperature(calculateBestWorkTemperature(outerTemperature));
            conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
            recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            return recommendations;
        } else if(outerTemperature >= 25 && innerTemperature >= 15) {
            if(humidityValidation) {
                if(conditioner.isStatus()) {
                    recommendations.add(TURN_OFF_CONDITIONER);
                } else {
                    recommendations.add(KEEP_CONDITIONER_OFF);
                }
            } else {
                conditioner.setStatus(true);
                conditioner.setState(State.VENTILATION);
                conditioner.setWorkTemperature(calculateBestWorkTemperature(outerTemperature));
                conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
                recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            }
            return recommendations;
        } else if(outerTemperature >= 25 && innerTemperature >= 10) {
            if(conditioner.isStatus()) {
                recommendations.add(TURN_OFF_CONDITIONER);
            } else {
                recommendations.add(KEEP_CONDITIONER_OFF);
            }
            return recommendations;
        } else if (outerTemperature >= 25 && innerTemperature >= 0) {
            conditioner.setStatus(true);
            if(humidityValidation) {
                conditioner.setState(State.HEATING);
            } else {
                conditioner.setState(State.HEATING_VENTILATION);
            }
            conditioner.setWorkTemperature(calculateBestWorkTemperature(outerTemperature));
            conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
            recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            return recommendations;
        } else if(outerTemperature >= 25) {
            recommendations.add(EXTREMELY_LOW_TEMPERATURE);
            return recommendations;
        }

        if(outerTemperature >= 18 && innerTemperature >= 25) {
            conditioner.setStatus(true);
            conditioner.setState(State.VENTILATION);
            conditioner.setWorkTemperature(calculateBestWorkTemperature(outerTemperature));
            conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
            recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            return recommendations;
        } else if (outerTemperature >= 18 && innerTemperature >= 10) {
            if(humidityValidation) {
                if(conditioner.isStatus()) {
                    recommendations.add(TURN_OFF_CONDITIONER);
                } else {
                    recommendations.add(KEEP_CONDITIONER_OFF);
                }
            } else {
                conditioner.setStatus(true);
                conditioner.setState(State.VENTILATION);
                conditioner.setWorkTemperature(calculateBestWorkTemperature(outerTemperature));
                conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
                recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            }
            return recommendations;
        } else if (outerTemperature >= 18 && innerTemperature >= 0) {
            conditioner.setStatus(true);
            if(humidityValidation) {
                conditioner.setState(State.HEATING);
            } else {
                conditioner.setState(State.HEATING_VENTILATION);
            }
            conditioner.setWorkTemperature(calculateBestHeatingWorkTemperature(outerTemperature));
            conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
            recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            return recommendations;
        } else if(outerTemperature >= 18) {
            recommendations.add(EXTREMELY_LOW_TEMPERATURE);
            return recommendations;
        }

        if(outerTemperature >= 15 && innerTemperature >= 25) {
            conditioner.setStatus(true);
            conditioner.setState(State.DEMISTING);
            conditioner.setWorkTemperature(calculateBestWorkTemperature(outerTemperature));
            conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
            recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            return recommendations;
        } else if (outerTemperature >= 15 && innerTemperature >= 10) {
            if(humidityValidation) {
                if(conditioner.isStatus()) {
                    recommendations.add(TURN_OFF_CONDITIONER);
                } else {
                    recommendations.add(KEEP_CONDITIONER_OFF);
                }
            } else {
                conditioner.setStatus(true);
                conditioner.setState(State.VENTILATION);
                conditioner.setWorkTemperature(calculateBestWorkTemperature(outerTemperature));
                conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
                recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            }
            return recommendations;
        } else if (outerTemperature >= 15 && innerTemperature >= 0) {
            conditioner.setStatus(true);
            if(humidityValidation) {
                conditioner.setState(State.HEATING);
            } else {
                conditioner.setState(State.HEATING_VENTILATION);
            }
            conditioner.setWorkTemperature(calculateBestHeatingWorkTemperature(outerTemperature));
            conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
            recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            return recommendations;
        } else if(outerTemperature >= 15) {
            recommendations.add(EXTREMELY_LOW_TEMPERATURE);
            return recommendations;
        }

        if(outerTemperature >= 0 && innerTemperature >= 15) {
            conditioner.setStatus(true);
            conditioner.setState(State.DEMISTING);
            conditioner.setWorkTemperature(calculateBestWorkTemperature(outerTemperature));
            conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
            recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            return recommendations;
        } else if (outerTemperature >= 0 && innerTemperature >= 10) {
            if(humidityValidation) {
                if(conditioner.isStatus()) {
                    recommendations.add(TURN_OFF_CONDITIONER);
                } else {
                    recommendations.add(KEEP_CONDITIONER_OFF);
                }
            } else {
                conditioner.setStatus(true);
                conditioner.setState(State.VENTILATION);
                conditioner.setWorkTemperature(calculateBestWorkTemperature(outerTemperature));
                conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
                recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            }
            return recommendations;
        } else if (outerTemperature >= 0 && innerTemperature >= 0) {
            conditioner.setStatus(true);
            if(humidityValidation) {
                conditioner.setState(State.HEATING);
            } else {
                conditioner.setState(State.HEATING_VENTILATION);
            }
            conditioner.setWorkTemperature(calculateBestHeatingWorkTemperature(outerTemperature));
            conditioner.setPower(calculatePower(conditioner.getWorkTemperature(), innerTemperature));
            recommendations.add(formulateComfortRecommendation(conditioner) + calculateConsumption(conditioner, innerTemperature));
            return recommendations;
        } else if(outerTemperature >= 10) {
            recommendations.add(EXTREMELY_LOW_TEMPERATURE);
            return recommendations;
        }

        recommendations.add(ERROR_IN_READING_VALUES);
        return recommendations;
    }

    private Integer calculateBestWorkTemperature(Integer outerTemperature) {
        if(outerTemperature >= 30) {
            return 24;
        } else if(outerTemperature >= 27) {
            return 23;
        } else if(outerTemperature >= 24) {
            return 22;
        } else if(outerTemperature >= 21) {
            return 21;
        } else {
            return 20;
        }
    }

    private Integer calculateBestHeatingWorkTemperature(Integer outerTemperature) {
        if(outerTemperature >= 10) {
            return 15;
        } else if(outerTemperature >= 5) {
            return 16;
        } else if(outerTemperature >= 0) {
            return 17;
        } else if(outerTemperature >= -5) {
            return 20;
        } else {
            return 25;
        }
    }

    private Integer calculatePower(Integer temperature, Integer innerTemperature) {
        if (Math.abs(temperature - innerTemperature) <= 5)
            return 25;
        else if (Math.abs(temperature - innerTemperature) <= 10)
            return 50;
        else return 100;
    }

    private String formulateComfortRecommendation(Conditioner conditioner) {
        return "Most comfort recommendation: turn on conditioner; set work mode: " + conditioner.getState() + "; set work temperature: " +
                conditioner.getWorkTemperature() + "; set conditioner power: " + conditioner.getPower();
    }

    private String calculateConsumption(Conditioner conditioner, Integer innerTemperature) {
        CarExpanded car = carService.getInfo("yarinich.vitalya@gmail.com");
        Double consumption;
        Double mass;

        if(State.CONDITIONING.equals(conditioner.getState())) {
            consumption = CONDITIONING_CONSUMPTION;
        } else if(State.CONDITIONING_VENTILATION.equals(conditioner.getState())) {
            consumption = CONDITIONING_VENTILATION_CONSUMPTION;
        } else if(State.VENTILATION.equals(conditioner.getState())) {
            consumption = VENTILATION_CONSUMPTION;
        } else if(State.HEATING.equals(conditioner.getState())) {
            consumption = HEATING_CONSUMPTION;
        } else if(State.HEATING_VENTILATION.equals(conditioner.getState())) {
            consumption = HEATING_VENTILATION_CONSUMPTION;
        } else if(State.RECIRCULATION.equals(conditioner.getState())) {
            consumption = RECIRCULATION_CONSUMPTION;
        } else {
            consumption = DEMISTING_CONSUMPTION;
        }

        if(car.getClassification().equalsIgnoreCase("wagon")) {
            mass = WAGON;
        } else if (car.getClassification().equalsIgnoreCase("sedan")) {
            mass = SEDAN;
        } else if(car.getClassification().equalsIgnoreCase("hatchback")) {
            mass = HATCHBACK;
        } else if(car.getClassification().equalsIgnoreCase("crossover")) {
            mass = CROSSOVER;
        } else if(car.getClassification().equalsIgnoreCase("suv")) {
            mass = SUV;
        } else if(car.getClassification().equalsIgnoreCase("minivan")) {
            mass = MINIVAN;
        } else if(car.getClassification().equalsIgnoreCase("sport")) {
            mass = SPORT;
        } else {
            mass = COUPE;
        }

        Integer temp = Math.abs(innerTemperature - conditioner.getWorkTemperature());
        Double carConsumption = Double.parseDouble(car.getConsumption().substring(0, 3));
        double consumptionOverall = consumption * carConsumption;

        Double time = Math.floor(calculateTime(mass, temp, conditioner.getPower(), consumptionOverall / 2) / 60);
        System.out.println(car.getConsumption());
        System.out.println(Math.floor(calculateTime(mass, temp, conditioner.getPower(), consumptionOverall) / 60));

        return ". The time required to change the temperature, for these settings: " + time + " minutes; fuel consumption for this: " +
                (consumptionOverall - carConsumption) + "L";
    }

    public Double calculateTime(Double mass, Integer temp, Integer power, Double consumption) {
        return mass * AIR_CONST * AIR_RO * temp / (power * consumption);
    }

}
