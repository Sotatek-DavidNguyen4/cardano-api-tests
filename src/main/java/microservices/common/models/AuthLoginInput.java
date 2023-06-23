package microservices.common.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthLoginInput {
    private String email;
    private String password;
    private String type;
}
