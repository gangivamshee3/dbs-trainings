package com.classpath.ordermicroservice.model;

import com.classpath.ordermicroservice.contraint.MailId;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
//persistence annotations
@Entity
@Table(name="orders")
@Validated
@XmlRootElement
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    @NotEmpty(message = "customer name cannot be empty")
    private String customerName;
    @Email(message = "Invalid email address")
    //@MailId(message = "invalid email address passed")
    private String customerEmail;
    @Min(value = 15000, message = "min price for order is 15k")
    @Max(value = 50000, message = "max order price is 50k")
    private double price;
    @PastOrPresent(message = "order date cannot be in future")
    private LocalDate date;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<LineItem> lineItems;

    //scaffolding method
    public void addLineItem(LineItem lineItem){
        if(this.lineItems == null){
            this.lineItems = new HashSet<>();
        }
        this.lineItems.add(lineItem);
        lineItem.setOrder(this);
    }
}