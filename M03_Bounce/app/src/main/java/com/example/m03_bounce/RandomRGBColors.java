package com.example.m03_bounce;
import java.util.Random;

// Class to get random numbers to use for colors, I found it easiest to just have this as it's own class instead of a method in BouncingBallView
public class RandomRGBColors {
    private Random rand;
    private int low, high;

    // Intended to be 0 and 256 since RGB goes from 0-255, but any numbers will work in this constructor
    // Low and high numbers are inclusive and exclusive respectively
    public RandomRGBColors(int low, int high) {
        rand = new Random();
        this.low = low;
        this.high = high;
    }

    public int getRandomNumber() {
        return rand.nextInt(high-low) + low;
    }

}
