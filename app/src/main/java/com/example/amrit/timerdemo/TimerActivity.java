package com.example.amrit.timerdemo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

import me.zhanghai.android.materialprogressbar.CircularProgressDrawable;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class TimerActivity extends AppCompatActivity {

    public enum TimerState{
        Stopped, Running, Paused
    }

    private long TIME_CONSTANT = 100;

    private long timerLengthInSeconds = 0;
    private long timerLengthInMinutes = 0;
    private TimerState timerState = TimerState.Stopped;
    private CountDownTimer timer;

    private  long secondsRemaining = TIME_CONSTANT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.drawable.ic_timer);
        getSupportActionBar().setTitle("    Timer");

        setupPauseButton();
        setupPlayButton();
        setupStopButton();
    }

    private void setupPauseButton() {
        FloatingActionButton pauseBtn = (FloatingActionButton) findViewById(R.id.pause);
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerState != TimerState.Paused) {
                    timerState = TimerState.Paused;
                    timer.cancel();
                }
            }
        });
    }

    private void setupPlayButton() {
        FloatingActionButton playBtn = (FloatingActionButton) findViewById(R.id.play);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
    }

    private void setupStopButton() {
        FloatingActionButton pauseBtn = (FloatingActionButton) findViewById(R.id.stop);
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerState != TimerState.Stopped) {
                    onTimerFinished();
                }
            }
        });
    }

    private void startTimer() {

        MaterialProgressBar materialProgressBar = (MaterialProgressBar) findViewById(R.id.progressBar);
        materialProgressBar.setMax((int) secondsRemaining);

        if (timerState != TimerState.Running) {
            timer = new CountDownTimer(secondsRemaining * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    secondsRemaining = millisUntilFinished / 1000;
                    upDateUI();
                }

                @Override
                public void onFinish() {
                    onTimerFinished();
                }
            }.start();

            timerState = TimerState.Running;
        }
    }

    private void onTimerFinished() {
        timerState = TimerState.Stopped;
        timer.cancel();
        secondsRemaining = TIME_CONSTANT;
        upDateUI();
        MaterialProgressBar materialProgressBar = (MaterialProgressBar) findViewById(R.id.progressBar);
        materialProgressBar.setProgress(0);
    }

    private void upDateUI() {
        int minutesUntilFinished = (int) secondsRemaining / 60;
        int secondsInMinuteUntilFinished = (int) secondsRemaining - minutesUntilFinished * 60;
        String secondsStr = "" + secondsInMinuteUntilFinished;
        if (secondsInMinuteUntilFinished <= 9){
            secondsStr = "0" + secondsStr;
        }

        TextView timerText = (TextView) findViewById(R.id.timer);
        timerText.setText(minutesUntilFinished + ":" + secondsStr);

        MaterialProgressBar materialProgressBar = (MaterialProgressBar) findViewById(R.id.progressBar);
        int progress = (int) (TIME_CONSTANT - secondsRemaining);
        Log.i("PROGRESS", progress + "");
        materialProgressBar.setProgress(progress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timer, menu);
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
