package com.example.m03_bounce;
import android.graphics.Color;
import android.util.Log;
import java.util.ArrayList;

// This is what I'm using as my new feature to make this more "gamey."
// If the limit of balls is reached, it clears the balls and halves the number of rectangles you can have (rounded down)
// If the limit of rectangles is reached, it clears the rectangles and halves the number of squares you can have (rounded down
// If the limit of squares is reached, it clears the squares and halves the number of balls you can have (rounded down)
public class ClearShapes {
    private int ballLimit, rectangleLimit, squareLimit;

    public ClearShapes(int ballLimit, int rectangleLimit, int squareLimit) {
        this.ballLimit = ballLimit;
        this.rectangleLimit = rectangleLimit;
        this.squareLimit = squareLimit;
    }

    public ArrayList<Ball> ClearBalls(ArrayList<Ball> balls, RandomRGBColors randColors) {
        if (balls.size() > ballLimit) {
            balls.clear();
            balls.add(new Ball(Color.rgb(randColors.getRandomNumber(), randColors.getRandomNumber(), randColors.getRandomNumber())));
            rectangleLimit = (int)(rectangleLimit/2);
            Log.v("BouncingBallLog", "Too many balls, they have been cleared, rectangle limit is now " + rectangleLimit);
        }
        return balls;
    }

    public ArrayList<Rectangle> ClearRectangles(ArrayList<Rectangle> rectangles, RandomRGBColors randColors) {
        if (rectangles.size() > rectangleLimit) {
            rectangles.clear();
            rectangles.add(new Rectangle(Color.rgb(randColors.getRandomNumber(), randColors.getRandomNumber(), randColors.getRandomNumber())));
            squareLimit = (int)(squareLimit/2);
            Log.v("BouncingBallLog", "Too many rectangles, they have been cleared, square limit is now " + squareLimit);
        }
        return rectangles;
    }

    public ArrayList<Square> ClearSquares(ArrayList<Square> squares, RandomRGBColors randColors) {
        if (squares.size() > squareLimit) {
            squares.clear();
            squares.add(new Square(Color.rgb(randColors.getRandomNumber(), randColors.getRandomNumber(), randColors.getRandomNumber())));
            ballLimit = (int)(ballLimit/2);
            Log.v("BouncingBallLog", "Too many squares, they have been cleared, square limit is now " + ballLimit);
        }
        return squares;
    }
}
