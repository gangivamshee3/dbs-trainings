package com.classpath.ordermicroservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
@Slf4j
public class ApplicationHealthController {

    private final ApplicationEventPublisher eventPublisher;

    private final ApplicationAvailability availability;

    @PostMapping("/liveness")
    public void updateLivenessHealth(){
        ApplicationAvailability availability = this.availability;
        LivenessState livenessState = availability.getLivenessState();
        //toggle the liveness stage
        LivenessState updatedStatus = livenessState == LivenessState.BROKEN ? LivenessState.CORRECT : LivenessState.BROKEN;
        Object state = updatedStatus == LivenessState.CORRECT ? "System is back ": new IllegalArgumentException("Application is not healthy");
        AvailabilityChangeEvent.publish(this.eventPublisher, state , updatedStatus);
        log.info(" Updated Liveness State:: "+ availability.getLivenessState());
    }

    @PostMapping("/readiness")
    public void updateReadinessHealth(){
        ApplicationAvailability availability = this.availability;
        ReadinessState readinessState = availability.getReadinessState();
        //toggle the readiness stage
        ReadinessState updatedStatus = readinessState == ReadinessState.REFUSING_TRAFFIC ? ReadinessState.ACCEPTING_TRAFFIC : ReadinessState.REFUSING_TRAFFIC;
        Object state = updatedStatus == ReadinessState.ACCEPTING_TRAFFIC ? "System is back ": new IllegalArgumentException("Application is not ready");
        AvailabilityChangeEvent.publish(this.eventPublisher, state , updatedStatus);
        log.info(" Updated Readiness State:: "+ availability.getReadinessState());
    }
}