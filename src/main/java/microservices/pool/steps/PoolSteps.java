package microservices.pool.steps;

import core.BaseApi;
import io.qameta.allure.Step;
import microservices.pool.models.PoolResponse;
import static org.assertj.core.api.Assertions.assertThat;
import org.testng.Assert;
import constants.Endpoints;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;


public class PoolSteps extends BaseApi {
    @Step("Get registration pool list")
    public PoolSteps when_getRegistrationPoolList(Map<String, Object> params){
        sendGet(Endpoints.PoolControllerApi.REGISTRATION_POOLS, params);
        return this;
    }

    @Step("Get de-registration pool list")
    public PoolSteps when_getDeRegistrationPoolList(Map<String, Object> params){
        sendGet(Endpoints.PoolControllerApi.DEREGISTRATION_POOLS, params);
        return this;
    }

    @Step("Verify registration pool list response")
    public PoolSteps verify_registrationPoolList(PoolResponse poolResponse, Map<String, Object> params){
        assertThat(poolResponse.getTotalItems())
                .as("Value of field 'totalItems' is wrong")
                .isEqualTo(params.get("totalItems"));
        if (params.get("sort").equals("desc")) {
            Assert.assertEquals(poolResponse.getData(),poolResponse.getData().stream().sorted(Collections.reverseOrder()).collect(Collectors.toList()));
        }
        if (params.get("sort").equals("asc")){
            Assert.assertEquals(poolResponse.getData(),poolResponse.getData().stream().sorted().collect(Collectors.toList()));
        }
        return this;
    }

}
