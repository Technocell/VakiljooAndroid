package ir.technocell.vakiljoo.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.technocell.vakiljoo.HqClient;
import ir.technocell.vakiljoo.R;

public class MatnSoal extends AppCompatActivity {


    Bundle bundle;
    RequestQueue RQ;
    TextView mTitle,mQText;
    StringRequest stringRequest;
    private static final String Q_URL="http://vakiljoo.com/AppData/Questions.php";
    MaterialDialog dialog;


    
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matn_soal);
        Init();
        SetupHeader();
        GetQuestions(GetId());
    }

    public static String generateRqCode(int min, int max) {
        String finalCode;
        Random r = new Random();
        int requestCode = r.nextInt(max - min + 1) + min;
        finalCode = String.valueOf(requestCode);
        return finalCode;
    }

    private void Init()
    {
        RQ=Volley.newRequestQueue(this);
        mTitle=findViewById(R.id.mTitle);
        mQText=findViewById(R.id.mQText);


    }
    private String GetId()
    {
        String message = null;
        try {
            bundle = getIntent().getExtras();
            message =bundle.getString("QID");
        }catch (Exception e)
        {

        }
        return message;
    }
    private void SetupHeader()
    {

        View HeaderView=findViewById(R.id.mHeaderMother);
        ImageButton
                mHBackBtn=HeaderView.findViewById(R.id.mHBackBtn);
        TextView
                mHeaderTitle=HeaderView.findViewById(R.id.mHeaderTitle);
        mHeaderTitle.setText("سوال کاربر");
        mHBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BACK = new Intent(MatnSoal.this,HqClient.class);
                startActivity(BACK);
            }
        });
    }

    private void GetQuestions(final String QID)
    {
        dialog = new MaterialDialog.Builder(MatnSoal.this).title("لطفا صبر کنید").content("درحال آنالیز اطلاعات").progress(true, 0).titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf","vazir.ttf").show();

        stringRequest=new StringRequest(Request.Method.POST, Q_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                if(!response.equals("-400"))
                {
                    try{
                        JSONArray jsonArray=new JSONArray(response.toString());
                        for(int c=0;c<jsonArray.length();c++)
                        {
                            JSONObject object=jsonArray.getJSONObject(c);
                            mTitle.setText(object.getString("Q_Title"));
                            mQText.setText(object.getString("Q_Question"));
                          //  mQText.setText(object.getString("Q_ID"));

                        }

                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("Q_RqType","GetOneQuestion");
                map.put("Q_RqCode",generateRqCode(54639842,76632547));
                map.put("U_ID",QID);
                return map;
            }
        };
        RQ.add(stringRequest);
    }
}
