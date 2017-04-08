package com.irislu.administrator.my2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;
    private TextView tvScore;
    private int score;
    public MainActivity() {
        mainActivity = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = (TextView) findViewById(R.id.tvscore);
        gameView = (GameView) findViewById(R.id.gameView);
        Button startGameBT = (Button) findViewById(R.id.startGameBT);
        Button endGameBT = (Button) findViewById(R.id.endGameBT);
        startGameBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gameView.startGame();
               //实现不了重新开始

            }
        });
        endGameBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
    }

    private static MainActivity mainActivity = null;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public void clearScore() {
        score = 0;
        showScore();
    }
    public void showScore() {
        tvScore.setText(score + "");
    }
    public void addScore(int s){
        score += s;
        showScore();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
