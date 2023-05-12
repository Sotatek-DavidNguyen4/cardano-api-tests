package tests;

import base.BaseTest;
import core.BaseApi;
import microservices.token.models.DataToken;
import microservices.token.models.Token;
import microservices.token.steps.TokenSteps;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class TokenTests extends BaseTest {
    private Token token = new Token();
    BaseApi baseApi = new BaseApi();
    @Test(description = "verify that get list token successfull", groups={"token"}, dataProvider = "paramSuccess")
    public void getListTokenSuccess(int page, int size, ArrayList<String> sort){
        TokenSteps tokenSteps = new TokenSteps();
        tokenSteps.getListTokens(page, size, sort);
        token = (Token) tokenSteps.saveResponseObject(Token.class);
        ArrayList<DataToken> dataToken = token.getData();
        baseApi.validateResponse(200);
        for(int i =1; i <dataToken.size(); i++){
            if(sort.get(0).contains("ASC")){
                Assert.assertTrue(dataToken.get(i).getTxCount() >= dataToken.get(i-1).getTxCount());
            }else{
                Assert.assertTrue(dataToken.get(i).getTxCount() <= dataToken.get(i-1).getTxCount());
            }
        }
        if(page > 0){
            Assert.assertEquals(token.getCurrentPage(), page);
        }else{
            Assert.assertEquals(token.getCurrentPage(), 0);
        }
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
        TokenSteps tokenSteps = new TokenSteps();
        tokenSteps.getListTokensWithPageInvalid(page, size, sort);
        Token token = (Token) tokenSteps.saveResponseObject(Token.class);
        ArrayList<DataToken> dataTokens = token.getData();
        baseApi.validateResponse(200);
        Assert.assertEquals(token.getCurrentPage(), 0);
        Assert.assertEquals(dataTokens.size(), size);
        for(int i =1; i <dataTokens.size(); i++){
            Assert.assertTrue(dataTokens.get(i).getTxCount() <= dataTokens.get(i-1).getTxCount());
        }
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
        TokenSteps tokenSteps = new TokenSteps();
        tokenSteps.getListTokensWithSizeInvalid(page, size, sort);
        Token token = (Token) tokenSteps.saveResponseObject(Token.class);
        ArrayList<DataToken> dataTokens = token.getData();
        baseApi.validateResponse(200);
        Assert.assertEquals(token.getCurrentPage(), page);
        Assert.assertEquals(dataTokens.size(), 20);
        for(int i =1; i <dataTokens.size(); i++){
            Assert.assertTrue(dataTokens.get(i).getTxCount() >= dataTokens.get(i-1).getTxCount());
        }
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
        tokenSteps.getListTokensWithSortNull(page, size, sort);
        baseApi.validateResponse(200);
    }
    @DataProvider(name = "paramInvalidSort")
    public Object[][] DataSwetInvalidSort(){
        ArrayList<String> sortTxCountDesc = new ArrayList<>();
        sortTxCountDesc.add("txCount,DESC");
        ArrayList<String> sortTxCountAsc = new ArrayList<>();
        sortTxCountAsc.add("txCount,ASC");
        ArrayList<String> sortSupplyAsc = new ArrayList<>();
        sortSupplyAsc.add("txCount,ASC");
        ArrayList<String> sortSupplyDesc = new ArrayList<>();
        sortSupplyDesc.add("txCount,DESC");
        return new Object[][]{
                {10,2, sortTxCountDesc},
                {10,2, sortTxCountAsc},
                {10,2, sortSupplyAsc},
                {10,2, sortSupplyDesc}
        };
    }
}
