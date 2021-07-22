package com.ascential.technical.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberFinderImpl implements NumberFinder {
    Logger logger = LoggerFactory.getLogger(NumberFinderImpl.class);
    FastestComparator fastestComparator = new FastestComparator();

    public boolean contains(int valueToFind, List<CustomNumberEntity> list) {
        if (list == null || list.isEmpty()) {
            logger.info("CustomNumberEntity is empty so the value {} is not present", valueToFind);
            return false;
        }
        list.sort(Comparator.comparing(entity -> Integer.parseInt(entity.getNumber())));
        int firstIndex = 0;
        int lastIndex = list.size();
        while (firstIndex <= lastIndex) {
            int middleIndex = (firstIndex + lastIndex) / 2;
            if (fastestComparator.compare(valueToFind, list.get(middleIndex)) == 0) {
                logger.info("{} is present in the json", valueToFind);
                return true;
            } else if (fastestComparator.compare(valueToFind, list.get(middleIndex)) > 0) {
                firstIndex = middleIndex + 1;
            } else if (fastestComparator.compare(valueToFind, list.get(middleIndex)) < 0) {
                lastIndex = middleIndex - 1;
            }
        }
        logger.info("{} is not present in the json", valueToFind);
        return false;
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
            if(!isJsonArray(json)){
                return null;
            }
            JSONArray obj = new JSONArray(json);
            for (int i = 0; i < obj.length(); i++) {
                if (!obj.getJSONObject(i).get("number").equals(null)) {
                    String number = (String) obj.getJSONObject(i).get("number");
                    if (isNumeric(number)) {
                        CustomNumberEntity customNumberEntity = new CustomNumberEntity();
                        customNumberEntity.setNumber(number);
                        customNumberEntityList.add(customNumberEntity);
                    }

                }
            }

        }

        return customNumberEntityList;
    }


    private boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    private boolean isJsonArray(String str) {
        try {
            new JSONArray(str);
        } catch (JSONException e) {
            logger.error("Not a valid json file");
            return false;
        }
        return true;
    }


}
