package ir.technocell.vakiljoo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.technocell.vakiljoo.Activity.HqActivity;
import ir.technocell.vakiljoo.RecyclerAdapters.MyQuestionAdapter;
import ir.technocell.vakiljoo.RecyclerAdapters.TopVakilsRecyclerListener;
import ir.technocell.vakiljoo.RecyclerItems.MySoalsItem;
import mehdi.sakout.fancybuttons.FancyButton;

public class UserQuestionsActivity extends AppCompatActivity {


    private RecyclerView mMySoalsRecycler;
    private List<MySoalsItem> mySoalsItems=new ArrayList<>();
    private MyQuestionAdapter myQuestionAdapter;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private static final String QUESTION_URL="http://vakiljoo.com/AppData/Questions.php";
    private SharedPreferences userData;
    private SharedPreferences.Editor userDataEdit;
    private View HeaderView;
    private ImageButton mHBackBtn;
    private FancyButton mNewQuestionBtn;

    View view;
    ImageView mMohasebat,mMap,mChats,mTrhSoal,mHome,mProfile;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_questions);
        InitUseQuestions();
        GetQuestions();
        SetupHeader();
        InitBottomMenu();
        BottomMenuOperations();
    }

    private void SetupHeader()
    {
        HeaderView=findViewById(R.id.mHeaderMother);
        mHBackBtn=HeaderView.findViewById(R.id.mHBackBtn);
        mHBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BACK = new Intent(UserQuestionsActivity.this,HqActivity.class);
                startActivity(BACK);
            }
        });
    }
    private void InitBottomMenu()
    {
        view=findViewById(R.id.mBottomMenuMother);
        mTrhSoal=view.findViewById(R.id.mTrhSoal);
        mMohasebat=view.findViewById(R.id.mMohasebat);
        mMap=view.findViewById(R.id.mMap);
        mChats=view.findViewById(R.id.mChats);
        mTrhSoal=view.findViewById(R.id.mTrhSoal);
        mHome=view.findViewById(R.id.mHome);
    }
    private void BottomMenuOperations()
    {
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Tager:->","yes");
                Intent iGOToActivity=new Intent(UserQuestionsActivity.this,HqActivity.class);
                startActivity(iGOToActivity);
            }
        });
        mTrhSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(UserQuestionsActivity.this,"شما اینجا هستید !",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void GetQuestions() {

        stringRequest = new StringRequest(Request.Method.POST, QUESTION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Log.e("Response of my Soals-->",response.toString());
                    JSONArray jsonArray=new JSONArray(response.toString());
                    for(int c=0;c<jsonArray.length();c++)
                    {
                        JSONObject object=jsonArray.getJSONObject(c);
                        MySoalsItem mySoalsItem=new MySoalsItem(object.getString("Q_Title")
                                ,object.getString("Q_Question"),object.getString("Q_Group"),object.getString("Q_Date"),
                                "http://vakiljoo.com/AppData/Core/ProfilePics/"+GetUserID()+".jpeg");
                        mySoalsItems.add(mySoalsItem);
                    }
                    myQuestionAdapter.notifyDataSetChanged();
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Q_RqCode", generateRqCode(54639842, 76632547));
                map.put("Q_RqType", "GetMyQuestions");
                map.put("U_ID", GetUserID().toString());
                return map;
            }


        };
        requestQueue.add(stringRequest);
    }

    private String GetUserID()
    {
        return   userData.getString("User_ID","no");
    }

    private String generateRqCode(int min, int max) {
        String finalCode;
        Random r = new Random();
        int requestCode = r.nextInt(max - min + 1) + min;
        finalCode = String.valueOf(requestCode);
        return finalCode;
    }


    private void InitUseQuestions()
    {
        userData=PreferenceManager.getDefaultSharedPreferences(this);
        userDataEdit=userData.edit();
        requestQueue=Volley.newRequestQueue(this);
        mMySoalsRecycler=findViewById(R.id.mMySoalsRecycler);
        myQuestionAdapter=new MyQuestionAdapter(mySoalsItems);
        mMySoalsRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mMySoalsRecycler.setLayoutManager(mLayoutManager);
        mMySoalsRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mMySoalsRecycler.setItemAnimator(new DefaultItemAnimator());
        mMySoalsRecycler.setAdapter(myQuestionAdapter);

        mMySoalsRecycler.addOnItemTouchListener(new TopVakilsRecyclerListener(this, mMySoalsRecycler, new TopVakilsRecyclerListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                MySoalsItem soals = mySoalsItems.get(position);

                Log.e("This is Top man-->",soals.getText());

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        mNewQuestionBtn=findViewById(R.id.mNewQuestionBtn);
        mNewQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent submitq = new Intent(UserQuestionsActivity.this,SubmitQuestion.class);
                startActivity(submitq);
            }
        });
    }

}
