package com.onlineeducationsyestem;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.EdittextAdapter;
import com.onlineeducationsyestem.adapter.QuestionTypeCheckboxAdapter;
import com.onlineeducationsyestem.adapter.QuestionTypeMatrixOneAdapter;
import com.onlineeducationsyestem.adapter.QuestionTypeMatrixSecondAdapter;
import com.onlineeducationsyestem.adapter.QuestionTypeRadioAdapter;
import com.onlineeducationsyestem.adapter.QuestionTypeSingleSortingAdapter;
import com.onlineeducationsyestem.widget.ItemMoveCallback;
import com.onlineeducationsyestem.widget.ItemMoveCallback1;

import java.util.ArrayList;

public class ExamActivity extends AppCompatActivity {

    private LinearLayout llContent;
    private ImageView imgPrev,imgNext;
    private View inflatedView;
    private RecyclerView rvRadio,rvCheckbox,rvSorting,rvQuestion,rvSortingMtrix;
    private QuestionTypeRadioAdapter questionTypeRadioAdapter;
    private QuestionTypeCheckboxAdapter questionTypeCheckboxAdapter;
    private QuestionTypeSingleSortingAdapter questionTypeSingleSortingAdapter;
    private QuestionTypeMatrixOneAdapter questionTypeMatrixOneAdapter;
    private QuestionTypeMatrixSecondAdapter questionTypeMatrixSecondAdapter;
    private int counter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initUI();

        typeRadio();


    }
    private void typeRadio()
    {
        inflatedView = View.inflate(this, R.layout.question_type_radio, null);
        llContent.addView(inflatedView);
        rvRadio =inflatedView.findViewById(R.id.rvRadio);
        ArrayList<String> list=new ArrayList<>();
        list.add("djfg");
        list.add("djfg");
        list.add("djfg");
        list.add("djfg");
        questionTypeRadioAdapter= new QuestionTypeRadioAdapter(ExamActivity.this, list);
        rvRadio.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(ExamActivity.this);
        rvRadio.setLayoutManager(manager);
        rvRadio.setAdapter(questionTypeRadioAdapter);
    }
    private void typeCheckbox()
    {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_chechkbox, null);
        llContent.addView(inflatedView);
        rvCheckbox =inflatedView.findViewById(R.id.rvCheckbox);
        ArrayList<String> list=new ArrayList<>();
        list.add("djfg");
        list.add("djfg");
        list.add("djfg");
        list.add("djfg");
        questionTypeCheckboxAdapter= new QuestionTypeCheckboxAdapter(ExamActivity.this, list);
        rvCheckbox.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(ExamActivity.this);
        rvCheckbox.setLayoutManager(manager);
        rvCheckbox.setAdapter(questionTypeCheckboxAdapter);
    }

    private void typeFillInTheBlank()
    {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_fill_in_the_blank, null);
        llContent.addView(inflatedView);
        RecyclerView rvEdittext =inflatedView.findViewById(R.id.rvEdittext);
        ArrayList<String> list =new ArrayList<>();
        list.add("adada");
        list.add("dsfd");
        list.add("cdv");

        EdittextAdapter edittextAdapter=new EdittextAdapter(ExamActivity.this,list);
        rvEdittext.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(ExamActivity.this);
        rvEdittext.setLayoutManager(manager);
        rvEdittext.setAdapter(edittextAdapter);
       /* LinearLayout llEdit =inflatedView.findViewById(R.id.llEdit);
        final EditText editText = new EditText(this);
        editText.setHint("Answer 1");
        editText.setSingleLine(true);
        editText.setMaxLines(1);
        ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.black));
        ViewCompat.setBackgroundTintList(editText, colorStateList);
        editText.setTextSize(getResources().getDimensionPixelSize(R.dimen._4ssp));
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (llEdit != null) {
            llEdit.addView(editText);
        }
        final EditText editText1 = new EditText(this);
        editText1.setHint("Answer 2");
        editText1.setSingleLine(true);
        editText1.setMaxLines(1);
        ColorStateList colorStateList1 = ColorStateList.valueOf(getResources().getColor(R.color.black));
        ViewCompat.setBackgroundTintList(editText1, colorStateList1);
        editText1.setTextSize(getResources().getDimensionPixelSize(R.dimen._4ssp));
        editText1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (llEdit != null) {
            llEdit.addView(editText1);
        }*/

    }

    private void typeSingleSorting()
    {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_single_shorting, null);
        llContent.addView(inflatedView);


        ArrayList<String> list =new ArrayList<>();
        list.add("Two");
        list.add("One");
        list.add("Four");
        list.add("Three");

        rvSorting =inflatedView.findViewById(R.id.rvSorting);
        questionTypeSingleSortingAdapter = new QuestionTypeSingleSortingAdapter(list);
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback(questionTypeSingleSortingAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvSorting);
        rvSorting.setAdapter(questionTypeSingleSortingAdapter);
    }

    private void typeMatrix()
    {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_matrix_shorting, null);
        llContent.addView(inflatedView);


        ArrayList<String> list1=new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");

        ArrayList<String> list =new ArrayList<>();
        list.add("Two");
        list.add("One");
        list.add("Four");
        list.add("Three");

        rvQuestion =inflatedView.findViewById(R.id.rvQuestion);
        questionTypeMatrixOneAdapter = new QuestionTypeMatrixOneAdapter(ExamActivity.this,list1);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(ExamActivity.this);
        rvQuestion.setLayoutManager(mLayoutManager);
        rvQuestion.setItemAnimator(new DefaultItemAnimator());
        rvQuestion.setAdapter(questionTypeMatrixOneAdapter);


        rvSortingMtrix =inflatedView.findViewById(R.id.rvSortingMtrix);
        questionTypeMatrixSecondAdapter = new QuestionTypeMatrixSecondAdapter(list);
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback1(questionTypeMatrixSecondAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvSortingMtrix);
        rvSortingMtrix.setAdapter(questionTypeMatrixSecondAdapter);
    }

    private void typeTrueFalse()
    {

    }

    private void initUI()
    {
        llContent =findViewById(R.id.llContent);
        imgPrev =findViewById(R.id.imgPrev);
        imgNext=findViewById(R.id.imgNext);


        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(counter != 0)
                {
                    llContent.removeView(inflatedView);
                    counter --;
                }
                if(counter == 0) {
                    typeRadio();
                }else if(counter ==1) {

                    typeCheckbox();
                }else if(counter == 2)
                {
                    typeFillInTheBlank();
                }else if(counter == 3)
                {
                    typeSingleSorting();
                }else if(counter == 4)
                {
                    typeMatrix();
                }

            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                counter++;
                llContent.removeView(inflatedView);
                if(counter ==1) {

                    typeCheckbox();
                }else if(counter == 2)
                {
                    typeFillInTheBlank();
                }else if(counter == 3)
                {
                    typeSingleSorting();
                }else if(counter == 4)
                {
                    typeMatrix();
                }
            }
        });
    }

}
