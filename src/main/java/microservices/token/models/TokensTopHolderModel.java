package microservices.token.models;

import lombok.Data;

import java.util.ArrayList;
@Data
public class TokensTopHolderModel {
    private ArrayList<TokenTopHolderModel> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
