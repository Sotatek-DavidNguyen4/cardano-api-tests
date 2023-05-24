package constants;

public class Endpoints {
    public static class DelegationApi {

        public static final String POOL_DETAIL_URI = "delegations/pool-detail-header/{poolView}";
        public static final String POOL_DETAIL_DELEGATORS_URI = "delegations/pool-detail-delegators";
        public static final String POOL_DETAIL_ANALYTICS_URI = "delegations/pool-detail-analytics";
        public static final String POOL_HEADER_URI = "delegations/header";

    }
    public static class AddressesApi {

        public static final String ADDRESS_URI = "addresses/{address}";
        public static final String TOP_ADDRESS_URI = "addresses/top-addresses";
        public static final String MIN_MAX_BALANCE_URI = "addresses/{address}";
        public static final String ADDRESS_ANALYTICS_URI = "addresses/analytics/{address}/{type}";
        public static final String ADDRESS_TRANSACTION_URI = "addresses/{address}/txs";

    }

    public static class PoolControllerApi {
        public static final String REGISTRATION_POOLS = "pools/registration";
        public static final String DEREGISTRATION_POOLS = "pools/de-registration";
    }
    public static class TokenApi {
        public static final String TOKEN_ID = "tokenId";
        public static final String GET_LIST_TOKEN = "tokens";
        public static final String GET_A_TOKEN = "tokens/{"+ TOKEN_ID +"}";
        public static final String GET_TXS = "tokens/{"+ TOKEN_ID +"}/txs";
        public static final String GET_MINTS = "tokens/{"+ TOKEN_ID +"}/mints";
    }
    public static class TransactionApi {
        public static final String HASH_ID = "hash";
        public static final String TYPE = "type";
        public static final String TRANSACTION_HASH = "txs/{" + HASH_ID + "}";
        public static final String FILTER_TRANSACTION = "txs";
    }

    public static class ContractApi {
        public static final String GET_LIST_CONTRACT = "contracts";

    }

    public static class EpochApi{
        public static final String GET_EPOCH ="epochs";
        public static final String GET_CURRENT_EPOCH ="epochs/current";
        public static final String EPOCH_NO = "epochNo";
        public static final String GET_LIST_EPOCH_BY_EPOCH_NO ="epochs/{"+ EPOCH_NO +"}/blocks";
        public static final String GET_EPOCH_BY_EPOCH_NO ="epochs/{"+ EPOCH_NO +"}";

    }
    public static class PoliciesApi{
        public static final String POLICY_ID = "policyId";
        public static final String GET_TOKEN_BY_POLICIES = "policies/{"+POLICY_ID+"}/tokens";
        public static final String GET_POLICY_DETAIL = "policies/{"+POLICY_ID+"}";
        public static final String GET_HOLDER_BY_POLICIES = "policies/{"+POLICY_ID+"}/holders";
    }

    public static class StakeKeyApi{
        public static final String GET_STAKE_ADDRESS = "stakes/address/{address}";
    }
}
