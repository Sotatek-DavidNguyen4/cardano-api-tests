package tests;

import base.BaseTest;
import core.BaseApi;
import microservices.steps.token.model.DataToken;
import microservices.steps.token.model.Token;
import microservices.steps.token.step.TokenSteps;
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
            Assert.assertTrue(dataToken.get(i).getTxCount() <= dataToken.get(i-1).getTxCount());
        }
        Assert.assertEquals(token.getCurrentPage(), page);
        Assert.assertEquals(dataToken.size(), size);
    }
    @DataProvider(name="paramSuccess")
    public Object[][] dataSetSuccess(){
        ArrayList<String> sortDesc = new ArrayList<>();
        sortDesc.add("supply,DESC");
        sortDesc.add("txCount,DESC");
        ArrayList<String> sortAsc = new ArrayList<>();
        sortAsc.add("supply,ASC");
        sortAsc.add("txCount,ASC");
        return new Object[][]{
                {20,2,sortDesc},
                {20,2, sortAsc}
        };
    }

    @Test(description = "verify that get list token fail with page invalid", groups = {"token"}, dataProvider = "paramInvalidPage")
    public void getListTokenWithPageInvalid(String page, int size, ArrayList<String>sort){
        TokenSteps tokenSteps = new TokenSteps();
        tokenSteps.getListTokensWithPageInvalid(page, size, sort);
        Token token = (Token) tokenSteps.saveResponseObject(Token.class);
        ArrayList<DataToken> dataTokens = token.getData();
        baseApi.validateResponse(200);
        Assert.assertEquals(token.getCurrentPage(), 0);
        Assert.assertEquals(dataTokens.size(), size);
        for(int i =1; i <dataTokens.size(); i++){
            if(sort.get(0).contains("ASC")){
                Assert.assertTrue(dataTokens.get(i).getTxCount() >= dataTokens.get(i-1).getTxCount());
            }else{
                Assert.assertTrue(dataTokens.get(i).getTxCount() <= dataTokens.get(i-1).getTxCount());
            }
        }
    }
    @DataProvider(name ="paramInvalidPage")
    public Object[][] dataSetInvalidPage(){
        ArrayList<String> sortDesc = new ArrayList<>();
        sortDesc.add("supply,DESC");
        sortDesc.add("txCount,DESC");
        ArrayList<String> sortAsc = new ArrayList<>();
        sortAsc.add("supply,ASC");
        sortAsc.add("txCount,ASC");
        return new Object[][]{
                {"  ", 5, sortDesc},
                {"", 5, sortDesc},
                {null, 5, sortDesc},
                {"abc", 5, sortDesc},
                {"@#$", 5, sortAsc},

                {"  ", 5, sortAsc},
                {"", 5, sortAsc},
                {null, 5, sortAsc},
                {"abc", 5, sortAsc},
                {"@#$", 5, sortAsc}
        };
    }
    @Test(description = "verify that get list token fail with size invalid", groups = {"token"}, dataProvider = "paramInvalidSize")
    public void getListTokenWithSizeInvalid(int page, String size, ArrayList<String>sort){
        TokenSteps tokenSteps = new TokenSteps();
        tokenSteps.getListTokensWithSizeInvalid(page, size, sort);
        Token token = (Token) tokenSteps.saveResponseObject(Token.class);
        ArrayList<DataToken> dataTokens = token.getData();
        baseApi.validateResponse(200);
        Assert.assertEquals(token.getCurrentPage(), page);
        Assert.assertEquals(dataTokens.size(), 20);
        for(int i =1; i <dataTokens.size(); i++){
            if(sort.get(0).contains("ASC")){
                Assert.assertTrue(dataTokens.get(i).getTxCount() >= dataTokens.get(i-1).getTxCount());
            }else{
                Assert.assertTrue(dataTokens.get(i).getTxCount() <= dataTokens.get(i-1).getTxCount());
            }
        }
    }
    @DataProvider(name = "paramInvalidSize")
    public Object[][] DataSwetInvalidSize(){
        ArrayList<String> sortDesc = new ArrayList<>();
        sortDesc.add("supply,DESC");
        sortDesc.add("txCount,DESC");
        ArrayList<String> sortAsc = new ArrayList<>();
        sortAsc.add("supply,ASC");
        sortAsc.add("txCount,ASC");
        return new Object[][]{
                {5,"  ", sortDesc},
                {5, "", sortDesc},
                {5, null, sortDesc},
                {5, "abc", sortDesc},
                {5, "@#$", sortDesc},

                {5,"  ", sortAsc},
                {5, "", sortAsc},
                {5, null, sortAsc},
                {5, "abc", sortAsc},
                {5, "@#$", sortAsc},
        };
    }
    @Test(description = "verify that get list token fail with sort invalid", groups = {"token"}, dataProvider = "paramInvalidSort")
    public void getListTokenWithSortInvalid(int page, int size, ArrayList<String>sort){
        TokenSteps tokenSteps = new TokenSteps();
        tokenSteps.getListTokens(page, size, sort);
        Token token = (Token) tokenSteps.saveResponseObject(Token.class);
        ArrayList<DataToken> dataTokens = token.getData();
        baseApi.validateResponse(200);
        Assert.assertEquals(token.getCurrentPage(), page);
        Assert.assertEquals(dataTokens.size(), 20);
        for(int i =1; i <dataTokens.size(); i++){
            if(sort.get(0).contains("ASC")){
                Assert.assertTrue(dataTokens.get(i).getTxCount() >= dataTokens.get(i-1).getTxCount());
            }else{
                Assert.assertTrue(dataTokens.get(i).getTxCount() <= dataTokens.get(i-1).getTxCount());
            }
        }
    }
    @DataProvider(name = "paramInvalidSort")
    public Object[][] DataSwetInvalidSort(){
        ArrayList<String> sortNull = null;
        ArrayList<String> sortBlank = new ArrayList<>();
        sortBlank.add("");
        ArrayList<String> sortSpace = new ArrayList<>();
        sortSpace.add("   ");
        ArrayList<String> sortAbc = new ArrayList<>();
        sortAbc.add("   ");
        ArrayList<String> sortSepecialCharacter = new ArrayList<>();
        sortAbc.add("@#$");
        return new Object[][]{
                {20,5, sortNull},
                {20,5, sortBlank},
                {20,5, sortSpace},
                {20,5, sortAbc},
                {20,5, sortSepecialCharacter},
        };
    }
}
