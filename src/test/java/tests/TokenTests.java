package tests;

import base.BaseTest;
import core.BaseApi;
import microservices.token.models.DataToken;
import microservices.token.models.Token;
import microservices.token.steps.TokenSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class TokenTests extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
    private Token token = new Token();
    private ArrayList<DataToken> dataTokens;
    @Test(description = "verify that get list token successfull", groups={"token"}, dataProvider = "paramSuccess")
    public void getListTokenSuccess(int page, int size, ArrayList<String> sort){
        tokenSteps.getListTokens(page, size, sort)
                .verifyResponseGetListToken(200);
        token = tokenSteps.saveResponseListToken();
        dataTokens = token.getData();
        tokenSteps.verifyNumberPage(token.getCurrentPage(), page)
                .verifySizeOfResponse(dataTokens.size(), size);
    }
    @DataProvider(name="paramSuccess")
    public Object[][] dataSetSuccess(){
        ArrayList<String> sortAsc = new ArrayList<>();
        sortAsc.add("supply,ASC");
        sortAsc.add("txCount,ASC");
        ArrayList<String> sortDesc = new ArrayList<>();
        sortDesc.add("supply,DESC");
        sortDesc.add("txCount,DESC");
        return new Object[][]{
                {10,2,sortAsc},
                {10,2, sortDesc},
                {-10,2, sortAsc},
                {10,-10, sortAsc}
        };
    }

    @Test(description = "verify that get list token with page invalid", groups = {"token"}, dataProvider = "paramInvalidPage")
    public void getListTokenWithPageInvalid(String page, int size, ArrayList<String>sort){
        tokenSteps.getListTokensWithPageInvalid(page, size, sort)
                .verifyResponseGetListToken(200);
        token = tokenSteps.saveResponseListToken();
        dataTokens = token.getData();
        tokenSteps.verifyNumberPageInvalid(token.getCurrentPage())
                .verifySizeOfResponse(dataTokens.size(), size);
    }
    @DataProvider(name ="paramInvalidPage")
    public Object[][] dataSetInvalidPage(){
        ArrayList<String> sortDesc = new ArrayList<>();
        sortDesc.add("supply,DESC");
        sortDesc.add("txCount,DESC");
        return new Object[][]{
                {"abc", 5, sortDesc},
                {"  ", 5, sortDesc},
                {"@#$%%", 5, sortDesc},
        };
    }
    @Test(description = "verify that get list token with size invalid", groups = {"token"}, dataProvider = "paramInvalidSize")
    public void getListTokenWithSizeInvalid(int page, String size, ArrayList<String>sort){
        tokenSteps.getListTokensWithSizeInvalid(page, size, sort)
                .verifyResponseGetListToken(200);
        token = tokenSteps.saveResponseListToken();
        dataTokens = token.getData();
        tokenSteps.verifyNumberPage(token.getCurrentPage(), page)
                .verifySizeInvalidOfResponse(dataTokens.size());
    }
    @DataProvider(name = "paramInvalidSize")
    public Object[][] DataSwetInvalidSize(){
        ArrayList<String> sortAsc = new ArrayList<>();
        sortAsc.add("supply,ASC");
        sortAsc.add("txCount,ASC");
        return new Object[][]{
                {5,"  ", sortAsc},
                {5, "y", sortAsc},
                {5, "@#$", sortAsc},
        };
    }
    @Test(description = "verify that get list token with sort invalid", groups = {"token"}, dataProvider = "paramInvalidSort")
    public void getListTokenWithSortInvalid(int page, int size, ArrayList<String>sort){
        TokenSteps tokenSteps = new TokenSteps();
        tokenSteps.getListTokensWithSortInvalid(page, size, sort)
                .verifyResponseGetListToken(200);
    }
    @DataProvider(name = "paramInvalidSort")
    public Object[][] DataSwetInvalidSort(){
        ArrayList<String> sortTxCountDesc = new ArrayList<>();
        sortTxCountDesc.add("txCount,DESC");
        ArrayList<String> sortTxCountAsc = new ArrayList<>();
        sortTxCountAsc.add("txCount,ASC");
        ArrayList<String> sortSupplyAsc = new ArrayList<>();
        sortSupplyAsc.add("supply,ASC");
        ArrayList<String> sortSupplyDesc = new ArrayList<>();
        sortSupplyDesc.add("supply,DESC");
        return new Object[][]{
                {10,2, sortTxCountDesc},
                {10,2, sortTxCountAsc},
                {10,2, sortSupplyAsc},
                {10,2, sortSupplyDesc}
        };
    }
}
