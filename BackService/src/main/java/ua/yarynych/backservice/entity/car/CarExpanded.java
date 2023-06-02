package ua.yarynych.backservice.entity.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarExpanded {
    private String userEmail;
    private String brand;
    private String model;
    private String classification;
    private String engine;
    private String consumption;
    private String air;
}
