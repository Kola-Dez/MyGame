package Kernel;

import java.util.HashMap;

public class Converter {

    public HashMap<String, String> convertSS(HashMap<String, Integer> data){
        HashMap<String, String> newLevel = new HashMap<>();
        for (String key : data.keySet()) {
            Integer value = data.get(key);
            String stringValue = String.valueOf(value);
            newLevel.put(key, stringValue);
        }
        return newLevel;
    }
    public HashMap<String, Integer> convertSI(HashMap<String, String> data){
        HashMap<String, Integer> newLevel = new HashMap<>();
        for (String key : data.keySet()) {
            String value = data.get(key);
            Integer intValue = Integer.valueOf(value);
            newLevel.put(key, intValue);
        }
        return newLevel;
    }

}
