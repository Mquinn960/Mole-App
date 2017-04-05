package mquinn.whackamole;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    // Less significant, relatively static declarations
    int varRandMole;
    private TextView mTimeView;
    private TextView mScoreView;
    public int varScore = 0;
    private int varLives = 5;
    final Handler handler = new Handler();
    public boolean varClose = false;

    // TODO: get the seconds desired from options screen
    // This is our game length
    private int maxTime = 60 * 1000;
    // Our game timer interval in millis
    private long stepTime = 1 * 1000;

    // This is our delay per mole popping up (difficulty)
    public int timeInterval = 1000;
    // This is the time a mole spends above ground (difficulty)
    public int moleUpTime = 350;

    public CountDownTimer mTimer = new myTimer(maxTime, stepTime);

    // Our initial functions, start the timer, start the first moleLoop
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mTimeView = (TextView) findViewById(R.id.textTimeVal);
        mScoreView = (TextView) findViewById(R.id.textScoreVal);

        mTimer.start();
        handler.post(moleLoop);

        varClose = false;

    }

    @Override
    protected void onPause() {

        super.onPause();
        varClose = true;

    }

    @Override
    protected void onStop() {

        super.onStop();
        varClose = true;

    }

    // Public timer class which is handling the game clock
    public class myTimer extends CountDownTimer {

        public myTimer(int maxTime, long stepTime) {

            super(maxTime, stepTime);

        }
        @Override

        // Called when the timer finishes
        public void onFinish() {

            // Call endgame class and pass score, reason (due to time out)
            this.cancel();
            String messageTime = getString(R.string.str_end_time);
            EndGame(varScore, messageTime);

            //reset difficulty vars
            timeInterval = 1000;
            moleUpTime = 350;

        }

        // Ticker called every #millis until done
        public void onTick(long millisUntilFinished) {

            // Using to set the time view every second (1000ms)
            mTimeView.setText(String.valueOf(millisUntilFinished / 1000));

            // deprecated
//            if ((millisUntilFinished/1000) == ((maxTime/1000)/1.33)){
//                // game passes first quarter
//                increaseDifficulty();
//            } else if ((millisUntilFinished/1000) == ((maxTime/1000)/2)){
//                // game passes half way
//                increaseDifficulty();
//            } else if ((millisUntilFinished/1000) == ((maxTime/1000)/4)){
//                // game in last quarter
//                increaseDifficulty();
//            }

            if (((millisUntilFinished/1000)%15 == 0) && (millisUntilFinished/1000) != 60){
                increaseDifficulty();
            }

        }
    }

    // functions to incrementally increase difficulty
    public void increaseDifficulty(){
        timeInterval *= 0.8;
        moleUpTime *= 0.8;
    }

    // Endgame method which passes our intent to EndActivity
    // Intents passed are our final score, and the reason the game is over
    public void EndGame(int EndScore, String Reason) {

                    Intent intent = new Intent(getApplicationContext(), EndActivity.class);
                    intent.putExtra("score", EndScore);
                    intent.putExtra("reason", Reason);

                    mTimer.cancel();
                    finish();
                    startActivity(intent);

    }


    // Game loop which handles moles popping up at random
    // using a runnable which calls itself every X millis
    public Runnable moleLoop = new Runnable() {

        int varPrev;

        public void stopRun (){
            handler.removeCallbacksAndMessages(null);
        }

        @Override
        public void run () {

                // Building array of moles and their images
                final ImageView moles[] = new ImageView[9];

                moles[0] = (ImageView) findViewById(R.id.imageMole1);
                moles[1] = (ImageView) findViewById(R.id.imageMole2);
                moles[2] = (ImageView) findViewById(R.id.imageMole3);
                moles[3] = (ImageView) findViewById(R.id.imageMole4);
                moles[4] = (ImageView) findViewById(R.id.imageMole5);
                moles[5] = (ImageView) findViewById(R.id.imageMole6);
                moles[6] = (ImageView) findViewById(R.id.imageMole7);
                moles[7] = (ImageView) findViewById(R.id.imageMole8);
                moles[8] = (ImageView) findViewById(R.id.imageMole9);

                // Pick a mole at random, if you get the same twice, reroll
                // Slightly less likely to double up
                varRandMole = new Random().nextInt(8);
                if (varRandMole == varPrev) {
                    varRandMole = new Random().nextInt(8);
                }
                varPrev = varRandMole;

                // Pop the mole up
                moles[varRandMole].animate().translationY(-120).setDuration(moleUpTime);

//            // Logging current mole activity
//            Log.i("GameActivity", "mole info:" + moles[varRandMole].getTranslationY());

                // Timer to pop our mole back down if player fails to hit it
                // Shuts down any active moles, could probably be revised only to do moles
                // which have been up for the allotted time Interval
                new Timer().schedule(new TimerTask() {
                    public void run() {

                        for (int i = 0; i < 9; i++) {
                            if (moles[i].getTranslationY() == -120) {

                                final int j = i;

                                // Sets the mole back to its beginning position
                                // run this update on the UI thread as we need a looper thread
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        moles[j].animate().translationY(0).setDuration(100);
                                    }
                                });

                                // Deduct a life if we miss a mole
                                varLives -= 1;
                                updateLives(varLives);

                            }
                        }
                    }
                }, timeInterval);

            if (!varClose) {
                handler.postDelayed(moleLoop, timeInterval);
            }
        }

    };

    // Handling our life indicators
    public void updateLives(int Lives){

        final ImageView heart1= (ImageView) findViewById(R.id.imageHeart1);
        final ImageView heart2= (ImageView) findViewById(R.id.imageHeart2);
        final ImageView heart3= (ImageView) findViewById(R.id.imageHeart3);
        final ImageView heart4= (ImageView) findViewById(R.id.imageHeart4);
        final ImageView heart5= (ImageView) findViewById(R.id.imageHeart5);

        // Start taking off lives, when none are left, call our game end method
        // These are run using main UI thread in a runnable as we cannot update UI resources
        // from without the main thread otherwise
        if (Lives == 4){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    heart5.setImageResource(R.drawable.placeholder_heart_empty);
                }
            });
        } else if (Lives == 3){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    heart4.setImageResource(R.drawable.placeholder_heart_empty);
                }
            });
        } else if (Lives == 2) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    heart3.setImageResource(R.drawable.placeholder_heart_empty);
                }
            });
        } else if (Lives == 1){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    heart2.setImageResource(R.drawable.placeholder_heart_empty);
                }
            });
        } else if (Lives == 0){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    heart1.setImageResource(R.drawable.placeholder_heart_empty);
                }
            });
            String messageLives = getString(R.string.str_end_lives);
            EndGame(varScore, messageLives);
        }

    }

    // Aiding modularity by making score updates it's own method
    public void updateScore(int Score){
        mScoreView.setText(String.valueOf(Score));
    }

    // OnClick function for mole objects when we hit them
    public void onClick(View v) {

        // Somewhat redundantly rebuilding our mole array privately
        final ImageView molesClick [] = new ImageView [9];

        molesClick [0] = (ImageView) findViewById(R.id.imageMole1);
        molesClick [1] = (ImageView) findViewById(R.id.imageMole2);
        molesClick [2] = (ImageView) findViewById(R.id.imageMole3);
        molesClick [3] = (ImageView) findViewById(R.id.imageMole4);
        molesClick [4] = (ImageView) findViewById(R.id.imageMole5);
        molesClick [5] = (ImageView) findViewById(R.id.imageMole6);
        molesClick [6] = (ImageView) findViewById(R.id.imageMole7);
        molesClick [7] = (ImageView) findViewById(R.id.imageMole8);
        molesClick [8] = (ImageView) findViewById(R.id.imageMole9);

        // Show hit-reg for testing
        //          Toast.makeText(v.getContext(),
        //                "Hit Registered",
        //                Toast.LENGTH_LONG).show();

        // Switch statement to find the right mole and pop him down
        switch(v.getId()) {
            case R.id.imageMole1:
                molesClick[0].animate().translationY(0).setDuration(20);
                break;
            case R.id.imageMole2:
                molesClick[1].animate().translationY(0).setDuration(20);
                break;
            case R.id.imageMole3:
                molesClick[2].animate().translationY(0).setDuration(20);
                break;
            case R.id.imageMole4:
                molesClick[3].animate().translationY(0).setDuration(20);
                break;
            case R.id.imageMole5:
                molesClick[4].animate().translationY(0).setDuration(20);
                break;
            case R.id.imageMole6:
                molesClick[5].animate().translationY(0).setDuration(20);
                break;
            case R.id.imageMole7:
                molesClick[6].animate().translationY(0).setDuration(20);
                break;
            case R.id.imageMole8:
                molesClick[7].animate().translationY(0).setDuration(20);
                break;
            case R.id.imageMole9:
                molesClick[8].animate().translationY(0).setDuration(20);
                break;
        }

        // Award points, update score
        varScore += 250;
        //mScoreView.setText(String.valueOf(varScore));
        updateScore(varScore);

    }

}










