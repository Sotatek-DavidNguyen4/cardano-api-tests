package data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DataUser {
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("type")
    public String type;
}
