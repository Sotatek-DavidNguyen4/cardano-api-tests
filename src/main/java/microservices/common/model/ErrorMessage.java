package microservices.common.model;

import lombok.Data;

@Data
public class ErrorMessage {
    private String errorCode;
    private String errorMessage;
}
