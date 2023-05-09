package microservices.steps.token.step;

import constants.EndPoints;
import core.BaseApi;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TokenSteps extends BaseApi {
    @Step
    public String getListTokens(int page, int size, ArrayList<String> sort){
        Map<String, Object> paramsToken = new HashMap<>();
        paramsToken.put("page", page);
        paramsToken.put("size", size);
        paramsToken.put("sort", sort);
        sendGet(EndPoints.GET_LIST_TOKEN, paramsToken);
        return getJsonAsString();
    }
}
