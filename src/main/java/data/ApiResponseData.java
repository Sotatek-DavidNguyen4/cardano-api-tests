package data;

import com.google.gson.JsonObject;
import microservices.epoch.models.epoch.EpochData;
import microservices.epoch.models.epochByEpochNo.EpochByEpochNo;
import microservices.epoch.models.epochByEpochNo.EpochDataByEpochNo;
import microservices.policy.models.detail.PolicyDetail;
import microservices.block.models.BlockDetailModel;
import microservices.txn.models.TransactionResponse;
import util.JsonUtils;
import util.ObjectMappingUtils;

public class ApiResponseData {
/**
 *    Transaction
 */
    public static TransactionResponse TRANSACTION_BYRON_ERA;
    public static TransactionResponse TRANSACTION_SHELLY_ERA;
    public static TransactionResponse TRANSACTION_ALLEGRA_ERA;
    public static TransactionResponse TRANSACTION_MARY_ERA;
    public static TransactionResponse TRANSACTION_ALOZO_ERA;
    public static TransactionResponse TRANSACTION_BABBAGE_ERA;
    public static TransactionResponse TRANSACTION_PROTOCOLS;
    public static TransactionResponse TRANSACTION_STAKE_CERTIFICATES;

/**
 *    Epoch
 */
    public static EpochData EPOCH_BY_NO;
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

    public static BlockDetailModel FIRST_BLOCK;
    public static BlockDetailModel BLOCK_MANY_TXNS;
    public static BlockDetailModel BLOCK_A_TXN;
    public static BlockDetailModel BLOCK_BYRON_ERA;
    public static BlockDetailModel BLOCK_SHELLY_ERA;
    public static BlockDetailModel BLOCK_ALLEGRA_ERA;
    public static BlockDetailModel BLOCK_MARY_ERA;
    public static BlockDetailModel BLOCK_ALOZO_ERA;
    public static BlockDetailModel BLOCK_BABBAGE_ERA;

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

        TRANSACTION_BYRON_ERA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_byron_era").toString(), TransactionResponse.class);
        TRANSACTION_SHELLY_ERA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_shelly_era").toString(), TransactionResponse.class);
        TRANSACTION_ALLEGRA_ERA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_allegra_era").toString(), TransactionResponse.class);
        TRANSACTION_MARY_ERA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_mary_era").toString(), TransactionResponse.class);
        TRANSACTION_ALOZO_ERA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_alozo_era").toString(), TransactionResponse.class);
        TRANSACTION_BABBAGE_ERA = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_babbage_era").toString(), TransactionResponse.class);
        TRANSACTION_PROTOCOLS = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_protocols").toString(), TransactionResponse.class);
        TRANSACTION_STAKE_CERTIFICATES = (TransactionResponse) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("transaction_stake_certificates").toString(), TransactionResponse.class);

        /*--------------------*/
        /* EPOCH API */
        /*--------------------*/

        EPOCH_BY_NO = (EpochData) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("epoch_by_no").toString(), EpochData.class);
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

        /* BLOCK API */
        /*--------------------*/
        FIRST_BLOCK = (BlockDetailModel) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("first_block").toString(), BlockDetailModel.class);
        BLOCK_MANY_TXNS = (BlockDetailModel) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("block_many_txns").toString(), BlockDetailModel.class);
        BLOCK_A_TXN = (BlockDetailModel) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("block_a_txn").toString(), BlockDetailModel.class);
        BLOCK_BYRON_ERA = (BlockDetailModel) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("block_byron_era").toString(), BlockDetailModel.class);
        BLOCK_SHELLY_ERA = (BlockDetailModel) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("block_shelly_era").toString(), BlockDetailModel.class);
        BLOCK_ALLEGRA_ERA = (BlockDetailModel) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("block_allegra_era").toString(), BlockDetailModel.class);
        BLOCK_MARY_ERA = (BlockDetailModel) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("block_mary_era").toString(), BlockDetailModel.class);
        BLOCK_ALOZO_ERA = (BlockDetailModel) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("block_alozo_era").toString(), BlockDetailModel.class);
        BLOCK_BABBAGE_ERA = (BlockDetailModel) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("block_babbage_era").toString(), BlockDetailModel.class);


    }
}
