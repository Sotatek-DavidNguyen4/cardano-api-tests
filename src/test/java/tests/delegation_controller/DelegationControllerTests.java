package tests.delegation_controller;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.delegation.models.PoolDetailHeaderModel;
import microservices.delegation.steps.DelegationControllerSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.util.Map;

@Epic("cardano")
@Feature("api-delegation")
public class DelegationControllerTests extends BaseTest {
    DelegationControllerSteps delegationControllerSteps = new DelegationControllerSteps();
    Object poolView = "pool1547tew8vmuj0g6vj3k5jfddudextcw6hsk2hwgg6pkhk7lwphe6";
    @Test(description = "verify that get data for pool detail", groups={"delegation", "delegation-detail"})
    public void getPoolDetailHeader(){

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
    @Test(description = "verify that get data for pool detail delegators successfully", groups={"delegation", "delegation-detail-delegators"})
    public void getPoolDetailDelegatorSuccessfully(){
        Map<String, Object> param = new CreateParameters().withPoolView(poolView).getParamsMap();

        //successfully without page and size
        delegationControllerSteps
                .getDataForPoolDetailDelegator(param)
                .validateStatusCode(200);

        //successfully without page
        param = new CreateParameters().withPoolView(poolView).withPage(1).getParamsMap();
        delegationControllerSteps
                .getDataForPoolDetailDelegator(param)
                .validateStatusCode(200);

        //successfully page and size
        param = new CreateParameters().withPoolView(poolView).withPage(1).withPageSize(148).getParamsMap();
        delegationControllerSteps
                .getDataForPoolDetailDelegator(param)
                .validateStatusCode(200);
    }
    @Test(description = "verify that get data for pool detail delegators unsuccessfully with invalid poolview", groups={"delegation", "delegation-detail-delegators"}, dataProvider = "paramInvalidPoolView")
    public void getPoolDetailDelegatorUnsuccessfullyWithInvalidPoolView(Object poolView){
        Map<String, Object> param = new CreateParameters().withPoolView(poolView).withPage(2).withPageSize(11).getParamsMap();
        //successfully without page and size
        delegationControllerSteps
                .getDataForPoolDetailDelegator(param)
                .validateStatusCode(400);

    }
    @DataProvider(name ="paramInvalidPoolView")
    public Object[][] dataSetInvalidPage(){
        return new Object[][]{
                {"pool1547tew8vmuj0g6vj3k5jfddudextcw6hsk2hwgg6pkhk7lwph"},
                {null},
                {" "},
                {"&^^^^*"},
                {123},
                {"text"}
        };
    }
}
