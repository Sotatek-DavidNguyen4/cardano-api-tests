package data;

import com.google.gson.JsonObject;
import microservices.txn.models.TransactionResponse;
import util.JsonUtils;
import util.ObjectMappingUtils;

public class ApiUser {
    public static DataUser USER_SIGN_IN;

    public ApiUser() {
        JsonObject map = JsonUtils.readJsonFile("preProd/pre_prod_api_user_data.json");

        USER_SIGN_IN = (DataUser) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("user_sign_in").toString(), DataUser.class);
    }
}
