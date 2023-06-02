package ua.yarynych.carservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private Conditioner conditioner;
    private Sensor innerSensor;
    private Sensor outerSensor;
}
