package com.example.android.flash_card;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Utils {
    public static List<Integer> randomIntArray(int maxNum, int size) {
        size = size <= maxNum ? size : maxNum;

        int[] numList = new int[maxNum];
        List<Integer> result = new ArrayList<>();
        Random rand = new Random();

        for(int i = 1; i <= maxNum; i++) {
            numList[i-1] = i;
        }

        while(size > 0) {
            int randIndex = rand.nextInt(maxNum--);
            result.add(numList[randIndex]);
            numList[randIndex] = numList[maxNum];
        }

        return result;
    }
}
