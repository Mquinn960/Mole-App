package mquinn.whackamole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private TextView mTimeView;
    private TextView mScoreView;


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

    }
}
