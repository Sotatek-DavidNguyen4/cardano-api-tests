package constants;

public class Endpoints {
    public static class DelegationApi {

        public static final String POOL_DETAIL_URI = "delegations/pool-detail-header/{poolView}";
        public static final String POOL_DETAIL_DELEGATORS_URI = "delegations/pool-detail-delegators";

    }

    public static class PoolControllerApi {
        public static final String REGISTRATION_POOLS = "pools/registration";
        public static final String DEREGISTRATION_POOLS = "pools/de-registration";
    }
    public static class TokenApi {
        public static final String TOKEN_ID = "tokenId";
        public static final String GET_LIST_TOKEN = "/tokens";
        public static final String GET_A_TOKEN = "/tokens/{"+ TOKEN_ID +"}";
        public static final String GET_TXS = "/tokens/{"+ TOKEN_ID +"}/txs";
    }
    public static class TransactionApi {
        public static final String HASH_ID = "hash";
        public static final String TRANSACTION_HASH = "txs/{" + HASH_ID + "}";
    }

}
