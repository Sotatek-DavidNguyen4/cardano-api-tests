package microservices.contract.models;

import lombok.Data;

import java.util.List;

@Data
public class NativeScriptOfContract {
    private String type;
    private List<Script> scripts;

    @Data
    public class Script{
        public String type;
        public String keyHash;
    }
}
