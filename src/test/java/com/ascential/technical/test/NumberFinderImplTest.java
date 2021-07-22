package com.ascential.technical.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

public class NumberFinderImplTest {
    NumberFinderImpl numberFinder = new NumberFinderImpl();
    List<CustomNumberEntity> list1 = new ArrayList<>();
    List<CustomNumberEntity> list2 = new ArrayList<>();

    @Before
    public void setUp() {
        String log4jConfPath = "src/test/resources/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        String[] numbers = {"67","45","45","-3","12","100","3"};
        for(int i=0;i<numbers.length;i++){
            CustomNumberEntity entity = new CustomNumberEntity();
            entity.setNumber(numbers[i]);
            list1.add(entity);
        }
    }

    @Test
    public void contains() {
        String path = "src/test/resources/number.json";
        File file = new File(path);
        assertEquals(true, numberFinder.contains(67, numberFinder.readFromFile(file.getAbsolutePath())));
        assertFalse( numberFinder.contains(20, numberFinder.readFromFile(file.getAbsolutePath())));
        path = "src/test/resources/invalid.json";
        file = new File(path);
        assertFalse(numberFinder.contains(100, numberFinder.readFromFile(file.getAbsolutePath())));
    }

    @Test
    public void readFromFile() {
        String path = "src/test/resources/number.json";
        File file = new File(path);
        assertEquals(list1, numberFinder.readFromFile(file.getAbsolutePath()));
    }
}
