package ru.ntr.villagemarket.responses;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Data
@SuperBuilder
public class Response {
    @Builder.Default
    private int httpStatus = HttpStatus.OK.value();
    @Builder.Default
    private String errorMessage ="";
}
