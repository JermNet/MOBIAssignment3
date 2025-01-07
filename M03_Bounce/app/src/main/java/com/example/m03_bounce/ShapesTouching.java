package com.example.m03_bounce;

/**
 * Created by Jeremy on 24/9/24.
 */
public class ShapesTouching {

    // This is for collision with the rectangle, similar concept to interacting with the box
    public boolean isTouching(Rectangle rectangle, Ball ball) {
        // Checks ball's right again rectangle's left, ball's left against rectangle's right, ball's bottom against rectangle's
        // top and ball's tap against rectangle's bottom in that order
        return ball.getX() + ball.getRadius() > rectangle.getX() &&
                ball.getX() - ball.getRadius() < rectangle.getX() + rectangle.getWidth() &&
                ball.getY() + ball.getRadius() > rectangle.getY() &&
                ball.getY() - ball.getRadius() < rectangle.getY() + rectangle.getHeight();
    }

    // Same thing but for a rectangle and square
    public boolean isTouching(Rectangle rectangle, Square square) {
        return rectangle.getX() < square.getX() + square.getWidth() &&
                rectangle.getX() + rectangle.getWidth() > square.getX() &&
                rectangle.getY() < square.getY() + square.getHeight() &&
                rectangle.getY() + rectangle.getHeight() > square.getY();
    }
}
