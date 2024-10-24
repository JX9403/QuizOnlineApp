package com.example.onlinequiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.onlinequiz.databinding.ActivityScoreBinding;

import java.util.concurrent.TimeUnit;

public class ScoreActivity extends AppCompatActivity {

    ActivityScoreBinding binding;
    private long timeTaken;
    private int totalQuestions, correctAnsw, wrongAnsw, skipQuestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        totalQuestions = getIntent().getIntExtra("total_question", 0);
        correctAnsw = getIntent().getIntExtra("correct", 0);
        wrongAnsw = getIntent().getIntExtra("wrong", 0);

        String remainingTime = String.format("%02d:%02d min",
                TimeUnit.MICROSECONDS.toMinutes(timeTaken),
                TimeUnit.MICROSECONDS.toSeconds(timeTaken) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MICROSECONDS.toMinutes(timeTaken)));

        skipQuestions = totalQuestions - (correctAnsw+wrongAnsw);

        binding.timeTaken.setText(remainingTime);
        binding.question.setText(totalQuestions+"");
        binding.txtCorrect.setText(correctAnsw+"");
        binding.txtWrong.setText(wrongAnsw+"");
        binding.txtSkip.setText(skipQuestions+"");
        binding.score.setText(correctAnsw + "");


        binding.btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        binding.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}