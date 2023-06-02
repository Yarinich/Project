package ua.yarynych.carservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.yarynych.carservice.entity.features.State;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conditioner {
    private boolean status;
    private State state;
    private Integer power;
    private Integer workTemperature;
}
