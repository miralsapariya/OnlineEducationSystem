package com.onlineeducationsyestem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.EdittextAdapter;
import com.onlineeducationsyestem.adapter.QuestionTypeCheckboxAdapter;
import com.onlineeducationsyestem.adapter.QuestionTypeMatrixImgOneAdapter;
import com.onlineeducationsyestem.adapter.QuestionTypeMatrixImgSecondAdapter;
import com.onlineeducationsyestem.adapter.QuestionTypeMatrixOneAdapter;
import com.onlineeducationsyestem.adapter.QuestionTypeMatrixSecondAdapter;
import com.onlineeducationsyestem.adapter.QuestionTypeRadioAdapter;
import com.onlineeducationsyestem.adapter.QuestionTypeSingleSortingAdapter;
import com.onlineeducationsyestem.interfaces.NetworkListener;
import com.onlineeducationsyestem.model.Exam;
import com.onlineeducationsyestem.network.ApiCall;
import com.onlineeducationsyestem.network.ApiInterface;
import com.onlineeducationsyestem.network.RestApi;
import com.onlineeducationsyestem.network.ServerConstents;
import com.onlineeducationsyestem.util.AppConstant;
import com.onlineeducationsyestem.util.AppSharedPreference;
import com.onlineeducationsyestem.util.AppUtils;
import com.onlineeducationsyestem.widget.ItemMoveCallback;
import com.onlineeducationsyestem.widget.ItemMoveCallback1;
import com.onlineeducationsyestem.widget.ItemMoveCallback2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

public class ExamActivity extends BaseActivity implements NetworkListener {

    private LinearLayout llContent;
    private ImageView imgPrev, imgNext;
    private View inflatedView;
    private RecyclerView rvRadio, rvCheckbox, rvSorting, rvQuestion, rvSortingMtrix;
    private QuestionTypeRadioAdapter questionTypeRadioAdapter;
    private QuestionTypeCheckboxAdapter questionTypeCheckboxAdapter;
    private QuestionTypeSingleSortingAdapter questionTypeSingleSortingAdapter;
    private QuestionTypeMatrixOneAdapter questionTypeMatrixOneAdapter;
    private QuestionTypeMatrixSecondAdapter questionTypeMatrixSecondAdapter;
    private QuestionTypeMatrixImgOneAdapter questionTypeMatrixImgOneAdapter;
    private QuestionTypeMatrixImgSecondAdapter questionTypeMatrixImgSecondAdapter;
    private int counter = 0;
    private String course_id = "";
    private Exam data;
    private ImageView imgBack;
    private TextView tvOrder, tvCourse, tvTestName;
    private RecyclerView rvEdittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
    }

    private void typeRadio(ArrayList<Exam.Option> options) {
        inflatedView = View.inflate(this, R.layout.question_type_radio, null);
        llContent.addView(inflatedView);
        rvRadio = inflatedView.findViewById(R.id.rvRadio);

        questionTypeRadioAdapter = new QuestionTypeRadioAdapter(ExamActivity.this, options);
        rvRadio.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(ExamActivity.this);
        rvRadio.setLayoutManager(manager);
        rvRadio.setAdapter(questionTypeRadioAdapter);
    }

    private void typeCheckbox(ArrayList<Exam.Option> list) {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_chechkbox, null);
        llContent.addView(inflatedView);
        rvCheckbox = inflatedView.findViewById(R.id.rvCheckbox);

        questionTypeCheckboxAdapter = new QuestionTypeCheckboxAdapter(ExamActivity.this, list);
        rvCheckbox.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(ExamActivity.this);
        rvCheckbox.setLayoutManager(manager);
        rvCheckbox.setAdapter(questionTypeCheckboxAdapter);
    }

    private void typeFillInTheBlank(ArrayList<Exam.Option> list, String question) {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_fill_in_the_blank, null);
        llContent.addView(inflatedView);
        rvEdittext = inflatedView.findViewById(R.id.rvEdittext);

        TextView tvQuestion = inflatedView.findViewById(R.id.tvQuestion);
        tvQuestion.setText(question);
        TextView tvFillInTheBlank = inflatedView.findViewById(R.id.tvFillInTheBlank);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getOption());
            sb.append("________");
        }
        String str = sb.toString();
        tvFillInTheBlank.setText(str);
        EdittextAdapter edittextAdapter = new EdittextAdapter(ExamActivity.this, list);
        rvEdittext.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(ExamActivity.this);
        rvEdittext.setLayoutManager(manager);
        rvEdittext.setAdapter(edittextAdapter);

    }

    private void typeSingleSorting(ArrayList<Exam.Option> list, String question) {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_single_shorting, null);
        llContent.addView(inflatedView);

        rvSorting = inflatedView.findViewById(R.id.rvSorting);
        TextView tvQuestion = inflatedView.findViewById(R.id.tvQuestion);
        tvQuestion.setText(question);
        questionTypeSingleSortingAdapter = new QuestionTypeSingleSortingAdapter(list);
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback(questionTypeSingleSortingAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvSorting);
        rvSorting.setAdapter(questionTypeSingleSortingAdapter);
    }

    private void typeImgMatrix(ArrayList<Exam.Option> list, String question) {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_matrix_shorting, null);
        llContent.addView(inflatedView);
        rvQuestion = inflatedView.findViewById(R.id.rvQuestion);


        questionTypeMatrixImgOneAdapter = new QuestionTypeMatrixImgOneAdapter(ExamActivity.this, list);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(ExamActivity.this);
        rvQuestion.setLayoutManager(mLayoutManager);
        rvQuestion.setItemAnimator(new DefaultItemAnimator());
        rvQuestion.setAdapter(questionTypeMatrixImgOneAdapter);

        rvSortingMtrix = inflatedView.findViewById(R.id.rvSortingMtrix);
        questionTypeMatrixImgSecondAdapter = new QuestionTypeMatrixImgSecondAdapter(list);
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback2(questionTypeMatrixImgSecondAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvSortingMtrix);
        rvSortingMtrix.setAdapter(questionTypeMatrixImgSecondAdapter);
    }

    private void typeMatrix(ArrayList<Exam.Option> list, String question) {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_matrix_shorting, null);
        llContent.addView(inflatedView);

        rvQuestion = inflatedView.findViewById(R.id.rvQuestion);
        TextView tvQuestion = inflatedView.findViewById(R.id.tvQuestion);
        tvQuestion.setText(question);

        questionTypeMatrixOneAdapter = new QuestionTypeMatrixOneAdapter(ExamActivity.this, list);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(ExamActivity.this);
        rvQuestion.setLayoutManager(mLayoutManager);
        rvQuestion.setItemAnimator(new DefaultItemAnimator());
        rvQuestion.setAdapter(questionTypeMatrixOneAdapter);

        rvSortingMtrix = inflatedView.findViewById(R.id.rvSortingMtrix);
        questionTypeMatrixSecondAdapter = new QuestionTypeMatrixSecondAdapter(list);
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback1(questionTypeMatrixSecondAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvSortingMtrix);
        rvSortingMtrix.setAdapter(questionTypeMatrixSecondAdapter);
    }

    private void typeTrueFalse(ArrayList<Exam.Option> list, String question) {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_true_false, null);
        llContent.addView(inflatedView);
        rvRadio = inflatedView.findViewById(R.id.rvRadio);
        TextView tvQuestion = inflatedView.findViewById(R.id.tvQuestion);
        tvQuestion.setText(question + "");
        questionTypeRadioAdapter = new QuestionTypeRadioAdapter(ExamActivity.this, list);
        rvRadio.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(ExamActivity.this);
        rvRadio.setLayoutManager(manager);
        rvRadio.setAdapter(questionTypeRadioAdapter);

    }

    private void initUI() {
        course_id = getIntent().getExtras().getString("course_id");
        llContent = findViewById(R.id.llContent);
        imgPrev = findViewById(R.id.imgPrev);
        imgNext = findViewById(R.id.imgNext);
        tvOrder = findViewById(R.id.tvOrder);
        imgBack = findViewById(R.id.imgBack);
        tvTestName = findViewById(R.id.tvTestName);
        tvCourse = findViewById(R.id.tvCourse);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (AppUtils.isInternetAvailable(ExamActivity.this)) {
            callQuizStart();
        }

        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPrevious("previous");
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPrevious("next");
            }
        });
    }

    private void nextPrevious(String from) {
        llContent.removeView(inflatedView);
        JSONArray jsonArray = new JSONArray();

        if (data.getData().get(0).getQueType() == AppConstant.SINGLE_CHOICE) {
            try {
                for (int i = 0; i < data.getData().get(0).getOptions().size(); i++) {
                    JSONObject jobj = new JSONObject();
                    if (data.getData().get(0).getOptions().get(i).isSelected() == true) {
                        jobj.put(data.getData().get(0).getOptions().get(i).getKey() + "", data.getData().get(0).getOptions().get(i).getOption());
                        jsonArray.put(jobj);
                    }
                }
                Log.d("OPTION ARRAY :: ", jsonArray.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (data.getData().get(0).getQueType() == AppConstant.MULTIPLE_CHOICE) {
            try {
                for (int i = 0; i < data.getData().get(0).getOptions().size(); i++) {
                    JSONObject jobj = new JSONObject();
                    if (data.getData().get(0).getOptions().get(i).isSelected() == true) {
                        jobj.put(data.getData().get(0).getOptions().get(i).getKey() + "", data.getData().get(0).getOptions().get(i).getOption());
                        jsonArray.put(jobj);
                    }
                }
                Log.d("OPTION ARRAY :: ", jsonArray.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (data.getData().get(0).getQueType() == AppConstant.FILL_IN_THE_BLANK) {
            try {
                for (int i = 0; i < data.getData().get(0).getOptions().size(); i++) {
                    View view1 = rvEdittext.getChildAt(i);
                    EditText editText = view1.findViewById(R.id.edittext);
                    String string = editText.getText().toString();
                    String key = i + "";
                    JSONObject jobj = new JSONObject();
                    jobj.put(key, string);
                    jsonArray.put(jobj);

                    Log.d("fill in  ARRAY :: ", jsonArray.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (data.getData().get(0).getQueType() == AppConstant.SHORTING) {
            try {
                for (int i = 0; i < data.getData().get(0).getOptions().size(); i++) {
                    JSONObject jobj = new JSONObject();
                    jobj.put(data.getData().get(0).getOptions().get(i).getKey() + "", data.getData().get(0).getOptions().get(i).getOption());
                    jsonArray.put(jobj);

                }
                Log.d("OPTION ARRAY :: ", jsonArray.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (data.getData().get(0).getQueType() == AppConstant.STATEMENT) {
            try {
                for (int i = 0; i < data.getData().get(0).getOptions().size(); i++) {
                    JSONObject jobj = new JSONObject();
                    jobj.put(data.getData().get(0).getOptions().get(i).getKey() + "", data.getData().get(0).getOptions().get(i).getOption());
                    jsonArray.put(jobj);
                }
                Log.d("OPTION ARRAY :: ", jsonArray.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (data.getData().get(0).getQueType() == AppConstant.MATRIX) {

            try {
                for (int i = 0; i < data.getData().get(0).getOptions().size(); i++) {
                    JSONObject jobj = new JSONObject();
                    jobj.put(data.getData().get(0).getOptions().get(i).getKey() + "", data.getData().get(0).getOptions().get(i).getOption());
                    jsonArray.put(jobj);
                }
                Log.d("OPTION ARRAY :: ", jsonArray.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        if (from.equalsIgnoreCase("next"))
            callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() + 1);
        else
            callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() - 1);

    }

    private void callNextApi(String jsonObject, int navigation) {
        String lang = "";
        AppUtils.showDialog(ExamActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);
        params.put("question_id", data.getData().get(0).getQueId() + "");
        params.put("order", data.getData().get(0).getOrder() + "");
        params.put("navigation", navigation + "");
        params.put("answer", jsonObject);

        if (AppSharedPreference.getInstance().getString(ExamActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(ExamActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<Exam> call = apiInterface.continueQuiz(lang, AppSharedPreference.getInstance().
                getString(ExamActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(ExamActivity.this, call, this, ServerConstents.EXAM);

    }

    private void callQuizStart() {
        String lang = "";
        AppUtils.showDialog(ExamActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);
        if (AppSharedPreference.getInstance().getString(ExamActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(ExamActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<Exam> call = apiInterface.startQuizeApi(lang, AppSharedPreference.getInstance().
                getString(ExamActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(ExamActivity.this, call, this, ServerConstents.QUIZ_START);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if (requestCode == ServerConstents.EXAM) {
            data = (Exam) response;
            Log.d("response Examp :: ", data.getStatus() + "");
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                if (data.getData().get(0).getQuiz_over().equalsIgnoreCase("no")) {
                    tvCourse.setText(data.getData().get(0).getCourseName());
                    tvTestName.setText("Test");
                    tvOrder.setText(data.getData().get(0).getOrder() + "");
                    if (data.getData().get(0).getQueType() == AppConstant.SINGLE_CHOICE) {
                        typeRadio(data.getData().get(0).getOptions());
                    } else if (data.getData().get(0).getQueType() == AppConstant.MULTIPLE_CHOICE) {
                        typeCheckbox(data.getData().get(0).getOptions());
                    } else if (data.getData().get(0).getQueType() == AppConstant.MATRIX) {
                        if (data.getData().get(0).getSub_type() == 3) {
                            typeMatrix(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                        } else {
                            typeImgMatrix(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                        }
                    } else if (data.getData().get(0).getQueType() == AppConstant.FILL_IN_THE_BLANK) {
                        typeFillInTheBlank(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                    } else if (data.getData().get(0).getQueType() == AppConstant.SHORTING) {
                        typeSingleSorting(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                    } else if (data.getData().get(0).getQueType() == AppConstant.STATEMENT) {
                        typeTrueFalse(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                    }
                } else {
                    Intent intent =new Intent(ExamActivity.this,ReportActivity.class);
                    startActivity(intent);
                }
            }
        } else if (requestCode == ServerConstents.QUIZ_START) {
            data = (Exam) response;
            Log.d("response Examp :: ", data.getStatus() + "");
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                tvCourse.setText(data.getData().get(0).getCourseName());
                tvTestName.setText("Test");
                tvOrder.setText(data.getData().get(0).getOrder() + "");
                if (data.getData().get(0).getQueType() == AppConstant.SINGLE_CHOICE) {
                    typeRadio(data.getData().get(0).getOptions());
                } else if (data.getData().get(0).getQueType() == AppConstant.MULTIPLE_CHOICE) {
                    typeCheckbox(data.getData().get(0).getOptions());
                } else if (data.getData().get(0).getQueType() == AppConstant.MATRIX) {
                    if (data.getData().get(0).getSub_type() == 3) {
                        typeMatrix(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                    } else {
                        typeImgMatrix(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                    }
                } else if (data.getData().get(0).getQueType() == AppConstant.FILL_IN_THE_BLANK) {
                    typeFillInTheBlank(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                } else if (data.getData().get(0).getQueType() == AppConstant.SHORTING) {
                    typeSingleSorting(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                } else if (data.getData().get(0).getQueType() == AppConstant.STATEMENT) {
                    typeTrueFalse(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                }
            } else {
                Intent intent =new Intent(ExamActivity.this,ReportActivity.class);
                startActivity(intent);
            }

        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {
    }
}
