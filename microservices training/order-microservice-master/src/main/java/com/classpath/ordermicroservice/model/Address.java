package com.classpath.ordermicroservice.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
@Table(name="address")
public class Address {
    @Id
    @Column(name="user_id")
    private Long addressId;
    private String city;
    private String state;
    private String zipCode;
    @OneToOne
    @MapsId
    @JoinColumn(name="user_id")
    private User user;
}