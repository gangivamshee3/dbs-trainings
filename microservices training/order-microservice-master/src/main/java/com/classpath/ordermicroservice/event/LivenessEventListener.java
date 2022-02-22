package com.classpath.ordermicroservice.event;

import com.classpath.ordermicroservice.model.*;
import com.classpath.ordermicroservice.repository.OrderRepository;
import com.classpath.ordermicroservice.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class LivenessEventListener {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
   // private final PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker();

    @EventListener
    public void onLivenessStateChange(AvailabilityChangeEvent<LivenessState> event){
        switch (event.getState()){
            case BROKEN:
                log.error(" ================== Application is  Broken =================== :: {}", event.getSource());
                break;
            case CORRECT:
                log.info(" ==================  Application is healthy ===================:: {}", event.getSource());
                break;
        }
    }
    @EventListener
    public void onReadinessStateChange(AvailabilityChangeEvent<ReadinessState> event){
        switch (event.getState()){
            case REFUSING_TRAFFIC:
                log.error(" ================== Application Readiness is  Broken =================== :: {}", event.getSource());
                break;
            case ACCEPTING_TRAFFIC:
                log.info(" ==================  Application Readiness is healthy ===================:: {}", event.getSource());
                break;
        }
    }

    @EventListener
    public void onApplicationLoadEvent(ApplicationReadyEvent event){
        IntStream.range(0, 100).forEach(index -> {
                    Order order = Order.builder()
                            .price(faker.number().randomDouble(2, 16000, 25000))
                            .date(faker.date().past(4, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                            .customerName(faker.name().firstName())
                            .customerEmail(faker.internet().emailAddress())
                            .build();
                    IntStream.range(0, 3).forEach(value -> {
                        LineItem lineItem = LineItem.builder()
                                .price(faker.number().randomDouble(2, 500, 2000))
                                .qty(faker.number().numberBetween(2, 6))
                                .name(faker.commerce().productName())
                                .build();
                        order.addLineItem(lineItem);
                    });
                    this.orderRepository.save(order);
                });
        log.info("========= Application is booted up!! ============");
    }

   // @EventListener
   /* public void loadUsersForSecurity(ApplicationReadyEvent event){

        Role roleUser = Role.builder().role("ROLE_USER").build();
        Role roleAdmin = Role.builder().role("ROLE_ADMIN").build();
        Role roleSuperAdmin = Role.builder().role("ROLE_SUPER_ADMIN").build();

        List<Role> roles = Arrays.asList(roleUser, roleAdmin, roleSuperAdmin);

        IntStream.range(0, roles.size()).forEach(index -> {
            User user = User
                    .builder()
                    .dob(faker.date().birthday(20, 45).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .email(faker.internet().emailAddress())
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .password(this.passwordEncoder.encode("welcome"))
                    .build();
            Address address = Address.builder().city(faker.address().cityName()).state(faker.address().state()).zipCode(faker.address().zipCode()).build();
            user.addAddress(address);
            user.addRole(roles.get(index));
            this.userRepository.save(user);
        });
        }
*/
}