package mquinn.whackamole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;

public class GameActivity extends AppCompatActivity {



    new CountDownTimer(30000, 1000) {

        public void onTick(long millisUntilFinished) {
            mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
        }

    public void onFinish() {
        mTextField.setText("done!");
    }
}.start();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }
}
