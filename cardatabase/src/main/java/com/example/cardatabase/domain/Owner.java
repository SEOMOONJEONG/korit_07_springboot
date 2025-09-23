package com.example.cardatabase.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity         // 테이블을 만들기 위한 것
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor

public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ownerId;

    @NonNull
    private final String firstName;
    @NonNull
    private final String lastName;

    // 소유자는 다수의 차들을 가질 수 있기 때문에 collections를 사용(list)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Car> cars;
}
