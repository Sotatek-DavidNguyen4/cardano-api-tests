package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.delegation.models.PoolDetailHeaderModel;
import microservices.delegation.steps.DelegationControllerSteps;
import org.testng.annotations.Test;

@Epic("cardano")
@Feature("api-delegation")
public class DelegationControllerTests extends BaseTest {
    DelegationControllerSteps delegationControllerSteps = new DelegationControllerSteps();
    @Test(description = "verify that get data for pool detail successfully", groups={"delegation", "delegation-detail"})
    public void getCurrentProtocol(){
        Object poolView = "pool1547tew8vmuj0g6vj3k5jfddudextcw6hsk2hwgg6pkhk7lwphe6";
        //successfully
        PoolDetailHeaderModel actualPoolDetailHeader = (PoolDetailHeaderModel) delegationControllerSteps
                .getDataForPoolDetail(poolView)
                .validateStatusCode(200)
                .saveResponseObject(PoolDetailHeaderModel.class);

        delegationControllerSteps
                .verifyAllAttributeExistsOrNot(actualPoolDetailHeader);

        // wait for confirming that status code is 400 or 500
/*        //poolView = "%^&&&"
        poolView = "%^&&&";
        delegationControllerSteps
                .getDataForPoolDetail(poolView)
                .validateStatusCode(400);

        //poolView = "null"
        poolView = "null";
        delegationControllerSteps
                .getDataForPoolDetail(poolView)
                .validateStatusCode(400);

        //poolView = "blank"
        poolView = "";
        delegationControllerSteps
                .getDataForPoolDetail(poolView)
                .validateStatusCode(400);

        //poolView = 1233333333
        poolView = 1233333333;
        delegationControllerSteps
                .getDataForPoolDetail(poolView)
                .validateStatusCode(400);*/
    }
}
