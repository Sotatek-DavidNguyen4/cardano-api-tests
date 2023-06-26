package microservices.profile.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import microservices.profile.constants.ProfileContants;

@Data
@AllArgsConstructor
public class CreateNoteInput {
    public String network;
    public String note;
    public String txHash;

    public CreateNoteInput(String network) {
        this.network = network;
        this.note = "auto test " + System.currentTimeMillis();
        this.txHash = "txHash " + System.currentTimeMillis();
    }
}
