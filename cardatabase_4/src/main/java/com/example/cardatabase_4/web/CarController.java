package com.example.cardatabase_4.web;

import com.example.cardatabase_4.domain.Car;
import com.example.cardatabase_4.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // 1. 모든 자동차 정보 조회(GET /api/cars)
    @GetMapping("/cars")
    public List<Car> getCars() {
        return carService.getCars();
    }

    // 2. 차량 한 대 추가 (POST /api/cars)
    @PostMapping("/cars")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car savedCar = carService.addCar(car);

        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }
}
