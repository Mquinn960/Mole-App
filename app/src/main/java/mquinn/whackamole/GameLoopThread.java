package mquinn.whackamole;

import android.graphics.Canvas;

/**
 * Created by Mquinn on 22/02/2017.
 */

//    public class GameLoopThread extends Thread {
//
//        private mGame view;
//        private boolean running = false;
//
//        public GameLoopThread(mGame view) {
//            this.view = view;
//        }
//
//        public void setRunning(boolean run) {
//            running = run;
//        }
//
//        @Override
//        public void run() {
//            while (running) {
//                Canvas mCanvas = null;
//                try {
//                    mCanvas = view.getHolder().lockCanvas();
//                    synchronized (view.getHolder()) {
//                        view.onDraw(mCanvas);
//                    }
//                } finally {
//                    if (mCanvas != null) {
//                        view.getHolder().unlockCanvasAndPost(c);
//                    }
//                }
//            }
//        }
//    }

public class GameLoopThread extends Thread {

    static final long FPS = 30;
    private GameActivity.GameView view;
    private boolean running = false;

    public GameLoopThread(GameActivity.GameView view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {

        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;

        while (running) {

            Canvas c = null;
            startTime = System.currentTimeMillis();

            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }

            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);

            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}

        }
    }
}