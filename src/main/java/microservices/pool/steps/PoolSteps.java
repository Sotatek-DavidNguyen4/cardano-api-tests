package microservices.pool.steps;

import constants.Endpoint;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.pool.models.PoolResponse;
import static org.assertj.core.api.Assertions.assertThat;
import org.testng.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class PoolSteps extends BaseApi {
    @Step
    public PoolSteps when_getRegistrationPoolList(Map<String, Object> params){
        sendGet(Endpoint.REGISTRATION_POOLS, params);
        return this;
    }

    @Step
    public PoolSteps when_getDeRegistrationPoolList(Map<String, Object> params){
        sendGet(Endpoint.DEREGISTRATION_POOLS, params);
        return this;
    }

    @Step
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
