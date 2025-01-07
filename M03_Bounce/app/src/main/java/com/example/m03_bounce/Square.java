package com.example.m03_bounce;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.Random;

/**
 * Created by Jeremy on 24/9/24.
 */
// May make a shapes abstract class for these to inherit from but idk how much I want to do that
public class Square {

    // Square height and width
    float width = 100;
    float height = 100;
    // Now the top left corner since it's a square
    float x;
    float y;
    // Square speed
    float speedX;
    float speedY;
    // Acceleration for different axis
    private double ax, ay, az = 0;

    // Needed for Canvas.drawRect()
    private RectF bounds;
    // Paint style, aka colour used for drawing
    private Paint paint;

    // Used for random number generator
    Random r = new Random();

    // Constructor numero uno
    public Square(int color) {
        // Initialize bounds and paint and set paint color
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // Random position and speed
        x = r.nextInt(800);
        y = r.nextInt(800);
        speedX = r.nextInt(100) - 5;
        speedY = r.nextInt(100) - 5;
    }

    // Constructor numero dos
    public Square(int color, float x, float y, float speedX, float speedY) {
        // Initialize bounds and paint and set paint color
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // Set parameter position and speed instead of random values
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    // Set acceleration values
    public void setAcc(double ax, double ay, double az){
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }

    // Check for collision detection with box (i.e what everything is being painted on)
    public void moveWithCollisionDetection(Box box) {
        // Get new (x,y) position
        x += speedX;
        y += speedY;

        // Add acceleration to speed
        speedX += ax;
        speedY += ay;

        // Detect collision and react
        if (x + width > box.xMax) {
            speedX = -speedX;
            x = box.xMax - width;
        } else if (x < box.xMin) {
            speedX = -speedX;
            x = box.xMin;
        }
        if (y + height > box.yMax) {
            speedY = -speedY;
            y = box.yMax - height;
        } else if (y < box.yMin) {
            speedY = -speedY;
            y = box.yMin;
        }
    }

    // Draw square
    public void draw(Canvas canvas) {
        bounds.set(x, y, x+width, y+height);
        canvas.drawRect(bounds, paint);
    }

    // Methods to get square variables since they are needed for ShapesTouching
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
