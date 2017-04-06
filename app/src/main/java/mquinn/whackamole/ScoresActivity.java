package mquinn.whackamole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        final Dbhelper db = new Dbhelper(this);
        final List<Player> dataList;

        TableLayout mTableLayout = (TableLayout)findViewById(R.id.scoreTable);

        dataList = db.getAll();

        Collections.sort(dataList, new Comparator<Player>() {
            @Override
            public int compare(Player playerOne, Player playerTwo) {
                return playerTwo.getVarScore() - (playerOne.getVarScore()); // Ascending
            }

        });

        for (int i = 0; i < dataList.size(); i++) {

            View tableRow = LayoutInflater.from(this).inflate(R.layout.scoreitem,null,false);

            TextView playerName = (TextView) tableRow.findViewById(R.id.player_name);
            TextView playerScore = (TextView) tableRow.findViewById(R.id.player_score);

            String varName = dataList.get(i).getVarName();
            int varScore = dataList.get(i).getVarScore();

            playerName.setText(varName);
            playerScore.setText(String.valueOf(varScore));

            mTableLayout.addView(tableRow);

        }
    }
}
