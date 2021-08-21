package ru.ntr.villagemarket.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingErrorAspect {

    private static final Logger log = LogManager.getLogger(LoggingErrorAspect.class);
    private final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Pointcut("execution(* ru.ntr.villagemarket.*.*.*.*(..))")
    public void errorLogger() {
    }

    @AfterThrowing(pointcut = "errorLogger()", throwing = "ex")
    public void logAfterThrowingAllMethods(JoinPoint jp, Exception ex) throws Throwable {
        log.error("Exception during: {} with ex: {}", constructLogMsg(jp), ex);
    }

    private String constructLogMsg(JoinPoint jp) {
        var args = Arrays.asList(jp.getArgs()).stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        var sb = new StringBuilder("@");
        sb.append(method.getName());
        sb.append(":");
        sb.append(args);
        return sb.toString();
    }

}
