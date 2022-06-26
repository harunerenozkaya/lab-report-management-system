package com.harun.labManagement.util;

import java.util.Random;

public class IDGenerator {

    public static Long generateID(int length){
        Random rnd = new Random();
        double lowerBound = Math.pow(10,length-1);
        double upperBound = Math.pow(10,length)-1;

        double n = lowerBound + rnd.nextDouble(upperBound);

        return (long) n;
    }
}
