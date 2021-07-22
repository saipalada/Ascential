package com.ascential.technical.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.json.JSONArray;

public class NumberFinderImpl implements NumberFinder {
    FastestComparator fastestComparator = new FastestComparator();

    public boolean contains(int valueToFind, List<CustomNumberEntity> list) {
        boolean contains = false;
        boolean positiveResult = false;
        boolean negativeResult = false;
        list.stream().sorted();
        for (CustomNumberEntity customNumberEntity : list) {
            if (fastestComparator.compare(valueToFind, customNumberEntity) == 0) {
                contains = true;
                break;
            } else if (fastestComparator.compare(valueToFind, customNumberEntity) > 0) {
                if (negativeResult) {
                    break;
                }
                positiveResult = true;
            } else {
                if (positiveResult) {
                    break;
                }
                negativeResult = true;
            }
        }

        return contains;
    }

    public List<CustomNumberEntity> readFromFile(String filePath) {
        List<CustomNumberEntity> customNumberEntityList = new ArrayList<>();
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.out.println("error d data");
        }
        if (content != null) {
            String json = content.replaceAll("\n", "");
            JSONArray obj = new JSONArray(json);
            for (int i = 0; i < obj.length(); i++) {
                if (!obj.getJSONObject(i).get("number").equals(null)) {
                    String number = (String) obj.getJSONObject(i).get("number");
                    if(isNumeric(number)){
                        CustomNumberEntity customNumberEntity = new CustomNumberEntity();
                        customNumberEntity.setNumber(number);
                        customNumberEntityList.add(customNumberEntity);
                    }

                }
            }

        }

        return customNumberEntityList;
    }



    public boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

}
