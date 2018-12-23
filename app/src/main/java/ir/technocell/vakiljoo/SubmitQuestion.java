package ir.technocell.vakiljoo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import mehdi.sakout.fancybuttons.FancyButton;

public class SubmitQuestion extends AppCompatActivity {


    private Spinner mQGroup;
    private TextView mQuestionTitle, mQuestionText, mQuestionTextCount;
    private Switch mQurstionState;
    private FancyButton mSend;


    private static String INFO_URL = "http://vakiljoo.com/AppData/Questions.php";
    private SharedPreferences userData;
    private SharedPreferences.Editor userDataEdit;
    private RequestQueue RQ;
    private StringRequest request;
    HashMap<String, String> hashMap = new HashMap<>();
    private HashMap<String,String> mapUser;
    View view;
    ImageView mMohasebat,mMap,mChats,mTrhSoal,mHome,mProfile;



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_question);
        Init();

        SetupHeader();
        SetCounterText();
        InitBottomMenu();
        BottomMenuOperations();
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(SubmitQuestion.this).title("ارسال سوال").content("آیا از ازسال سوال مطمن هستید؟")
                        .titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf", "vazir.ttf")
                        .positiveText("بله").negativeText("خیر").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SendQuestion();
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

    }

    private void InitBottomMenu()
    {
        view=findViewById(R.id.mBottomMenuMother);
        //  mMap=view.findViewById(R.id.mMap);
       // mTrhSoal=view.findViewById(R.id.mTrhSoal);
     //   mChats=view.findViewById(R.id.mChats);
     //   mHome=view.findViewById(R.id.mHome);
       // mProfile=view.findViewById(R.id.mProfile);
    }
    private void BottomMenuOperations()
    {
        /*
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Tager:->","yes");
                Intent iGOToActivity=new Intent(SubmitQuestion.this,HqActivity.class);
                startActivity(iGOToActivity);
            }
        });
        mTrhSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(SubmitQuestion.this,"شما اینجا هستید !",Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    private void SetCounterText()
    {
        mQuestionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mQuestionTextCount.setText(charSequence.length()+"/500");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void SetupHeader()
    {

        View HeaderView=findViewById(R.id.mHeaderMother);
        ImageButton
        mHBackBtn=HeaderView.findViewById(R.id.mHBackBtn);
        mHBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BACK = new Intent(SubmitQuestion.this,UserQuestionsActivity.class);
                startActivity(BACK);
            }
        });
    }

    private void Init() {
        View view=findViewById(R.id.mSubmitQuestionMother);
        mQGroup = view.findViewById(R.id.mGroup);
        mQuestionTitle = view.findViewById(R.id.mTitle);
        mQuestionText = view.findViewById(R.id.mQText);
        mQuestionTextCount = view.findViewById(R.id.mTextCounter);
        mQurstionState = view.findViewById(R.id.mIsPublic);
       mSend = view.findViewById(R.id.mMovakelBtn);


        List<String> categories = new ArrayList<String>();
        categories.add("حقوقی");
        categories.add("کیفری");
        categories.add("خانواده");
        categories.add("ثبت احوال");
        categories.add("امور مهاجرت");


        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner, categories);

        mQGroup.setAdapter(categoriesAdapter);


        userData = PreferenceManager.getDefaultSharedPreferences(this);
        userDataEdit = userData.edit();
        RQ= Volley.newRequestQueue(this);



    }

    private void SendQuestion() {
        request = new StringRequest(Request.Method.POST, INFO_URL, new Response.Listener<String>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                if (response.equals("Technocell:Ok")) {
                    Log.e("OnlineTime-->", "Setted");

                    new MaterialDialog.Builder(SubmitQuestion.this).title("ثبت موفق").content("سوال شما با موفقیت ثبت شد")
                    .titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf","vazir.ttf")
                            .positiveText("تایید").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).show();


                } else {
                    new MaterialDialog.Builder(SubmitQuestion.this).title("ثبت ناموفق").content("سوال شما با موفقیت ثبت نشد")
                            .titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf","vazir.ttf")
                            .positiveText("تایید").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("U_ID", GetUserID());
                map.put("Q_RqType", "AddQuestion");
                map.put("Q_UName ", userData.getString("U_Name_Show",""));
                map.put("Q_UFamily ", userData.getString("U_Family_Show",""));
                map.put("Q_Title",  mQuestionTitle.getText().toString());
                map.put("Q_Question", mQuestionText.getText().toString());
                map.put("Q_Group", mQGroup.getSelectedItem().toString());
                map.put("Q_isPublic", IsPublic());
                map.put("Q_isOpen", "true");
                map.put("Q_RqCode", generateRqCode(54639842, 76632547));
                return map;
            }
        };
        RQ.add(request);
    }

    private String GetUserID() {
        return userData.getString("User_ID", "no");
    }

    private String IsPublic()
    {
        final String[] res = {"false"};
        mQurstionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    res[0] ="true";
                }
            }
        });
        return res[0];
    }


    public static String generateRqCode(int min, int max) {
        String finalCode;
        Random r = new Random();
        int requestCode = r.nextInt(max - min + 1) + min;
        finalCode = String.valueOf(requestCode);
        return finalCode;
    }




}
