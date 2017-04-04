package mquinn.whackamole;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        TextView ScoreCheck = (TextView) findViewById(R.id.Score_Test);

        Intent intent = getIntent();
        int ScoreValue = intent.getExtras().getInt("score");

        ScoreCheck.setText(ScoreValue);

    }
}
