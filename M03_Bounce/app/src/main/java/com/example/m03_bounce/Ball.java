package com.example.m03_bounce;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.Random;

/**
 * Created by Jeremy on 24/9/24.
 */
public class Ball {

    // Ball's radius
    float radius = 50;
    // Ball's center (x,y)
    float x;
    float y;
    // Ball's speed
    float speedX;
    float speedY;
    // Acceleration for different axis
    private double ax, ay, az = 0;

    // Needed for Canvas.drawOval()
    private RectF bounds;
    // The paint style, colour used for drawing
    private Paint paint;

    // Used for random number generator
    Random r = new Random();

    // Constructor numero uno
    public Ball(int color) {
        // Initialize bounds and paint and set paint color
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // Random position and speed
        x = radius + r.nextInt(800);
        y = radius + r.nextInt(800);

        // Increased/decreased bounds for increased/decreased speed range,
        // set to a hard value for always increased/decreased speed (used in other constructor)
        speedX = r.nextInt(100) - 5;
        speedY = r.nextInt(100) - 5;
        //speedX = 200;
        //speedY = 200;
    }

    // Constructor numero dos
    public Ball(int color, float x, float y, float speedX, float speedY) {
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
        if (x + radius > box.xMax) {
            speedX = -speedX;
            x = box.xMax - radius;
        } else if (x - radius < box.xMin) {
            speedX = -speedX;
            x = box.xMin + radius;
        }
        if (y + radius > box.yMax) {
            speedY = -speedY;
            y = box.yMax - radius;
        } else if (y - radius < box.yMin) {
            speedY = -speedY;
            y = box.yMin + radius;
        }
    }

    // Draw ball
    public void draw(Canvas canvas) {
        bounds.set(x - radius, y - radius, x + radius, y + radius);
        canvas.drawOval(bounds, paint);
    }

    // Methods to get ball variables since they are needed for ShapesTouching
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }
}
