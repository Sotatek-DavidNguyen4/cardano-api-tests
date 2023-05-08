package microservices.steps.protocol;

import core.BaseApi;
import org.testng.annotations.Test;

public class ProtocolSteps extends BaseApi {

    @Test
    public String getProtocolHistoryChange(){
        sendGet("/protocol/current");
        return getJsonAsString();
    }
}
