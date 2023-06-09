package data;

import com.google.gson.JsonObject;
import microservices.epoch.models.epoch.EpochData;
import microservices.epoch.models.epochByEpochNo.EpochByEpochNo;
import microservices.epoch.models.epochByEpochNo.EpochDataByEpochNo;
import microservices.policy.models.detail.PolicyDetail;
import microservices.txn.models.TransactionResponse;
import util.JsonUtils;
import util.ObjectMappingUtils;

public class ApiResponseData {
    public static TransactionResponse FIRST_TRANSACTION;
    public static TransactionResponse RANDOM_TRANSACTION;
    public static TransactionResponse TRANSACTION_HAVE_30000000000_ADA;
    public static TransactionResponse TRANSACTION_HAVE_29999998493561943_ADA;
    public static TransactionResponse TRANSACTION_BYRON_ERA;
    public static TransactionResponse TRANSACTION_SHELLY_ERA;
    public static TransactionResponse TRANSACTION_ALLEGRA_ERA;
    public static TransactionResponse TRANSACTION_MARY_ERA;
    public static TransactionResponse TRANSACTION_ALOZO_ERA;
    public static TransactionResponse TRANSACTION_BABBAGE_ERA;

/**
 *    Epoch
 */
    public static EpochData FIRST_EPOCH;
    public static EpochData EPOCH_BYRON_ERA;
    public static EpochData EPOCH_SHELLY_ERA;
    public static EpochData EPOCH_ALLEGRA_ERA;
    public static EpochData EPOCH_MARY_ERA;
    public static EpochData EPOCH_ALOZO_ERA;
    public static EpochData EPOCH_BABBAGE_ERA;

/**
 *    Policies
 */
    public static PolicyDetail POLICY_DETAIL;



    public ApiResponseData() {
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

        FIRST_TRANSACTION = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("first_transaction").toString(), TransactionResponse.class);
        RANDOM_TRANSACTION = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("random_transaction").toString(), TransactionResponse.class);
        TRANSACTION_HAVE_30000000000_ADA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_have_30000000000_ada").toString(), TransactionResponse.class);
        TRANSACTION_HAVE_29999998493561943_ADA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_have_29999998493561943_ada").toString(), TransactionResponse.class);
        TRANSACTION_BYRON_ERA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_byron_era").toString(), TransactionResponse.class);
        TRANSACTION_SHELLY_ERA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_shelly_era").toString(), TransactionResponse.class);
        TRANSACTION_ALLEGRA_ERA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_allegra_era").toString(), TransactionResponse.class);
        TRANSACTION_MARY_ERA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_mary_era").toString(), TransactionResponse.class);
        TRANSACTION_ALOZO_ERA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_alozo_era").toString(), TransactionResponse.class);
        TRANSACTION_BABBAGE_ERA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_babbage_era").toString(), TransactionResponse.class);

        /*--------------------*/
        /* EPOCH API */
        /*--------------------*/

        FIRST_EPOCH = (EpochData) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("first_epoch").toString(), EpochData.class);
        EPOCH_BYRON_ERA = (EpochData) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("epoch_byron_era").toString(), EpochData.class);
        EPOCH_SHELLY_ERA = (EpochData) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("epoch_shelly_era").toString(), EpochData.class);
        EPOCH_ALLEGRA_ERA = (EpochData) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("epoch_allegra_era").toString(), EpochData.class);
        EPOCH_MARY_ERA = (EpochData) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("epoch_mary_era").toString(), EpochData.class);
        EPOCH_ALOZO_ERA = (EpochData) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("epoch_alozo_era").toString(), EpochData.class);
        EPOCH_BABBAGE_ERA = (EpochData) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("epoch_babbage_era").toString(), EpochData.class);

        /*--------------------*/
        /* POLICY API */
        /*--------------------*/

        POLICY_DETAIL = (PolicyDetail) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("policy_detail").toString(), PolicyDetail.class);

    }
}
