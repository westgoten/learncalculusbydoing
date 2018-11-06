package com.daedalusacademy.learncalculusbydoing;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import io.github.kexanie.library.MathView;

import java.lang.reflect.Type;

public class MultipleAnswerQuestion implements Question {
    private static final int numberOfOptions = 4;
    private static int numberOfQuestions = 0;

    private int questionNumber;
    private Activity activity;
    private static MathView questionTitle;
    private static MathView[] questionOptions = new MathView[numberOfOptions];
    private static CheckBox[] questionButtons = new CheckBox[numberOfOptions];
    private boolean[] answer;

    private static final String TAG = "MultipleAnswerQuestion";

    public MultipleAnswerQuestion(Context context, boolean[] answer) {
        this.activity = (Activity) context;
        this.answer = answer;

        this.questionNumber = numberOfQuestions++;

        if (this.questionNumber == 0) {
            initializeViews();
        }
    }

    @Override
    public void setViewsText() {
        Resources resources = this.activity.getResources();
        TypedArray MAQArray = resources.obtainTypedArray(R.array.MAQ_array1);
        int MAQStringArrayId = MAQArray.getResourceId(this.questionNumber, 0);

        String[] MAQStringArray;
        if (MAQStringArrayId > 0) {
            MAQStringArray = resources.getStringArray(MAQStringArrayId);

            questionTitle.setText(MAQStringArray[0]);

            for (int i = 0; i < numberOfOptions; i++)
                questionOptions[i].setText(MAQStringArray[i+1]);
        } else {
            Log.v(TAG, "MAQStringArrayId doesn't exist");
        }

        MAQArray.recycle();
    }

    @Override
    public boolean isAnswerCorrect() {
        for (int i = 0; i < numberOfOptions; i++)
            if (questionButtons[i].isChecked() != this.answer[i])
                return false;
        return true;
    }

    @Override
    public void highlightAnswer() {
        for (int i = 0; i < numberOfOptions; i++) {
            if (questionButtons[i].isChecked() != this.answer[i]) {
                ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.wrongAnswer);
            } else {
                ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.correctAnswer);
            }

            questionButtons[i].setClickable(false);
        }
    }

    @Override
    public boolean hasUserInput() {
        for (int i = 0; i < numberOfOptions; i++) {
            if (questionButtons[i].isChecked())
                return true;
        }
        return false;
    }

    @Override
    public void setInputViewsVisibility(boolean isVisible) {
        for (int i = 0; i < numberOfOptions; i++) {
            if (isVisible)
                questionButtons[i].setVisibility(View.VISIBLE);
            else
                questionButtons[i].setVisibility(View.GONE);
        }

        LinearLayout parent = (LinearLayout) questionButtons[0].getParent().getParent();
        if (isVisible)
            parent.setVisibility(View.VISIBLE);
        else
            parent.setVisibility(View.GONE);
    }

    @Override
    public void resetInputViewsState(){
        for (int i = 0; i < numberOfOptions; i++) {
            if (questionButtons[i].isChecked())
                questionButtons[i].setChecked(false);

            ((LinearLayout) questionButtons[i].getParent()).setBackgroundResource(R.color.noAnswer);
            questionButtons[i].setClickable(true);
        }
    }

    public static void resetNumberOfQuestions() {
        numberOfQuestions = 0;
    }

    private void initializeViews() {
        questionTitle = this.activity.findViewById(R.id.question_title);

        Resources resources = this.activity.getResources();
        TypedArray optionsArray = resources.obtainTypedArray(R.array.options_views_IDs);
        TypedArray checkBoxesArray = resources.obtainTypedArray(R.array.checkBoxes_IDs);

        for (int i = 0; i < numberOfOptions; i++) {
            int optionId = optionsArray.getResourceId(i, 0);
            int checkBoxId = checkBoxesArray.getResourceId(i, 0);

            if (optionId != 0)
                questionOptions[i] = this.activity.findViewById(optionId);
            else
                Log.v(TAG, "optionId doesn't exist");

            if (checkBoxId != 0)
                questionButtons[i] = this.activity.findViewById(checkBoxId);
            else
                Log.v(TAG, "checkBoxId doesn't exist");
        }

        optionsArray.recycle();
        checkBoxesArray.recycle();
    }

    public Activity getActivity() { return activity; }

    public MathView getQuestionTitle() {
        return questionTitle;
    }

    public MathView[] getQuestionOptions() {
        return questionOptions;
    }

    public CheckBox[] getQuestionButtons() {
        return questionButtons;
    }

    public boolean[] getAnswer() {
        return answer;
    }

    public static int getNumberOfOptions() {
        return numberOfOptions;
    }
}
