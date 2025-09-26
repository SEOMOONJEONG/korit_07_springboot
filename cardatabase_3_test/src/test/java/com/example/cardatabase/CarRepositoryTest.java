package com.example.cardatabase;

import com.example.cardatabase.domain.Car;
import com.example.cardatabase.domain.CarRepository;
import com.example.cardatabase.domain.Owner;
import com.example.cardatabase.domain.OwnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    /*
    @Test
    @DisplayName("차량 저장 메서드 테스트")
    void saveCar() {
        // given - 제반 준비 사항
        // Car Entity를 확인해봤을 때 field로 Owner를 요구하기 때문에
        // 얘부터 먼저 만들고 -> ownerRepository에 저장
        Owner owner = new Owner("Gemini", "GPT");
        ownerRepository.save(owner);
        // 그리고 Car 객체를 만들겁니다.
        Car car = new Car("Ford", "Mustang", "Red", "ABCDEF", 2021, 567890, owner);
        // when - 테스트 실행
        // 저장이 됐는가를 확인하기 위한 부분
        carRepository.save(car);
        // then - 그 결과가 어떠할지
        assertThat(carRepository.findById(car.getId())).isPresent();    // 이건 그냥 결과값이 하나일테니까

        assertThat(carRepository.findById(car.getId()).get().getBrand()).isEqualTo("Ford");
    }
    */
    /*
    @Test
    @DisplayName("예제 메서드 삭제 테스트")
    void deleteCar() {
        Owner owner1 = new Owner("Jeong", "SEO");
        Car car1 = new Car("Hyundai", "Avante", "White", "398GA4083", 2025, 4000000, owner1);
        // 저장확인
        carRepository.save(car1);
        ownerRepository.save(owner1);
        // 삭제
//        carRepository.deleteById(car1.getId());
        carRepository.deleteAll();      // 오너 세이브 안할 시 deleteAll() 오류 발생.
        // 삭제가 완료되었는지를 체크하는 assertThat()문이 필수적으로 요구됩니다.
//        assertThat(carRepository.findById(car1.getId())).isEmpty();
//        assertThat(carRepository.count()).isEqualTo(3);
        assertThat(carRepository.count()).isEqualTo(0);
    }
    */
    @Test
    @DisplayName("예제2 메서드 테스트")
    void findByBrandShouldReturnCar() {
        // given - Owner 하나 생성 및 저장 / Car 객체 3 대 생성 및 저장
        Owner owner1 = new Owner("One", "SEO");
        Car car1 = new Car("Hyundai", "Avante", "White", "398GA4083", 2025, 4000000, owner1);
        ownerRepository.save(owner1);
        carRepository.save(car1);
        Owner owner2 = new Owner("Two", "SEO");
        Car car2 = new Car("Ford", "Mustang", "Red", "176CO1758", 2021, 567890, owner2);
        Car car3 = new Car("Ford", "Bronco", "Black", "678HA3759", 2024, 8000000, owner2);
        ownerRepository.save(owner2);
        carRepository.save(car2);
        carRepository.save(car3);

        // when - carRepository.findByBrand("브랜드명") -> 근데 얘 결과 자료형이 List
        List<Car> fords = carRepository.findByBrand("Ford");

        // then 검증 - list 내부의 element의 자료형이 Car 객체일테니까,
        // 그 객체의 getBrand()의 결과값이 우리가 findByBrand()의 argument로 쓴 값과 동일한지 체크할 수 있겠네요. -> list(Size)로 확인
        assertThat(fords.get(0).getBrand()).isEqualTo("Ford");

        // 혹은, 현재 저희가 Ford 차량을 두 대 만들었으니까 size()의 결과값이 2겠죠
        assertThat(fords.size()).isEqualTo(2);

    }

}

