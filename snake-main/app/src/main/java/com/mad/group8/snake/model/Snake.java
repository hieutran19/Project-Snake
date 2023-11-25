package com.mad.group8.snake.model;

import android.graphics.Color;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    // Direction constants
    public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_DOWN = 2;
    public static final int DIRECTION_LEFT = 3;
    public static final int DIRECTION_RIGHT = 4;
    private int length = 2;
    private int gridRowCount = 10;
    private int gridColumnCount = 10;
    // Initial direction
    private int currentDirection = DIRECTION_RIGHT;
    private boolean canChangeDirectionVertically = true;

    // Snake body represented as a list of coordinates
    private List<Coordinate> body;

    // Food coordinate
    private Food food;

    public Snake() {
        // Initialize the snake with a length of 5 and a default position
        body = new ArrayList<>();
        for (int i = length - 1; i >= 0; i--) {
            body.add(new Coordinate(i, 0)); // Initial position (adjust as needed)
        }
    }
    public void initializeFood() {
        food = new Food(this);
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(int direction) {
        this.currentDirection = direction;
    }

    public List<Coordinate> getBody() {
        return body;
    }

    public void move() {
        // Move the snake based on the current direction
        Coordinate head = body.get(0);
        Coordinate newHead = new Coordinate(head.getX(), head.getY());

        // Update the position based on the current direction
        switch (currentDirection) {
            case DIRECTION_UP:
                if (canChangeDirectionVertically && gridRowCount != 0) {
                    newHead.setY((newHead.getY() - 1 + gridRowCount) % gridRowCount); // Wrap around vertically
                }
                break;
            case DIRECTION_DOWN:
                if (canChangeDirectionVertically && gridRowCount != 0) {
                    newHead.setY((newHead.getY() + 1) % gridRowCount); // Wrap around vertically
                }
                break;
            case DIRECTION_LEFT:
                if (gridColumnCount != 0) {
                    newHead.setX((newHead.getX() - 1 + gridColumnCount) % gridColumnCount); // Wrap around horizontally
                }
                break;
            case DIRECTION_RIGHT:
                if (gridColumnCount != 0) {
                    newHead.setX((newHead.getX() + 1) % gridColumnCount); // Wrap around horizontally
                }
                break;
        }

        // Add the new head to the front of the body
        body.add(0, newHead);

        // Remove the tail to maintain the snake's length
        if (body.size() > length) {
            body.remove(body.size() - 1);
        }
    }

    public void renderOnGrid(GridLayout grid) {
        // Reset the background color of all cells to white
        for (int i = 0; i < grid.getChildCount(); i++) {
            View cell = grid.getChildAt(i);
            cell.setBackgroundColor(Color.WHITE);
        }

        // Iterate through the snake's body and update the appearance on the grid
        for (Coordinate segment : body) {
            int index = segment.getY() * grid.getColumnCount() + segment.getX();
            if (index >= 0 && index < grid.getChildCount()) {
                View cell = grid.getChildAt(index);
                cell.setBackgroundColor(Color.GREEN); // Set color to represent the snake
            }
        }

        // Render the food on the grid
        food.renderOnGrid(grid);
    }

    public boolean checkFoodCollision() {
        // Check if the snake's head collides with the food
        Snake.Coordinate head = body.get(0);
        return head.getX() == food.getX() && head.getY() == food.getY();
    }

    public void eatFood() {
        // Increase the length of the snake
        grow();

        // Spawn new food
        food.spawnFood();
    }

    public void grow() {
        // Increase the length of the snake by adding a new segment
        // You can customize the logic for growing based on your game rules
        // For example, add a new segment at the tail when the snake eats food
        length++;
    }

    public boolean checkCollision() {
        // Check for collisions with walls or itself
        // Implement collision logic based on your game rules
        // Return true if there is a collision, false otherwise
        Coordinate head = body.get(0);
        for (int i = 1; i < body.size(); i++) {
            Coordinate segment = body.get(i);
            if (head.getX() == segment.getX() && head.getY() == segment.getY()) {
                return true; // Collision with body
            }
        }
        return false;
    }

    // Define a simple Coordinate class to represent (x, y) positions
    public static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
