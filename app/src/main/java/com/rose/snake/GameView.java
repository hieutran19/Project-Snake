package com.rose.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.Random;

public class GameView extends View {
    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final String TAG = "GameView";

    private static final int MAP_SIZE = 20;
    private static final int START_X =5;
    private static final int START_Y = 10;

    private final Point[][] mPoints = new Point[MAP_SIZE][MAP_SIZE];
    private final LinkedList<Point> mSnake = new LinkedList<>();
    private Direction mDir;

    private int mBoxSize;
    private int mBoxPadding;

    private final Paint mPaint = new Paint();
    public void init() {
        mBoxSize = getContext().getResources()
                .getDimensionPixelSize(R.dimen.game_size) / MAP_SIZE;
        mBoxPadding = mBoxSize / 10;
    }
    public void newGame() {
        mDir = Direction.RIGHT;
        initMap();
    }
    private void initMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                mPoints[i][j] = new Point(j, i);
            }
        }
        mSnake.clear();
        for (int i = 0; i < 3; i++) {
            Point point = getPoint(START_X + i, START_Y);
            point.type = PointType.SNAKE;
            mSnake.addFirst(point);
        }
    }
    private Point getPoint(int x, int y) {
        return mPoints[y][x];
    }
    public void next() {
        Point first = mSnake.getFirst();
        Point next = getNext(first);
        switch (next.type) {
            case EMPTY:
                next.type = PointType.SNAKE;
                mSnake.addFirst(next);
                mSnake.getLast().type = PointType.EMPTY;
                mSnake.removeLast();
                break;
        }
    }
    public void setDirection(Direction dir) {
        if ((dir == Direction.LEFT || dir == Direction.RIGHT) &&
                (mDir == Direction.LEFT || mDir == Direction.RIGHT)) {
            return;
        }
        if ((dir == Direction.UP || dir == Direction.DOWN) &&
                (mDir == Direction.UP || mDir == Direction.DOWN)) {
            return;
        }
        mDir = dir;
    }
    private Point getNext(Point point) {
        int x = point.x;
        int y = point.y;
        switch (mDir) {
            case UP:
                y = y == 0 ? MAP_SIZE - 1 : y - 1;
                break;
            case DOWN:
                y = y == MAP_SIZE - 1 ? 0 : y + 1;
                break;
            case LEFT:
                x = x == 0 ? MAP_SIZE - 1 : x - 1;
                break;
            case RIGHT:
                x = x == MAP_SIZE - 1 ? 0 : x + 1;
                break;
        }
        return getPoint(x, y);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int y = 0; y < MAP_SIZE; y++) {
            for (int x = 0; x < MAP_SIZE; x++) {
                int left = mBoxSize * x;
                int right = left + mBoxSize;
                int top = mBoxSize * y;
                int bottom = top + mBoxSize;
                switch (getPoint(x, y).type) {
                    case SNAKE:
                        mPaint.setColor(Color.BLACK);
                        canvas.drawRect(left, top, right, bottom, mPaint);
                        mPaint.setColor(Color.WHITE);
                        left += mBoxPadding;
                        right -= mBoxPadding;
                        top += mBoxPadding;
                        bottom -= mBoxPadding;
                        break;
                    case EMPTY:
                        mPaint.setColor(Color.BLACK);
                        break;
                }
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }
}
