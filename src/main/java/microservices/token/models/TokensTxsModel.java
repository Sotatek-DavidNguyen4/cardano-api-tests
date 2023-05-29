package microservices.token.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TokensTxsModel {
    private ArrayList<TokenTxsModel> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
