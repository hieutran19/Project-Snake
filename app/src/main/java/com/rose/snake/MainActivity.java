package com.rose.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private static final int FPS = 60;
    private static final int SPEED = 25;
    private static final int STATUS_PAUSED = 1;
    private static final int STATUS_START = 2;
    private static final int STATUS_PLAYING = 3;

    private GameView mGameView;


    private Button mGameBtn;
    private final AtomicInteger mGameStatus = new AtomicInteger(STATUS_START);
    private final Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGameView = findViewById(R.id.game_view);

        mGameBtn = findViewById(R.id.game_control_btn);

        mGameView.init();


        findViewById(R.id.up_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                mGameView.setDirection(Direction.UP);
            }
        });
        findViewById(R.id.down_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                mGameView.setDirection(Direction.DOWN);
            }
        });
        findViewById(R.id.left_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                mGameView.setDirection(Direction.LEFT);
            }
        });
        findViewById(R.id.right_btn).setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                mGameView.setDirection(Direction.RIGHT);
            }
        });

        mGameBtn.setOnClickListener(v -> {
            if (mGameStatus.get() == STATUS_PLAYING) {
                setGameStatus(STATUS_PAUSED);
            } else {
                setGameStatus(STATUS_PLAYING);
            }
        });

        setGameStatus(STATUS_START);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGameStatus.get() == STATUS_PLAYING) {
            setGameStatus(STATUS_PAUSED);
        }
    }

    private void setGameStatus(int gameStatus) {
        int prevStatus = mGameStatus.get();
        mGameBtn.setText("start");
        mGameStatus.set(gameStatus);
        switch (gameStatus) {
            case STATUS_START:
                mGameView.newGame();
                break;
            case STATUS_PLAYING:
                startGame();
                mGameBtn.setText("pause");
                break;
        }
    }

    private void startGame() {
        final int delay = 1000 / FPS;
        new Thread(() -> {
            int count = 0;
            while (mGameStatus.get() != STATUS_PAUSED) {
                try {
                    Thread.sleep(delay);
                    if (count % SPEED == 0) {
                        mGameView.next();
                        mHandler.post(mGameView::invalidate);
                    }
                    count++;
                } catch (InterruptedException ignored) {
                }
            }

        }).start();
    }
}