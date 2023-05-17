package constants;

public class Endpoints {
    public static class DelegationApi {

        public static final String POOL_DETAIL_URI = "delegations/pool-detail-header/{poolView}";
        public static final String POOL_DETAIL_DELEGATORS_URI = "delegations/pool-detail-delegators";
        public static final String POOL_DETAIL_ANALYTICS_URI = "delegations/pool-detail-analytics";
        public static final String POOL_HEADER_URI = "delegations/header";

    }

    public static class PoolControllerApi {
        public static final String REGISTRATION_POOLS = "pools/registration";
        public static final String DEREGISTRATION_POOLS = "pools/de-registration";
    }
    public static class TokenApi {
        public static final String GET_LIST_TOKEN = "/tokens";
        public static final String GET_A_TOKEN = "/tokens/{tokenId}";
        public static final String GET_TXS = "/tokens/{tokenId}/txs";
    }
    public static class TransactionApi {
        public static final String HASH_ID = "hash";
        public static final String TRANSACTION_HASH = "txs/{" + HASH_ID + "}";
    }

    public static class ContractApi {
        public static final String GET_LIST_CONTRACT = "/contracts";

    }

    public static class EpochApi{
        public static final String GET_CURRENT_EPOCH ="/epochs/current";
        public static final String GET_LIST_EPOCH_NO ="/epochs/{no}/blocks";
    }

}
