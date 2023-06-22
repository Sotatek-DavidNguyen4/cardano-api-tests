package tests.tokens;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.token.models.TokenModel;
import microservices.token.models.TokensModel;
import microservices.token.models.TokensTxsModel;
import microservices.token.steps.TokenSteps;
import org.testng.annotations.Test;
import util.CreateParameters;
import util.RandomNumber;

import java.net.HttpURLConnection;
import java.util.Map;
@Epic("cardano")
@Feature("api-token-controller")
@Story("Tokens Scenario")
public class TokenScenario extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
    private RandomNumber randomNumber = new RandomNumber();
    private TokenModel data = new TokenModel();
    @Test(description = "compare get list token and get token txs", groups={"token", "token_scenario"}, priority = 0)
    public void compareGetListTokenAndGetTokenTxs(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        TokensModel tokensModel = (TokensModel)
                tokenSteps.getListTokens(param)
                        .validateResponse(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensModel.class);

        int random = randomNumber.randomInteger(tokensModel.getData().size());
        data = tokensModel.getData().get(random);

        TokensTxsModel tokensTxsModel = (TokensTxsModel)
        tokenSteps.getTokenTxs(data.getFingerprint(), param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokensTxsModel.class);

        tokenSteps.compareGetListTokenAndGetTokenTxs(data, tokensTxsModel);
    }
    @Test(description = "compare get list token and get detail token", groups={"token", "token_scenario"}, priority = 1)
    public void compareGetListTokenAndGetDetailToken(){
        TokenModel tokenModel = (TokenModel)
        tokenSteps.getAToken(data.getFingerprint())
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokenModel.class);
        tokenSteps.compareGetListTokenAndGetDetailToken(data, tokenModel);
    }
}
