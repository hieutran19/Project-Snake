package com.mad.group8.snake.model;

// Inside Food.java

import android.graphics.Color;
import android.view.View;
import android.widget.GridLayout;

import java.util.Random;

public class Food {
    private int x;
    private int y;
    private Random random;
    public Food(Snake snake) {
        random = new Random();
        spawnFood();
    }
    public Food() {
        random = new Random();
        spawnFood();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void spawnFood() {
        // Generate random coordinates until a valid position is found
        do {
            x = random.nextInt(10);
            y = random.nextInt(10);
        } while (isFoodOnSnake(new Snake()));
    }

    private boolean isFoodOnSnake(Snake snake) {
        // Check if the food's position overlaps with the snake's positions
        for (Snake.Coordinate snakePart : snake.getBody()) {
            if (snakePart.getX() == x && snakePart.getY() == y) {
                // Food overlaps with the snake
                return true;
            }
        }
        // Food does not overlap with the snake
        return false;
    }
    public void renderOnGrid(GridLayout grid) {
        // Update the appearance of the food on the grid
        int foodIndex = y * grid.getColumnCount() + x;
        if (foodIndex >= 0 && foodIndex < grid.getChildCount()) {
            View foodCell = grid.getChildAt(foodIndex);
            foodCell.setBackgroundColor(Color.RED); // Set color to represent the food
        }
    }
}
