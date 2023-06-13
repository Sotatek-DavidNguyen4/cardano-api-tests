package microservices.token.models;

import com.google.gson.annotations.SerializedName;

@lombok.Data
public class TokenModel {
    private String id;
    private String name;
    private String displayName;
    private String policy;
    private String fingerprint;
    private int txCount;
    private String supply;
    private String volumeIn24h;
    private String totalVolume;
    private int numberOfHolders;
    @SerializedName("createdOn")
    private String time;
}
