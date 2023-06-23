package tests.contract;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.common.constants.APIErrorCode;
import microservices.common.constants.APIErrorMessage;
import microservices.contract.models.Contract;
import microservices.contract.models.DataContract;
import microservices.contract.models.NativeScriptOfContract;
import microservices.contract.steps.ContractSteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;

@Epic("cardano")
@Feature("api-contract")
@Story("Get native script of contract")
public class GetNativeScriptOfContractTests extends BaseTest {
    private ContractSteps contractSteps = new ContractSteps();
    private NativeScriptOfContract nativeScriptOfContract ;

    @Test(description = "Get native script of contract with non verified address", groups={"contract"})
    public void getNativeScriptWithNonVerifiedAddress(){

        String address ="addr1wy6f9ysw4sjtzpaaxu8yyhwxwe6gqa7pqmgxke0kqdvmkhc2dw88j";

        contractSteps.getNativeScriptOfContracts(address)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .validateResponseBody("Script not verified");

    }

    @Test(description = "Get native script of contract with verified address", groups={"contract"})
    public void getNativeScriptWithVerifiedAddress(){

        String address ="addr1w97qr6xfkm9s3m9zq9ffdgz3dum09aw82eyugyfj6ecu42qxavxwy";
        String pathSchema = "schemaJson/contract/nativeScriptOfContract.json";
        nativeScriptOfContract = (NativeScriptOfContract) contractSteps.getNativeScriptOfContracts(address)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathSchema)
                .saveResponseObject(NativeScriptOfContract.class);

        contractSteps.verifyResponseDataOfNativeNotNull(nativeScriptOfContract);
    }

    @Test(description = "Get native script of contract unsuccessful" ,groups = {"epoch"},dataProvider = "addressUnSuccess")
    public void getNativeScriptUnSuccessFul(String address){
        contractSteps.getNativeScriptOfContracts(address)
                     .then_verifyErrorResponse(400, APIErrorMessage.WALLET_ADDRESS_NOT_FOUND, APIErrorCode.WALLET_ADDRESS_NOT_FOUND);

    }
    @DataProvider(name="addressUnSuccess")
    public Object[][] dataAddressUnSuccess(){
        return new Object[][]{
                {"123"},
                {"abc"},
                {"@#$"},
                {" "},
                {"asset1c6t4elexwkpuzq08ssylhhmc78ahlz0sgw5a7y"},
                {"asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"},

        };
    }
}
