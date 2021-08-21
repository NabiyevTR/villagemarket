package ru.ntr.villagemarket.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.config.AppProperties;

@Aspect
@Component
public class LoggingControllerAspect {

    private static final Logger log = LogManager.getLogger(LoggingControllerAspect.class);
    private final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Pointcut("execution(* ru.ntr.villagemarket.controller.*.*.*(..))")
    public void controllerLogger() {
    }

    @Before("controllerLogger()")
    public void before(JoinPoint joinPoint) {

        if (!AppProperties.controllerLogging) return;

        try {
            log.info(
                    String.format(
                            "Request in %s: %s",
                            joinPoint.getThis(),
                            ow.writeValueAsString(joinPoint.getArgs())
                    )
            );
        } catch (JsonProcessingException e) {
            log.warn("Cannot convert to JSON");
        }
    }


    @AfterReturning(value = "controllerLogger()", returning = "obj")
    public void afterReturning(Object obj) {

        if (!AppProperties.controllerLogging) return;

        try {
            log.info(
                    String.format(
                            "Response: %s",
                            ow.writeValueAsString(obj)
                    )
            );
        } catch (JsonProcessingException e) {
            log.warn("Cannot convert to JSON");
        }
    }

    @AfterThrowing(value = "controllerLogger()", throwing = "e")
    public void afterThrow(JoinPoint joinPoint, Throwable e) {

        if (!AppProperties.controllerLogging) return;

        log.error("Error: ", e.getClass().getName());
    }

}
