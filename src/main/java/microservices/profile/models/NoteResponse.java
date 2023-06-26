package microservices.profile.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class NoteResponse {
    public ArrayList<Note> data;
    public int totalItems;

    @Data
    public class Note{
        public int id;
        public String txHash;
        public String note;
        public String createdDate;
    }
}
