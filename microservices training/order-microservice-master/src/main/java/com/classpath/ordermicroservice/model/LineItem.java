package com.classpath.ordermicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "order")
@ToString(exclude = "order")
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer itemId;
    @NotEmpty(message = "item name is mandatory")
    private String name;
    @Max(value=10, message = "max items can be 10")
    private int qty;
    private double price;

    @ManyToOne
    @JoinColumn(name="order_id", nullable = false)
    //owner of the relationship
    @JsonIgnore
    private Order order;
}