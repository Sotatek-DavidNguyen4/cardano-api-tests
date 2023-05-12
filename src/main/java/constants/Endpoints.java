package constants;

public class Endpoints {
    public static class DelegationApi {

        public static final String POOL_DETAIL_URI = "delegations/pool-detail-header/{poolView}";
    }

    public static class PoolControllerApi {
        public static final String REGISTRATION_POOLS = "pools/registration";
        public static final String DEREGISTRATION_POOLS = "pools/de-registration";
    }
    public static class TokenApi {
        public static final String GET_LIST_TOKEN = "/tokens";
        public static final String GET_A_TOKEN = "/tokens/{tokenId}";
    }
}
