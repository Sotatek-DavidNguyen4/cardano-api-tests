package microservices.pool.steps;

import core.BaseApi;
import io.qameta.allure.Step;
import microservices.common.constants.RequestParams;
import microservices.pool.models.PoolResponse;

import static constants.DateFormats.DATE_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertTrue;

import constants.Endpoints;
import util.AttributeStandard;
import util.SortListUtil;

import java.util.ArrayList;
import java.util.Collection;
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

    @Step("Verify pool list response")
    public PoolSteps then_verifyPoolListResponse(PoolResponse poolResponse, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params, 0, 10);
        assertThat(poolResponse.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(poolResponse.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());
        if (requestParams.getSort()!=null) {
            boolean sorted = SortListUtil.isSortedByField(new ArrayList<>(poolResponse.getData()), requestParams.getSort());
            assertThat(sorted).as("Pool registration list is not sorted by inputted params").isEqualTo(true);
        }
        assertTrue(AttributeStandard.areValidDates(poolResponse.getData().stream().map(s -> s.getTxTime()).collect(Collectors.toList()), DATE_FORMAT[0]));
        assertTrue(AttributeStandard.areValidHashes(poolResponse.getData().stream().map(s -> s.getTxHash()).collect(Collectors.toList())));
        assertTrue(AttributeStandard.areValidPoolId(poolResponse.getData().stream().map(s -> s.getPoolView()).collect(Collectors.toList())));
        assertTrue(AttributeStandard.areValidStakeAddress(poolResponse.getData().stream().map(s -> s.getStakeKey()).flatMap(Collection::stream).collect(Collectors.toList())));
        return this;
    }

}
