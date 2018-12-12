package ir.technocell.vakiljoo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
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
import com.isapanah.awesomespinner.AwesomeSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import ir.technocell.vakiljoo.Activity.HqActivity;
import ir.technocell.vakiljoo.RecyclerItems.TopVakilsItem;

public class SubmitQuestion extends AppCompatActivity {


    private AwesomeSpinner mQGroup;
    private TextView mQuestionTitle, mQuestionText, mQuestionTextCount;
    private Switch mQurstionState;
    private Button mSend;


    private static String INFO_URL = "http://vakiljoo.com/AppData/Questions.php";
    private SharedPreferences userData;
    private SharedPreferences.Editor userDataEdit;
    private RequestQueue RQ;
    private StringRequest request;
    HashMap<String, String> hashMap = new HashMap<>();
    private HashMap<String,String> mapUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_question);
        GetDaMapData();
        Init();
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

    private void Init() {
        mQGroup = findViewById(R.id.mQGroup);
        mQuestionTitle = findViewById(R.id.aq);
        mQuestionText = findViewById(R.id.cq);
        mQuestionTextCount = findViewById(R.id.vq);
        mQurstionState = findViewById(R.id.simpleswitch);
       mSend = findViewById(R.id.mMovakelBtn);


        /*ArrayAdapter<CharSequence> provincesAdapter = ArrayAdapter.createFromResource(this,
        R.array.spinnerItems, android.R.layout.simple_spinner_item);

        mQGroup.setAdapter(provincesAdapter, 0);*/
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Ariplane");
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_list_item, categories);
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
                map.put("Q_UName ", hashMap.get("U_Name_Show"));
                map.put("Q_UFamily ", hashMap.get("U_Family_Show"));
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

    private HashMap GetDaMapData()
    {
        try {

            Intent intent = getIntent();
            hashMap  = (HashMap<String, String>) intent.getSerializableExtra("mapUser");
            Log.v("HashMapTest", hashMap.get("U_Name_Show"));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return hashMap;
    }

    public static String generateRqCode(int min, int max) {
        String finalCode;
        Random r = new Random();
        int requestCode = r.nextInt(max - min + 1) + min;
        finalCode = String.valueOf(requestCode);
        return finalCode;
    }

    private void SetOnlineTime()
    {
        request=new StringRequest(Request.Method.POST, INFO_URL, new Response.Listener<String>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                if(response.equals("Technocell:Ok"))
                {
                    Log.e("OnlineTime-->","Setted");
                   // GoToNext(Objects.requireNonNull(mapUser.get("U_Type")));
                }else {
                    Log.e("OnlineTime-->","NotSetted");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("T_RqType","UpdateTime");
                map.put("T_RqCode",generateRqCode(28639842,44632547));
                map.put("U_ID",GetUserID());
                return map;
            }
        };
        RQ.add(request);
    }



}
