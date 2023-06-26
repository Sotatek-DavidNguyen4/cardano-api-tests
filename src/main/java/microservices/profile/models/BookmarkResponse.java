package microservices.profile.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class BookmarkResponse {
    public int id;
    public Object urlPage;
    public String keyword;
    public String type;
    public String network;
    public String createdDate;
}
