package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.steps.protocol.ProtocolSteps;
import org.testng.annotations.Test;

@Epic("cardano")
@Feature("api-protocol")
public class protocolParamControllerTests extends BaseTest {
    ProtocolSteps protocolSteps = new ProtocolSteps();
    @Test(description = "verify that get protocal successfull", groups={"protocol"})
    public void getCurrentProtocol(){
        System.out.println(protocolSteps.getProtocolHistoryChange() + "");
    }
}
