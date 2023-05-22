package tests.addresses;

import base.BaseTest;
import com.google.common.collect.Comparators;
import com.google.common.collect.Ordering;
import microservices.addresses.models.AddressModel;
import microservices.addresses.models.TopAddressModel;
import microservices.addresses.steps.AddressSteps;
import microservices.addresses.steps.TopAddressSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopAddressTests extends BaseTest {
    TopAddressSteps topAddressSteps = new TopAddressSteps();
    @Test(description = "verify get top-address successfully", groups={"addresses", "top-address"})
    public void verifyGetAddressResponseSuccessfully(){

        int page = 2;
        int pageSize = 3;
        String sort= "balance";
        //with page = 2
        Map<String, Object> param = new CreateParameters().withPage(page).getParamsMap();
        TopAddressModel topAddressModel = (TopAddressModel) topAddressSteps
                .getDataForTopAddress(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TopAddressModel.class);

        topAddressSteps
                .verifyThatDataResponseIsOnCorrectPage(page, topAddressModel.getCurrentPage());

        //with size = 3
        param = new CreateParameters().withPageSize(pageSize).getParamsMap();
        topAddressModel = (TopAddressModel) topAddressSteps
                .getDataForTopAddress(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TopAddressModel.class);

        topAddressSteps.verifyDataAmountIsCorrect(pageSize, topAddressModel.getTotalItems(), topAddressModel.getData().size());

        //with sort = "balance"
        param = new CreateParameters().withSort(sort).getParamsMap();
        topAddressModel = (TopAddressModel) topAddressSteps
                .getDataForTopAddress(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TopAddressModel.class);

        topAddressSteps.verifyAttributeIsSortedCorrectly(topAddressModel);

        //with page = 2 and size = 3 sort = id
        param = new CreateParameters().withPage(page).withPageSize(pageSize).withSort(sort).getParamsMap();
        topAddressSteps
                .getDataForTopAddress(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK);

        topAddressSteps.verifyAttributeIsSortedCorrectly(topAddressModel)
                        .verifyDataAmountIsCorrect(pageSize, topAddressModel.getTotalItems(), topAddressModel.getData().size());

        //with page = blank and size = 3 sort = balance
        param = new CreateParameters().withPageSize(pageSize).withSort(sort).getParamsMap();
        topAddressSteps
                .getDataForTopAddress(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK);

        topAddressSteps.verifyAttributeIsSortedCorrectly(topAddressModel);
    }    @Test(description = "verify get top-address unsuccessfully", groups={"addresses", "top-address"}, dataProvider = "paramInvalidData")
    public void verifyGetAddressResponseUnsuccessfully(Object pageSize){
        int page = 12222222;

        Map<String, Object> param = new CreateParameters().withPage(page).withPageSize(pageSize).getParamsMap();
        TopAddressModel topAddressModel = (TopAddressModel) topAddressSteps
                .getDataForTopAddress(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TopAddressModel.class);

        topAddressSteps.verifyDataValueIsEmpty(topAddressModel.getData());
    }
    @DataProvider(name ="paramInvalidData")
    public Object[][] dataSetInvalidData(){
        return new Object[][]{
                {null},
                {2},
        };
    }
}
