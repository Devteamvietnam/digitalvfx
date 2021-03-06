package com.devteam.module.customer.entity;

import com.devteam.module.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    private String streetAddress;
    private @NonNull String city;
    private @NonNull String stateCode;
    private @NonNull String country;
    private @NonNull String zipCode;

    @ManyToOne
    @JsonBackReference
    private Customer customer;
}
