package util;
import java.util.Random;

public class RamdomNumber {
    public int ramdomInger(int from){
        int randomNumber;
        Random random = new Random();
        randomNumber = random.nextInt(from);
        return randomNumber;
    }
}
