package com.onlineeducationsyestem.model;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FillInTheBlank {

    public static ArrayList<EditText> createFillInTheBlanks(Context context, LinearLayout linearl, ArrayList<FillObject> map) {

        ArrayList<EditText> editTextArrayList = new ArrayList<>();
        int lineNumber = 0;
        LinearLayout linearLayout = null;
        for (FillObject fillObject : map) {

            if (fillObject.getLineNumber() != lineNumber) {
                lineNumber = fillObject.getLineNumber();
                linearLayout = new LinearLayout(context);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearl.addView(linearLayout);
            }
            if (fillObject.getTextView()) {
                TextView textView = new TextView(context);
                textView.setText(fillObject.getText());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(16, 0, 16, 16);
                textView.setLayoutParams(layoutParams);
                textView.setTextSize(20f);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                linearLayout.addView(textView);

            } else {
                EditText editText = new EditText(context);
                editText.setHint(fillObject.getText());
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams1.setMargins(16, 0, 16, 16);
                editText.setLayoutParams(layoutParams1);
                editText.setGravity(Gravity.CENTER_VERTICAL);
                linearLayout.addView(editText);
                editTextArrayList.add(editText);
            }
        }
        return editTextArrayList;
    }


}
