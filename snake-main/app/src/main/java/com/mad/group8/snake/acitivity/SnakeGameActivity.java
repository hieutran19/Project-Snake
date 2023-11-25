package com.mad.group8.snake.acitivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mad.group8.snake.R;
import com.mad.group8.snake.model.Food;
import com.mad.group8.snake.model.Snake;

public class SnakeGameActivity extends AppCompatActivity {

    private Button btnUp, btnDown, btnLeft, btnRight;
    private Snake snake;
    private Food food;
    private GridLayout gameGrid;

    // Adjust this value to control the speed of the snake
    private static final long DELAY_MS = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_game);

        // Initialize buttons and game grid
        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        gameGrid = findViewById(R.id.gameGrid);
        initializeGameGrid();

        // Initialize Snake object
        snake = new Snake();
        food = new Food(snake);
        snake.initializeFood();
        snake.renderOnGrid(gameGrid);



        // Set onClick listeners for each button
        btnUp.setOnClickListener(view -> onUpButtonClick());
        btnDown.setOnClickListener(view -> onDownButtonClick());
        btnLeft.setOnClickListener(view -> onLeftButtonClick());
        btnRight.setOnClickListener(view -> onRightButtonClick());

        // Start the snake movement
        startSnakeMovement();
    }

    private void initializeGameGrid() {
        int numRows = 10;
        int numColumns = 10;

        // Set column count for the grid
        gameGrid.setColumnCount(numColumns);

        // Create grid cells
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                // Create a new TextView for each cell
                TextView cell = new TextView(this);
                cell.setLayoutParams(new GridLayout.LayoutParams());
                cell.setWidth(100);
                cell.setHeight(100);
                cell.setBackgroundResource(R.drawable.grid_cell_border);
                cell.setBackgroundColor(Color.GRAY);  // Set background resource
                cell.setGravity(Gravity.CENTER);
                int cellNumber = row * numColumns + col + 1;
                //cell.setText(String.valueOf(cellNumber));
                // Add the TextView to the grid
                gameGrid.addView(cell);
            }
        }
    }

    private void onUpButtonClick() {
        if (snake.getCurrentDirection() != Snake.DIRECTION_DOWN ) {
            snake.setCurrentDirection(Snake.DIRECTION_UP);
        }
    }

    private void onDownButtonClick() {
        if (snake.getCurrentDirection() != Snake.DIRECTION_UP ) {
            snake.setCurrentDirection(Snake.DIRECTION_DOWN);
        }
    }

    private void onLeftButtonClick() {
        if (snake.getCurrentDirection() != Snake.DIRECTION_RIGHT) {
            snake.setCurrentDirection(Snake.DIRECTION_LEFT);
        }
    }

    private void onRightButtonClick() {
        if (snake.getCurrentDirection() != Snake.DIRECTION_LEFT) {
            snake.setCurrentDirection(Snake.DIRECTION_RIGHT);
        }
    }

    private void startSnakeMovement() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Move the snake
                snake.move();
                // Check for food collision
                if (snake.checkFoodCollision()) {
                    // If the snake eats food, increase length and spawn new food
                    snake.eatFood();
                }
                if (snake.checkCollision()) {
                    // Game over condition
                    // You can handle game over actions here, such as displaying a message or restarting the game
                    // For now, let's log a message and finish the activity
                    Log.d("SnakeGame", "Game Over");
                    finish();
                    return; // Exit the method to stop further movements
                }

                // Render the updated state on the grid
                snake.renderOnGrid(gameGrid);
                // Schedule the next move
                handler.postDelayed(this, DELAY_MS);
            }
        }, DELAY_MS);
    }
}
