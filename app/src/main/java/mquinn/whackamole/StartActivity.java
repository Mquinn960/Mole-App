package mquinn.whackamole;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button mButtonStart=(Button) findViewById(R.id.button_start);
        Button mButtonScores=(Button) findViewById(R.id.button_scores);
        Button mButtonOptions=(Button) findViewById(R.id.button_options);

        // Listeners for each button (go to Play, go to Scores, go to Options)

        if (mButtonStart != null){
            mButtonStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (mButtonScores != null) {
            mButtonScores.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ScoresActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (mButtonOptions != null) {
            mButtonOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), OptionsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
