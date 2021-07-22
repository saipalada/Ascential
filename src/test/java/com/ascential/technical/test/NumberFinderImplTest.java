package com.ascential.technical.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class NumberFinderImplTest {
    NumberFinderImpl numberFinder = new NumberFinderImpl();
    List<CustomNumberEntity> list1 = new ArrayList<>();
    List<CustomNumberEntity> list2 = new ArrayList<>();

    @Before
    public void setUp() {
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
        assertEquals(false, numberFinder.contains(20, numberFinder.readFromFile(file.getAbsolutePath())));
    }

    @Test
    public void readFromFile() {
        String path = "src/test/resources/number.json";
        File file = new File(path);
        assertEquals(list1, numberFinder.readFromFile(file.getAbsolutePath()));
    }
}
