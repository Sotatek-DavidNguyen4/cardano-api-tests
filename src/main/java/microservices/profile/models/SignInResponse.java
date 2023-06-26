package microservices.profile.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInResponse {
    public String token;
    public String tokenType;
    public String email;
    public String refreshToken;
}
