package tests.delegation_controller;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.delegation.constants.DelegationConstant;
import microservices.delegation.models.PoolDetailDelegatorModel;
import microservices.delegation.models.PoolDetailHeaderModel;
import microservices.delegation.steps.DelegationControllerSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.net.HttpURLConnection;
import util.CreateParameters;

import java.util.Map;

@Epic("cardano")
@Feature("api-delegation")
public class DelegationControllerTests extends BaseTest {
    DelegationControllerSteps delegationControllerSteps = new DelegationControllerSteps();
    Object poolView = "pool1pu5jlj4q9w9jlxeu370a3c9myx47md5j5m2str0naunn2q3lkdy";
    @Test(description = "verify that get data for pool detail", groups={"delegation", "delegation-detail"})
    public void getPoolDetailHeader(){
        String schemaJson = "schemaJson/delegations/delegation-detail-header.json";
        //successfully
        PoolDetailHeaderModel actualPoolDetailHeader = (PoolDetailHeaderModel) delegationControllerSteps
                .getDataForPoolDetail(poolView)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(schemaJson)
                .saveResponseObject(PoolDetailHeaderModel.class);

        delegationControllerSteps
                .verifyAllAttributeExistsOrNot(actualPoolDetailHeader)
                .verifyFormatAttributes(actualPoolDetailHeader);
                //.verifyHashViewFormat(actualPoolDetailHeader);

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
        String schemaJson = "schemaJson/delegations/pool-detail-delegators.json";
        Object page, size;
        //with page and size
        page = null;
        size = null;
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withPoolView(poolView)
                .withSort("")
                .getParamsMap();

        PoolDetailDelegatorModel poolDetailDelegatorModel = (PoolDetailDelegatorModel) delegationControllerSteps
                .getDataForPoolDetailDelegator(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(schemaJson)
                .saveResponseObject(PoolDetailDelegatorModel.class);
        delegationControllerSteps
                .verifyDataAmountIsCorrect(10, poolDetailDelegatorModel.getData().size())
                .verifyFormatAttributes(poolDetailDelegatorModel);
        // page size poolview is null
        page = null;
        size = null;
        poolView = "null";
        param = new CreateParameters().withPoolView(poolView).withPage(page).getParamsMap();
        poolDetailDelegatorModel = (PoolDetailDelegatorModel) delegationControllerSteps
                .getDataForPoolDetailDelegator(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolDetailDelegatorModel.class);


    }
    //This test case still have bug need to be fixed
    @Test(description = "verify that get data for pool detail delegators unsuccessfully with invalid poolview", groups={"delegation", "delegation-detail-delegators"}, dataProvider = "paramInvalidPoolView")
    public void getPoolDetailDelegatorUnsuccessfullyWithInvalidPoolView(Object poolView){
        Map<String, Object> param = new CreateParameters().withPoolView(poolView).getParamsMap();
        //successfully without page and size
        delegationControllerSteps
                .getDataForPoolDetailDelegator(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK);

    }
    @DataProvider(name ="paramInvalidPoolView")
    public Object[][] dataSetInvalidPoolView(){
        return new Object[][]{
                {"@#$"},
                {123}
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
