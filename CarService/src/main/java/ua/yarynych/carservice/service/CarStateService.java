package ua.yarynych.carservice.service;

import org.springframework.stereotype.Service;
import ua.yarynych.carservice.entity.Cache;
import ua.yarynych.carservice.entity.Car;
import ua.yarynych.carservice.entity.Conditioner;
import ua.yarynych.carservice.entity.Sensor;
import ua.yarynych.carservice.entity.features.State;

import javax.annotation.PostConstruct;

@Service
public class CarStateService {
    private final Integer BEST_HUMIDITY = 50;
    private final Integer MIN_HUMIDITY = 0;
    private final Integer MAX_HUMIDITY = 100;

    private final Integer BEST_TEMPERATURE_MIN = 18;
    private final Integer BEST_TEMPERATURE_MAX = 25;

    private final Integer MIN_WORK_TEMPERATURE = 4;
    private final Integer MAX_WORK_TEMPERATURE = 32;

    private final Integer MIN_POWER = 1;
    private final Integer MAX_POWER = 100;

    private Cache carState;
    private Sensor innerSensor;
    private Sensor outerSensor;
    private Conditioner conditioner;

    @PostConstruct
    public void setDefaultValues() {
        Car car = new Car();
        setInnerSensor();
        car.setInnerSensor(innerSensor);
        setOuterSensor();
        car.setOuterSensor(outerSensor);
        setConditioner();
        car.setConditioner(conditioner);
        Cache cache = new Cache();
        cache.setCar(car);
        this.carState = cache;
    }

    public Car getCache() {
        return this.carState.getCar();
    }

    public Car getCarState() {
        return carState.getCar();
    }



    //Блок функціоналу внутрішнього сенсора
    public void setInnerSensor() {
        Sensor innerSensor = new Sensor();
        innerSensor.setHumidity(BEST_HUMIDITY);
        innerSensor.setTemperature((BEST_TEMPERATURE_MAX + BEST_TEMPERATURE_MIN)/2);
        this.innerSensor = innerSensor;
    }

    public void setInnerSensorTemperature(Integer temperature) {
        innerSensor.setTemperature(temperature);
    }

    public void setInnerSensorHumidity(Integer humidity) {
        if(humidity >= MAX_HUMIDITY) {
            innerSensor.setHumidity(MAX_HUMIDITY);
        } else if(humidity <= MIN_HUMIDITY) {
            innerSensor.setHumidity(MIN_HUMIDITY);
        } else {
            innerSensor.setHumidity(humidity);
        }
    }



    //Блок зовнішнього сенсора
    public void setOuterSensor() {
        Sensor outerSensor = new Sensor();
        outerSensor.setHumidity(BEST_HUMIDITY);
        outerSensor.setTemperature((BEST_TEMPERATURE_MAX + BEST_TEMPERATURE_MIN)/2);
        this.outerSensor = outerSensor;
    }

    public void setOuterSensorTemperature(Integer temperature) {
        outerSensor.setTemperature(temperature);
    }

    public void setOuterSensorHumidity(Integer humidity) {
        if(humidity >= MAX_HUMIDITY) {
            outerSensor.setHumidity(MAX_HUMIDITY);
        } else if(humidity <= MIN_HUMIDITY) {
            outerSensor.setHumidity(MIN_HUMIDITY);
        } else {
            outerSensor.setHumidity(humidity);
        }
    }



    //Блок функціоналу кондиціонера
    public void setConditioner() {
        Conditioner conditioner = new Conditioner();
        conditioner.setStatus(Boolean.FALSE);
        this.conditioner = conditioner;
    }

    public void setConditioner(boolean onoff, State state, Integer power, Integer workTemperature) {
        conditioner.setStatus(onoff);
        conditioner.setState(state);
        conditioner.setPower(power);
        conditioner.setWorkTemperature(workTemperature);
    }

    public void setConditionerStatus(String value) {
        if("on".equals(value)) {
            conditioner.setStatus(Boolean.TRUE);
        } else {
            conditioner.setStatus(Boolean.FALSE);
        }
    }

    public void setConditionerFunctions(String value) {
        conditioner.setState(State.valueOf(value));
    }

    public void setConditionerPower(Integer power) {
        if(power >= MAX_POWER) {
            conditioner.setPower(MAX_POWER);
        } else if(power <= MIN_POWER) {
            conditioner.setPower(MIN_POWER);
        } else {
            conditioner.setPower(power);
        }
    }

    public void setConditionerTemperature(Integer temperature) {
        if(temperature >= MAX_WORK_TEMPERATURE) {
            conditioner.setWorkTemperature(MAX_WORK_TEMPERATURE);
        } else if(temperature <= MIN_WORK_TEMPERATURE) {
            conditioner.setWorkTemperature(MIN_WORK_TEMPERATURE);
        } else {
            conditioner.setWorkTemperature(temperature);
        }
    }



    //Дод. блок
    public void readStateInfo(Conditioner conditioner, Sensor innerSensor, Sensor outerSensor) {
        Car car = new Car(conditioner, innerSensor, outerSensor);
        this.carState.setCar(car);
    }
}
