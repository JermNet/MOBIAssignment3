package com.example.m03_bounce;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jeremy on 24/9/24.
 */
public class BouncingBallView extends View {

    // List of balls and a reference to the first ball in the ArrayList
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private Ball ball_1;
    // List of rectangles and a reference to the first rectangle in the ArrayList
    private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
    private Rectangle rectangle_1;
    // List of squares and a reference to the first square in the ArrayList
    private ArrayList<Square> squares = new ArrayList<Square>();
    private Square square_1;

    // Box (what the shapes are drawn on top of)
    private Box box;

    // See RandomRGBColors.java, that explains what this is
    private RandomRGBColors randColors = new RandomRGBColors(0, 256);
    // See ShapesTouching.java, that explains what this is
    private ShapesTouching touching = new ShapesTouching();
    // See ClearShapes.java, that explains what this is
    private ClearShapes clearShapes = new ClearShapes(100, 100, 100);

    // For touch inputs - previous touch (x, y)
    private float previousX;
    private float previousY;

    // To calculate touch speed;
    private long previousTime;

    // Scores for when a shape touches a rectangle
    private int ballScore, squareScore;

    // Initializes everything else that will be used in the view and adds two of every shape
    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.v("BouncingBallView", "Constructor BouncingBallView");

        // Create the box and change color
        box = new Box(Color.rgb(186,219,237));

        // Adds a ball to the ArrayList and points ball_1 to the first element of the list
        balls.add(new Ball(Color.rgb(randColors.getRandomNumber(), randColors.getRandomNumber(), randColors.getRandomNumber())));
        ball_1 = balls.get(0);
        Log.w("BouncingBallLog", "Just added a bouncing ball");

        // Adds a second ball
        balls.add(new Ball(Color.rgb(randColors.getRandomNumber(), randColors.getRandomNumber(), randColors.getRandomNumber())));
        Log.w("BouncingBallLog", "Just added another bouncing ball");

        // Adds a rectangle to the ArrayList and points rectangle_1 to the first element of the list
        rectangles.add(new Rectangle(Color.rgb(randColors.getRandomNumber(), randColors.getRandomNumber(), randColors.getRandomNumber())));
        rectangle_1 = rectangles.get(0);
        Log.w("BouncingBallLog", "Just added a bouncing rectangle");

        // Adds a second rectangle
        rectangles.add(new Rectangle(Color.rgb(randColors.getRandomNumber(), randColors.getRandomNumber(), randColors.getRandomNumber())));
        Log.w("BouncingBallLog", "Just added another bouncing rectangle");

        // Adds a square to the ArrayList and points square_1 to the first element of the list
        squares.add(new Square(Color.rgb(randColors.getRandomNumber(), randColors.getRandomNumber(), randColors.getRandomNumber())));
        square_1 = squares.get(0);
        Log.w("BouncingBallLog", "Just added a bouncing square");

        // Adds a second square
        squares.add(new Square(Color.rgb(randColors.getRandomNumber(), randColors.getRandomNumber(), randColors.getRandomNumber())));

        // To enable keypad
        this.setFocusable(true);
        this.requestFocus();

        // To enable touch mode
        this.setFocusableInTouchMode(true);
    }

    // Called back to draw the view. Also called after invalidate().
    @Override
    protected void onDraw(Canvas canvas) {

        Log.v("BouncingBallView", "onDraw");

        // Draw the components
        box.draw(canvas);

        // Makes canvas transparent, I don't want that so it's commented out
        // canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        for (Rectangle rectangle : rectangles) {
            // Draw each rectangle in the list
            rectangle.draw(canvas);
            // Update the position of the rectangle
            rectangle.moveWithCollisionDetection(box);
        }

        for (Ball ball : balls) {
            // Draw each ball in the list
            ball.draw(canvas);
            // Update the position of the ball
            ball.moveWithCollisionDetection(box);

            // Check the collision with rectangles
            for (Rectangle rectangle : rectangles) {
                if (touching.isTouching(rectangle, ball)) {
                    ballScore+=1;
                    Log.w("Score Log", "Here is your ball touchy score! (ew): " + ballScore);
                }
            }
        }

        for (Square square : squares) {
            // Draw each square in the list
            square.draw(canvas);
            // Update the position of the square
            square.moveWithCollisionDetection(box);

            // Check the collision with rectangles
            for (Rectangle rectangle : rectangles) {
                if (touching.isTouching(rectangle, square)) {
                    squareScore += 1;
                    Log.w("Score Log", "Here is your square touchy score! (yucky): " + squareScore);
                }
            }
        }

        // Drawing the box last draws it over everything, making it look as though no balls/rectangles are drawn
        //box.draw(canvas);

        // NOT calling this method means the screen is never redrawn, meaning only the shapes
        // in this onDraw method are drawn and they never move.
        this.invalidate();
    }

    // Called back when the view is first created or its size changes.
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        // Set the movement bounds for the ball
        box.set(0, 0, w, h);
        Log.w("BouncingBallLog", "onSizeChanged w=" + w + " h=" + h);
    }

    // Touch-input handler
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        float deltaX, deltaY;
        long deltaTime;


        // For calculating a fast or slow swipe
        long currentTime = System.currentTimeMillis();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // Modify rotational angles according to movement
                deltaX = currentX - previousX;
                deltaY = currentY - previousY;
                deltaTime =  currentTime - previousTime;
                // NOTE: Removed scaling factor since it just changed the speed of ball_1 and that's it
                // We have time and distance, so we can get speed (math!)
                float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                float speed = distance / deltaTime;

                // Log
                Log.w("TouchSpeedLog", "Speed: " + speed);

                // Similar to scaling factor but being used as how to decide between ball and square
                float decideSpeed = 10.0f;

                // Check speed against decideSpeed to create balls and squares
                if (speed < decideSpeed) {
                    // Uses getRandomNumber method of my RandomRGBColors object to get a random number within the values of RGB.
                    balls.add(new Ball(Color.rgb(randColors.getRandomNumber(), randColors.getRandomNumber(), randColors.getRandomNumber()), previousX, previousY, deltaX, deltaY));
                    Log.w("BouncingBallLog", "Just added a slow swipe ball");
                }
                else {
                    squares.add(new Square(Color.rgb(randColors.getRandomNumber(), randColors.getRandomNumber(), randColors.getRandomNumber()), previousX, previousY, deltaX, deltaY));
                    Log.w("BouncingBallLog", "Just added a fast swipe square");
                }


                // A way to clear list when too many shapes
                balls = clearShapes.ClearBalls(balls, randColors);
                ball_1 = balls.get(0);
                rectangles = clearShapes.ClearRectangles(rectangles, randColors);
                rectangle_1 = rectangles.get(0);
                squares = clearShapes.ClearSquares(squares, randColors);
                square_1 = squares.get(0);

        }
        // Save current x, y and time
        previousX = currentX;
        previousY = currentY;
        previousTime = currentTime;

        // As I expected, this makes it everything is only redrawn when the screen is being touched
        // this.invalidate();

        return true;  // Event handled
    }

    // Called when button is pressed, this now makes rectangles so every shape can be created by the user
    public void RectangleButton() {
        Log.d("BouncingBallView  BUTTON", "User tapped the  button ... VIEW");
        Random rand = new Random();
        // Get half of the width and height as we are working with a circle
        int viewWidth = this.getMeasuredWidth();
        int viewHeight = this.getMeasuredHeight();

        // Make random x,y, velocity
        int x = rand.nextInt(viewWidth);
        int y = rand.nextInt(viewHeight);
        int dx = rand.nextInt(50);
        int dy = rand.nextInt(20);

        rectangles.add(new Rectangle(Color.rgb(randColors.getRandomNumber(), randColors.getRandomNumber(), randColors.getRandomNumber()), x, y, dx, dy));  // add ball at every touch event
        // Having invalidate here makes it so everything is only redrawn for the brief moment the button is pressed.
        // this.invalidate();

        // A way to clear list when too many shapes
        balls = clearShapes.ClearBalls(balls, randColors);
        ball_1 = balls.get(0);
        rectangles = clearShapes.ClearRectangles(rectangles, randColors);
        rectangle_1 = rectangles.get(0);
        squares = clearShapes.ClearSquares(squares, randColors);
        square_1 = squares.get(0);
    }

}
