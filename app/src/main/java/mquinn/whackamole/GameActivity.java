package mquinn.whackamole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GameView(this));

    }

//    public class mGame extends SurfaceView {
//
//        private Bitmap mBitmap;
//        private SurfaceHolder mHolder;
//        public mGame(Context context) {
//
//            super(context);
//            mHolder = getHolder();
//            mHolder.addCallback(new SurfaceHolder.Callback() {
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder mHolder) {
//            }
//
//            @Override
//            public void surfaceCreated(SurfaceHolder mHolder) {
//                Canvas mCanvas = mHolder.lockCanvas(null);
//                onDraw(mCanvas);
//                mHolder.unlockCanvasAndPost(mCanvas);
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder mHolder, int format, int width, int height) {
//            }
//
//        });
//
//        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_heart);
//
//        protected void onDraw(Canvas canvas) {
//
//            canvas.drawColor(Color.BLACK);
//            canvas.drawBitmap(mBitmap, 10, 10, null);
//
//        }
//
//    }
//
//}

    public class GameView extends SurfaceView {

        private Bitmap bmp;
        private SurfaceHolder holder;
        private GameLoopThread gameLoopThread;
        private int x = 0;
        private int xSpeed = 1;

        public GameView(Context context) {

            super(context);
            gameLoopThread = new GameLoopThread(this);
            holder = getHolder();
            holder.addCallback(new SurfaceHolder.Callback() {

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    boolean retry = true;
                    gameLoopThread.setRunning(false);
                    while (retry) {
                        try {
                            gameLoopThread.join();
                            retry = false;
                        } catch (InterruptedException e) {
                        }
                    }
                }

                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    gameLoopThread.setRunning(true);
                    gameLoopThread.start();
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

            });
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_hole);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (x == getWidth() - bmp.getWidth()) {
                xSpeed = -1;
            }
            if (x == 0) {
                xSpeed = 1;
            }
            x = x + xSpeed;
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(bmp, x, 10, null);
        }
    }
}







