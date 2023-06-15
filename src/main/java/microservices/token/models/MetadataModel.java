package microservices.token.models;

import lombok.Data;

@Data
public class MetadataModel {
    private String url;
    private String ticker;
    private int decimals;
    private String logo;
    private String description;
}
