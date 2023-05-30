package microservices.token.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TokensModel {
    private ArrayList<TokenModel> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
