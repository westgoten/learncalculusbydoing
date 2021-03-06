package com.westgoten.learncalculusbydoing;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class QuizResultActivity extends AppCompatActivity {
    private static final String EXTRA_SCORE = "com.westgoten.learncalculusbydoing.EXTRA_SCORE";

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        Intent resultIntent = getIntent();
        int score = resultIntent.getIntExtra(EXTRA_SCORE, 0);

        TextView scoreView = findViewById(R.id.result_score_text_view);
        SpannableString string = new SpannableString(getString(R.string.score, score, QuizActivity
                .getQuestionsTotal()));
        string.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primaryColor)), 0, 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        scoreView.setText(string);

        Button button = findViewById(R.id.result_to_menu_button);
        button.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setClassName("com.westgoten.learncalculusbydoing",
                        "com.westgoten.learncalculusbydoing.MenuActivity");
                startActivity(intent);
                finish();
            });
    }
}
