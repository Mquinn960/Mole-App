package mquinn.whackamole;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    private String defaultName = String.valueOf(R.string.default_name);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        TextView ScoreCheck = (TextView) findViewById(R.id.mScoreValue);
        TextView ReasonCheck = (TextView) findViewById(R.id.mReasonDisplay);

        Button mSubmit = (Button) findViewById(R.id.button_Submit);
        Button mMenu = (Button) findViewById(R.id.button_Menu);

        final EditText nameBox = (EditText)findViewById(R.id.mNameEntry);

        final Player newPlayer = new Player();

        Intent intent = getIntent();
        final int ScoreValue = intent.getExtras().getInt("score");
        String ReasonValue = intent.getExtras().getString("reason");

        final Dbhelper db = new Dbhelper(this);

        newPlayer.setVarName(defaultName);
        newPlayer.setVarScore(ScoreValue);

        ScoreCheck.setText(String.valueOf(ScoreValue));
        ReasonCheck.setText(ReasonValue);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String playerName = nameBox.getText().toString();

                newPlayer.setVarName(playerName);
                newPlayer.setVarScore(ScoreValue);

                db.addPlayer(newPlayer);

                Intent intent = new Intent(getApplicationContext(), ScoresActivity.class);
                startActivity(intent);

            }
        });

        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });

    }

}
