package tests.delegation_controller;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.delegation.models.PoolDetailDelegatorModel;
import microservices.delegation.models.PoolDetailHeaderModel;
import microservices.delegation.steps.DelegationControllerSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import sun.net.www.protocol.http.HttpURLConnection;
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
                .validateStatusCode(HttpURLConnection.HTTP_OK)
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
                .validateStatusCode(HttpURLConnection.HTTP_OK);

        //successfully without page
        param = new CreateParameters().withPoolView(poolView).withPage(1).getParamsMap();
        delegationControllerSteps
                .getDataForPoolDetailDelegator(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK);

        //successfully page and size
        param = new CreateParameters().withPoolView(poolView).withPage(1).withPageSize(148).getParamsMap();
        delegationControllerSteps
                .getDataForPoolDetailDelegator(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @Test(description = "verify that get data for pool detail delegators unsuccessfully with invalid poolview", groups={"delegation", "delegation-detail-delegators"}, dataProvider = "paramInvalidPoolView")
    public void getPoolDetailDelegatorUnsuccessfullyWithInvalidPoolView(Object poolView){
        Map<String, Object> param = new CreateParameters().withPoolView(poolView).withPage(2).withPageSize(11).getParamsMap();
        //successfully without page and size
        delegationControllerSteps
                .getDataForPoolDetailDelegator(param)
                .validateStatusCode(HttpURLConnection.HTTP_BAD_REQUEST);

    }
    @DataProvider(name ="paramInvalidPoolView")
    public Object[][] dataSetInvalidPoolView(){
        return new Object[][]{
                {"pool1547tew8vmuj0g6vj3k5jfddudextcw6hsk2hwgg6pkhk7lwph"},
                {null},
                {" "},
                {"&^^^^*"},
                {123},
                {"text"}
        };
    }
    @Test(description = "verify that get data for pool detail delegators unsuccessfully with invalid page", groups={"delegation", "delegation-detail-delegators"}, dataProvider = "paramInvalidPage")
    public void getPoolDetailDelegatorUnsuccessfullyWithInvalidPage(Object page){
        Map<String, Object> param = new CreateParameters().withPoolView(poolView).withPage(page).withPageSize(1).getParamsMap();

        //successfully
        PoolDetailDelegatorModel poolDetailDelegatorModel = (PoolDetailDelegatorModel) delegationControllerSteps
                .getDataForPoolDetailDelegator(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolDetailDelegatorModel.class);
        //verify data null
        delegationControllerSteps
                .verifyDataNull(poolDetailDelegatorModel);
    }
    @DataProvider(name ="paramInvalidPage")
    public Object[][] dataSetInvalidPage(){
        return new Object[][]{
                {" "},
                {null},
                {"text"},
                {-1},
                {0.999},
                {"%*^^^"}
        };
    }
    @Test(description = "verify that get data for pool detail delegators unsuccessfully with invalid size", groups={"delegation", "delegation-detail-delegators"}, dataProvider = "paramInvalidSize")
    public void getPoolDetailDelegatorUnsuccessfullyWithInvalidSize(Object size){
        Map<String, Object> param = new CreateParameters().withPoolView(poolView).withPage(2).withPageSize(size).getParamsMap();

        //successfully
        PoolDetailDelegatorModel poolDetailDelegatorModel = (PoolDetailDelegatorModel) delegationControllerSteps
                .getDataForPoolDetailDelegator(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolDetailDelegatorModel.class);
        //verify data null
        delegationControllerSteps
                .verifyDataNull(poolDetailDelegatorModel);
    }
    @DataProvider(name ="paramInvalidSize")
    public Object[][] dataSetInvalidSize(){
        return new Object[][]{
                {" "},
                {2.999},
                {-11},
                {"tesst"},
                {"TESST"},
                {"12abvvvff"}
        };
    }
}
