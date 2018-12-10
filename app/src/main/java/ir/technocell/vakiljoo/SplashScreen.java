package ir.technocell.vakiljoo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import es.dmoral.toasty.Toasty;


public class SplashScreen extends AppCompatActivity {


    private AVLoadingIndicatorView avl;


    private SharedPreferences userData;
    private SharedPreferences.Editor userDataEdit;

    private RequestQueue RQ;
    private StringRequest request;

    private HashMap<String,String> mapUser;

    private static String INFO_URL="http://vakiljoo.com/AppData/Users.php";
    private static String TIME_URL="http://vakiljoo.com/AppData/Timer.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        if(new VisualUtility(this).isInternetAvailable())
        {
            intit();
            CheckUserExist();
        }else {
            Toasty.error(this,"اتصال به اینترنت برقرار نیست !",Toast.LENGTH_LONG).show();
        }
    }

    private boolean CheckUserExist()
    {
        String userId=GetUserID();
        if(userId.equals("no"))
        {
            Log.e("User-->","NotFound");
            Intent iGoToRegister=new Intent(SplashScreen.this,Register.class);
            startActivity(iGoToRegister);
        }else {
            Log.e("User-->","Founded");
            GetInfo(GetUserID());
        }


        return false;
    }

    private boolean GetInfo(final String id)
    {

        request = new StringRequest(Request.Method.POST, INFO_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray jsonArray=new JSONArray(response.toString());
                    for(int c=0;c<jsonArray.length();c++)
                    {
                        JSONObject object=jsonArray.getJSONObject(c);
                        mapUser.put("User_ID",object.getString("U_ID"));
                        mapUser.put("U_Name_Show",object.getString("U_Name"));
                        mapUser.put("U_Family_Show",object.getString("U_Family"));
                        mapUser.put("U_Telephone_Show",object.getString("U_Telephone"));
                        mapUser.put("U_Money",object.getString("U_Money"));
                        mapUser.put("U_Type",object.getString("U_Type"));
                        mapUser.put("U_ProfilePic",object.getString("U_ProfilePic"));
                    }
                    SetOnlineTime();

                }catch (JSONException e)
                {
                    e.printStackTrace();
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
                map.put("U_RqType","getUserInfo");
                map.put("U_RqCode",generateRqCode(86639842,95632547));
                map.put("U_ID",id);
                return map;
            }
        };
        RQ.add(request);
        return false;
    }

    private void GoToNext(String userType)
    {
        if(userType.equals("lawyer"))
        {
            userDataEdit.putInt("userType",1);
           // Intent intent = new Intent(this, HqActivity.class); change HqActivity tovakil activity
            //intent.putExtra("mapUser", mapUser);
            //startActivity(intent);
        }else if(userType.equals("client")) {
            userDataEdit.putInt("userType",0);
            Intent intent = new Intent(this, HqActivity.class);
            intent.putExtra("mapUser", mapUser);
            startActivity(intent);
        }
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



    private void intit()
    {
        avl=findViewById(R.id.mAvl);
        avl.show();
        RQ= Volley.newRequestQueue(this);
        mapUser=new HashMap<>();

        userData=PreferenceManager.getDefaultSharedPreferences(this);
        userDataEdit=userData.edit();
    }

    private void SetOnlineTime()
    {
        request=new StringRequest(Request.Method.POST, TIME_URL, new Response.Listener<String>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                if(response.equals("Technocell:Ok"))
                {
                    Log.e("OnlineTime-->","Setted");
                    GoToNext(Objects.requireNonNull(mapUser.get("U_Type")));
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

    public void SetFullScreen()
    {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            android.app.ActionBar actionBar = getActionBar();
         getActionBar().hide();
        }
    }








}
