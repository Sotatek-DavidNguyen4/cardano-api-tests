package microservices.profile.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    public Object address;
    public String email;
    public String avatar;
    public int sizeNote;
    public int sizeBookmark;
    public String lastLogin;
}
