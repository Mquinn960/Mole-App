package mquinn.whackamole;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private TextView mTimeView;
    private TextView mScoreView;
    private CountDownTimer mTimer;
    // TODO: get the seconds desired from options screen
    private int maxTime = 60 * 1000;
    private long stepTime = 1 * 1000;


//    new CountDownTimer(30000, 1000) {
//
//        public void onTick(long millisUntilFinished) {
//            mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
//        }
//
//    public void onFinish() {
//        mTextField.setText("done!");
//    }
//}.start();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mTimeView = (TextView) findViewById(R.id.textTimeVal);
        mScoreView = (TextView) findViewById(R.id.textScoreVal);

        mTimer = new myTimer(maxTime, stepTime);
        mTimer.start();

    }

    public class myTimer extends CountDownTimer {
        public myTimer(int startTime, long interval) {
            super(maxTime, stepTime);
        }
        @Override
        //when the timer finishes...
        public void onFinish() {
            //display Time's up
            mTimeView.setText("Time's up!");
            //disable all the moles
            clear();
            //create a handler to wait 2 seconds before starting the EndGame activity and send the score
            handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), EndGame.class);
                    String sc=String.valueOf(s);
                    intent.putExtra("score", sc);
                    startActivity(intent);
                }
            }, 2000);
        }
        //this function will get called every half a second until the countdown is finished
        public void onTick(long millisUntilFinished) {
            //change the time to display how much time is left since it's in milliseconds it has to be divided by 1000 to display seconds
            time.setText("0:" + millisUntilFinished / 1000);
            //generate a random number and depending on the number enable a certain mole
            int r=(int)(Math.random()*10);
            if (r==1){
                if (!m1.isEnabled())
                    enableMole(m1);
            }else if (r==2){
                if (!m2.isEnabled())
                    enableMole(m2);
            }
            else if (r==3){
                if (!m3.isEnabled())
                    enableMole(m3);
            }
            else if (r==4){
                if (!m4.isEnabled())
                    enableMole(m4);
            }
            else if (r==5){
                if (!m5.isEnabled())
                    enableMole(m5);
            }
            else if (r==6){
                if (!m6.isEnabled())
                    enableMole(m6);
            }
            else if (r==7){
                if (!m7.isEnabled())
                    enableMole(m7);
            }
            else if (r==8){
                if (!m8.isEnabled())
                    enableMole(m8);
            }
            else {
                if (!m9.isEnabled())
                    enableMole(m9);
            }
        }
    }

}
