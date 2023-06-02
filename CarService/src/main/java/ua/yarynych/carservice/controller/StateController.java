package ua.yarynych.carservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.yarynych.carservice.entity.Car;
import ua.yarynych.carservice.service.CarStateService;

@Controller
public class StateController {

    @Autowired
    private CarStateService carStateService;

    @GetMapping("/")
    public String carPage(Model model) {
        model.addAttribute("title", "Машина");

        Car car = carStateService.getCache();
        model.addAttribute("conditioner", car.getConditioner());
        model.addAttribute("innerSensor", car.getInnerSensor());
        model.addAttribute("outerSensor", car.getOuterSensor());

        return "car";
    }

    @PostMapping("/setConditions")
    public void setConditions(@RequestParam Integer innerTemp) {

    }

    @PostMapping("/setInnerTemp")
    public String setInnerTemp(@RequestParam Integer innerTemp) {
        carStateService.setInnerSensorTemperature(innerTemp);
        return "redirect:/";
    }

    @PostMapping("/setInnerHumidity")
    public String setInnerHumidity(@RequestParam Integer innerHumidity) {
        carStateService.setInnerSensorHumidity(innerHumidity);
        return "redirect:/";
    }

    @PostMapping("/setOuterTemp")
    public String setOuterTemp(@RequestParam Integer outerTemp) {
        carStateService.setOuterSensorTemperature(outerTemp);
        return "redirect:/";
    }

    @PostMapping("/setOuterHumidity")
    public String setOuterHumidity(@RequestParam Integer outerHumidity) {
        carStateService.setOuterSensorHumidity(outerHumidity);
        return "redirect:/";
    }

    @PostMapping("/setOnOff")
    public String setStatus(@RequestParam(value = "on_off", required = false) String checkboxValue) {
        carStateService.setConditionerStatus(checkboxValue);
        return "redirect:/";
    }

    @PostMapping("/setFunction")
    public String setState(@RequestParam(value = "functions", required = false) String checkboxValue) {
        carStateService.setConditionerFunctions(checkboxValue);
        return "redirect:/";
    }

    @PostMapping("/setPower")
    public String setPower(@RequestParam Integer power) {
        carStateService.setConditionerPower(power);
        return "redirect:/";
    }

    @PostMapping("/setTemperature")
    public String setTemperature(@RequestParam Integer temperature) {
        carStateService.setConditionerTemperature(temperature);
        return "redirect:/";
    }
}
