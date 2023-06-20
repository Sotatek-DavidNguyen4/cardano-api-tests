package tests.addresses;

import base.BaseTest;
import com.google.common.collect.Comparators;
import com.google.common.collect.Ordering;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.addresses.models.AddressModel;
import microservices.addresses.models.TopAddressModel;
import microservices.addresses.steps.AddressSteps;
import microservices.addresses.steps.TopAddressSteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Epic("cardano")
@Feature("api-addresses")
public class TopAddressTests extends BaseTest {
    TopAddressSteps topAddressSteps = new TopAddressSteps();

    @Test(description = "verify get top-address unsuccessfully", groups={"addresses", "top-address"}, dataProvider = "paramInvalidData")
    public void verifyGetAddressResponseUnsuccessfully(String page,String size){
        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);

        TopAddressModel topAddressModel = (TopAddressModel) topAddressSteps
                .getDataForTopAddress(params)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TopAddressModel.class);

        topAddressSteps.verifyDataValueIsEmpty(topAddressModel.getData())
                       .then_verifyTopDelegatorsResponse(topAddressModel,params);
    }
    @DataProvider(name ="paramInvalidData")
    public Object[][] dataSetInvalidData(){
        return new Object[][]{
                {"",""},
                {"","3"},
                {"","a"},
                {"","-2"},
                {""," "},
                {"","jnfj#$%"},
        };
    }
}
