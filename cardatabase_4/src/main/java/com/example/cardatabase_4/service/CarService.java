package com.example.cardatabase_4.service;

import com.example.cardatabase_4.domain.Car;
import com.example.cardatabase_4.domain.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // 모든 자동차 목록을 조회한다고 가정하겠습니다.
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    // 새로운 자동차 저장
    public Car addCar(Car car) {
        return carRepository.save(car);

    }
}
