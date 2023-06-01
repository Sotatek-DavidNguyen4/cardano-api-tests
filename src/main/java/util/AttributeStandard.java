package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AttributeStandard {

    public static boolean isValidStakeAddress(String stakeAddr){
        if(stakeAddr != null && !stakeAddr.isEmpty() && !stakeAddr.trim().isEmpty()){
            return stakeAddr.length() == 64 && stakeAddr.startsWith("stake");
        }else {
            return false;
        }
    }

    public static boolean areValidStakeAddress(List<String> stakeAddrs) {
        for (String stakeAddr : stakeAddrs) {
            System.out.println(stakeAddr);
            if (!isValidStakeAddress(stakeAddr)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidPoolId(String pool){
        if(pool != null && !pool.isEmpty() && !pool.trim().isEmpty()){
            return pool.length() == 56 && pool.startsWith("pool");
        }else {
            return false;
        }
    }

    public static boolean areValidPoolId(List<String> pools) {
        for (String pool : pools) {
            if (!isValidPoolId(pool)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidTokenFingerprint(String token){
        if(token != null && !token.isEmpty() && !token.trim().isEmpty()){
            return token.length() == 44 && token.startsWith("asset");
        }else {
            return false;
        }
    }

    public static boolean isValidBlockHash(String blockHash) {
        if(blockHash != null && !blockHash.isEmpty() && !blockHash.trim().isEmpty()){
            return blockHash.length() == 64;
        }else {
            return false;
        }
    }

    public static boolean isValidHash(String txHash) {
        if(txHash != null && !txHash.isEmpty() && !txHash.trim().isEmpty()){
            return txHash.length() == 64;
        }else {
            return false;
        }
    }
    public static boolean areValidHashes(List<String> txHashs) {
        for (String txHash : txHashs) {
            if (!isValidHash(txHash)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidDateFormat(String dateToValdate, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(dateToValdate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    public static boolean areValidDates(List<String> datesToValidate, String format) {
        for (String dateToValidate : datesToValidate) {
            if (!isValidDateFormat(dateToValidate, format)) {
                return false;
            }
        }
        return true;
    }

}
