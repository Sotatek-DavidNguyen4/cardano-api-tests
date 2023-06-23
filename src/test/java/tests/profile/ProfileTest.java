package tests.profile;

import base.BaseTest;
import constants.Url;
import core.BaseApi;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.txn.models.FilterTransactionResponse;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

@Epic("cardano")
@Feature("api-authentication")
@Story("Profile test")
public class ProfileTest extends BaseTest {
//    private
//    @BeforeMethod(alwaysRun = true)
//    public void setUp() throws Exception {
//        BaseApi.initReqSpec();
//        BaseApi.setBaseUrl(Url.API);
//        baseSteps.when_authLogin(INVOICING_BILLING_PLAN_CREATION_USER.emailAddress, INVOICING_BILLING_PLAN_CREATION_USER.emailPassword, INVOICING_BILLING_PLAN_CREATION_USER.namespace);
//    }
//
//    @Test(description = "Get user info", groups = "profile")
//    public void get_user_info() {
//        MultiMap params = new MultiValueMap();
//        params.put("network", page);
//        FilterTransactionResponse filterTxsRes = (FilterTransactionResponse) txnSteps.when_userInfo(params)
//                .validateResponse(HttpURLConnection.HTTP_OK)
//                .saveResponseObject(FilterTransactionResponse.class);
//        txnSteps.then_verifyFilterTransactionResponse(filterTxsRes, params);
//    }

}
