package microservices.steps.protocol;

import core.BaseApi;
import io.qameta.allure.Step;
import org.testng.annotations.Test;

public class ProtocolSteps extends BaseApi {

    @Step("create Folder")
    public String getProtocolHistoryChange(){
        sendGet("/protocol/current");
        return getJsonAsString();
    }
}
