package microservices.steps.token.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Token {
    private ArrayList<DataToken> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
