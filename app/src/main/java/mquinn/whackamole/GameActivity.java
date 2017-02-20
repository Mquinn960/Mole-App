package mquinn.whackamole;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private TextView mTimeView;
    private TextView mScoreView;
    private CountDownTimer mTimer;
    // TODO: get the seconds desired from options screen
    private int maxTime = 60 * 1000;
    private long stepTime = 1 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mTimeView = (TextView) findViewById(R.id.textTimeVal);
        mScoreView = (TextView) findViewById(R.id.textScoreVal);

        mTimer = new myTimer(maxTime, stepTime);
        mTimer.start();

        m1=(ImageButton) findViewById(R.id.image);
        m2=(ImageButton) findViewById(R.id.imageButton2);
        m3=(ImageButton) findViewById(R.id.imageButton3);
        m4=(ImageButton) findViewById(R.id.imageButton4);
        m5=(ImageButton) findViewById(R.id.imageButton5);
        m6=(ImageButton) findViewById(R.id.imageButton6);
        m7=(ImageButton) findViewById(R.id.imageButton7);
        m8=(ImageButton) findViewById(R.id.imageButton8);
        m9=(ImageButton) findViewById(R.id.imageButton9);

    }

    public class myTimer extends CountDownTimer {
        public myTimer(int startTime, long interval) {
            super(maxTime, stepTime);
        }
        @Override

        // Game ending via Out Of Time event
        public void onFinish() {

            //create a handler to wait 2 seconds before starting the EndGame activity and send the score

//            new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(getApplicationContext(), EndGame.class);
//                    String sc=String.valueOf(s);
//                    intent.putExtra("score", sc);
//                    startActivity(intent);
//                }
//            }

        }


        //this function will get called every half a second until the countdown is finished
        public void onTick(long millisUntilFinished) {
            mTimeView.setText(String.valueOf(millisUntilFinished / 1000));

            // logic determining which mole will pop up on tick

        }



    }





}
