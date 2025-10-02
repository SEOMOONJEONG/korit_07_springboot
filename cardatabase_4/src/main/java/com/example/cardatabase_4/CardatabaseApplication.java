package com.example.cardatabase_4;//package com.example.cardatabase_4;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class Cardatabase4Application {
//
//	public static void main(String[] args) {
//		SpringApplication.run(Cardatabase4Application.class, args);
//	}
//
//}
import com.example.cardatabase_4.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
// 스프링 부트 애플리케이션임을 나타내는 애너테이션
// @Configuration, @EnableAutoConfiguration, @ComponentScan 등을 포함해
// 자동 설정과 컴포넌트 스캔을 활성화시킴

public class CardatabaseApplication implements CommandLineRunner {
	// 이 클래스는 스프링 부트 애플리케이션의 메인 클래스이자,
	// CommandLineRunner 인터페이스를 구현해서 애플리케이션 시작 후
	// run() 메서드를 실행하도록 함

	private static final Logger logger = LoggerFactory.getLogger(
			CardatabaseApplication.class
	);
	// 로깅을 위한 Logger 객체 생성
	// LoggerFactory에서 이 클래스(CardatabaseApplication)의 이름을 가진 로거를 생성


	private final CarRepository repository;
	private final OwnerRepository ownerRepository;
	private final AppUserRepository userRepository;

	public CardatabaseApplication(CarRepository repository, OwnerRepository ownerRepository, AppUserRepository userRepository) {
		this.repository = repository;
		this.ownerRepository = ownerRepository;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CardatabaseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 소유자 객체를 추가
		// `run` 메서드는 스프링 부트가 시작될 때 한번 실행되는 메서드 / run = 실행하기 위한 메서드
		// `String ... args`로 커맨드 라인 인자를 받을 수 있으며, / String ... = 가변 인자
		// throws Exception으로 예외가 발생하면 호출자에게 넘길 수 있도록 정의된 것
		Owner owner1 = new Owner("일", "김");
		Owner owner2 = new Owner("이", "김");
		// 다수의 객체를 한 번에 저장하는 메서드를 처음 사용해보겠습니다.
		ownerRepository.saveAll(Arrays.asList(owner1, owner2));

		// 그리고 Car의 생성자에 feild를 추가했기 때문에 오류나는 것을 막기 위해 owner들을 추가해주겠습니다.
		repository.save(new Car("Kia", "Seltos", "Chacol", "370SU5690", 2020, 30000000, owner1));
		repository.save(new Car("Hyundai", "Sonata", "White", "123GA456", 2025, 25000000, owner2));
		repository.save(new Car("Honda", "CR-V", "Black-White", "981NU7654", 2024, 45000000, owner2));

		for (Car car: repository.findAll()){
			logger.info("brand : {}, model : {}", car.getBrand(), car.getModel());
		}

		// AppUser 더미 데이터를 추가
		// 저 위에 보시면 Owner의 경우에는 owner1 / owner2 만들어가지고 ownerRepository에 저장했었습니다.
		userRepository.save(new AppUser("user", "$2a$12$gz2bOhHCqlSeULOQqr9t6OqwWc0sbEPwDeLQ7CcdG74w6ckwzdp3K", "USER"));
		userRepository.save(new AppUser("admin", "$2a$12$8az1J7jLmwl5FgkQEPRAF.7OVnOBt1Jfcx.GPmBoHuIDZT8H8v6wS", "ADMIN"));

	}
}
