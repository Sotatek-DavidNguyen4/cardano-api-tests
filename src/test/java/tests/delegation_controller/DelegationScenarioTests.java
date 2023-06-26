package tests.delegation_controller;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.addresses.steps.AddressAnalyticsSteps;
import microservices.delegation.models.PoolDetailAnalyticsChart;
import microservices.delegation.models.PoolDetailHeaderModel;
import microservices.delegation.models.PoolDetailInListModel;
import microservices.delegation.steps.DelegationControllerSteps;
import microservices.delegation.steps.DelegationPoolDetailAnalyticSteps;
import microservices.delegation.steps.DelegationTopSteps;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import static util.RandomNumber.randomInteger;

@Epic("cardano")
@Feature("api-delegation")
public class DelegationScenarioTests extends BaseTest {

    DelegationTopSteps delegationSteps = new DelegationTopSteps();
    DelegationControllerSteps delegationControllerSteps = new DelegationControllerSteps();
    DelegationPoolDetailAnalyticSteps delegationPoolDetailAnalyticSteps = new DelegationPoolDetailAnalyticSteps();
    @Test(description = "compare top delegation with data for pool detail", groups = {"delegation"})
    public void compareTopDelegaWithDataPoolDetail(){
        int randomNumber;
        String poolView;
        Map<String, Object> param= new CreateParameters()
                .getParamsMap();
        List<PoolDetailInListModel> poolDetailInListModel = (List<PoolDetailInListModel>) delegationSteps
                .getDataForDelegationHeader(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseListObject(PoolDetailInListModel[].class);
        randomNumber = randomInteger(poolDetailInListModel.size());
        poolView = poolDetailInListModel.get(randomNumber).getPoolId();
        param = new CreateParameters().withPoolView(poolView).getParamsMap();

        PoolDetailHeaderModel actualPoolDetailHeader = (PoolDetailHeaderModel) delegationControllerSteps
                .getDataForPoolDetail(poolView)
                .saveResponseObject(PoolDetailHeaderModel.class);
        delegationControllerSteps
                .verifyDelegationMatchWithDataInPoolDetail(poolDetailInListModel.get(randomNumber), actualPoolDetailHeader);

    }
}
