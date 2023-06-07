package data;

import com.google.gson.JsonObject;
import microservices.txn.models.TransactionInfo;
import util.JsonUtils;
import util.ObjectMappingUtils;

public class ApiResponseData {
    public static TransactionInfo FIRST_TRANSACTION;
    public static TransactionInfo RANDOM_TRANSACTION;
    public static TransactionInfo TRANSACTION_HAVE_30000000000_ADA;
    public static TransactionInfo TRANSACTION_HAVE_29999998493561943_ADA;
    public static TransactionInfo TRANSACTION_BYRON_ERA;
    public static TransactionInfo TRANSACTION_SHELLY_ERA;
    public static TransactionInfo TRANSACTION_ALLEGRA_ERA;
    public static TransactionInfo TRANSACTION_MARY_ERA;
    public static TransactionInfo TRANSACTION_ALOZO_ERA;
    public static TransactionInfo TRANSACTION_BABBAGE_ERA;


    public ApiResponseData() {
        System.out.println(System.getProperty("cardanoAPI.baseEnv"));
        JsonObject map = null;
        if (System.getProperty("cardanoAPI.baseEnv").contains("mainnet")){
            map = JsonUtils.readJsonFile("mainnet/mainnet_api_data.json");
        }
        else if(System.getProperty("cardanoAPI.baseEnv").contains("preprod")){
            map = JsonUtils.readJsonFile("preProd/pre_prod_api_data.json");
        }

        /*--------------------*/
        /* TRANSACTION API */
        /*--------------------*/

        FIRST_TRANSACTION = (TransactionInfo) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("first_transaction").toString(), TransactionInfo.class);
        RANDOM_TRANSACTION = (TransactionInfo) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("random_transaction").toString(), TransactionInfo.class);
        TRANSACTION_HAVE_30000000000_ADA = (TransactionInfo) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_have_30000000000_ada").toString(), TransactionInfo.class);
        TRANSACTION_HAVE_29999998493561943_ADA = (TransactionInfo) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_have_29999998493561943_ada").toString(), TransactionInfo.class);
        TRANSACTION_BYRON_ERA = (TransactionInfo) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_byron_era").toString(), TransactionInfo.class);
        TRANSACTION_SHELLY_ERA = (TransactionInfo) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_shelly_era").toString(), TransactionInfo.class);
        TRANSACTION_ALLEGRA_ERA = (TransactionInfo) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_allegra_era").toString(), TransactionInfo.class);
        TRANSACTION_MARY_ERA = (TransactionInfo) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_mary_era").toString(), TransactionInfo.class);
        TRANSACTION_ALOZO_ERA = (TransactionInfo) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_alozo_era").toString(), TransactionInfo.class);
        TRANSACTION_BABBAGE_ERA = (TransactionInfo) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_babbage_era").toString(), TransactionInfo.class);

    }
}
