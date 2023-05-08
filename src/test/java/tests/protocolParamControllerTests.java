package tests;

import base.BaseTest;
import microservices.steps.protocol.ProtocolSteps;
import org.testng.annotations.Test;

public class protocolParamControllerTests extends BaseTest {
    ProtocolSteps protocolSteps = new ProtocolSteps();
    @Test
    public void getCurrentProtocol(){
        System.out.println(protocolSteps.getProtocolHistoryChange());
    }
}
