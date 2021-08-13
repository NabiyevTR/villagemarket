package ru.ntr.villagemarket.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingErrorAspect {

    private static final Logger log = LogManager.getLogger(LoggingErrorAspect.class);
    private final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Pointcut("execution(* ru.ntr.villagemarket.*.*.*.*(..))")
    public void errorLogger() {
    }

    @AfterThrowing(pointcut = "errorLogger()", throwing = "ex")
    public void logAfterThrowingAllMethods(Exception ex) throws Throwable
    {
        log.error("Error: " , ex.getMessage());
    }

}
