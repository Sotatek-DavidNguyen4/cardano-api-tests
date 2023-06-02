package microservices.token.models;

@lombok.Data
public class TokenModel {
    private String id;
    private String name;
    private String displayName;
    private String policy;
    private String fingerprint;
    private int txCount;
    private String supplyl;
    private String volumeIn24h;
    private String totalVolume;
    private int numberOfHolders;
    private String createdOn;
}
