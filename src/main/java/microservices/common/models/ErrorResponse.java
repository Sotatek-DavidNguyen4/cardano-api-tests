package microservices.common.models;

import lombok.Data;

@Data
public class ErrorResponse {
    private String errorMessage;
    private String errorCode;
}
