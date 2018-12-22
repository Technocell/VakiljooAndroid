package ir.technocell.vakiljoo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.technocell.vakiljoo.Activity.HqActivity;
import ir.technocell.vakiljoo.RecyclerAdapters.TopVakilAdapter;
import ir.technocell.vakiljoo.RecyclerAdapters.TopVakilsRecyclerListener;
import ir.technocell.vakiljoo.RecyclerItems.TopVakilsItem;

public class SearchVakil extends AppCompatActivity {

    EditText mSeachTextBar;
    private List<TopVakilsItem> topVakilsList = new ArrayList<>();
    private RecyclerView mTopVakilsRecycler;
    private RequestQueue RQ;
    private TopVakilAdapter mVakilsAdapter;
    private static final String USERS_URL="http://vakiljoo.com/AppData/Users.php";
    StringRequest stringRequest;
    ImageView mBackBtn;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vakil);
        Init();
    }


    private void Init()
    {
        mTopVakilsRecycler = findViewById(R.id.mTopVakilsRecycler);
        mVakilsAdapter = new TopVakilAdapter(topVakilsList,this);
        mBackBtn=findViewById(R.id.mBackBtn);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iBack=new Intent(SearchVakil.this,HqActivity.class);
                startActivity(iBack);
            }
        });
        mTopVakilsRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mTopVakilsRecycler.setLayoutManager(mLayoutManager);
        mTopVakilsRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mTopVakilsRecycler.setItemAnimator(new DefaultItemAnimator());
        mTopVakilsRecycler.setAdapter(mVakilsAdapter);

        mTopVakilsRecycler.addOnItemTouchListener(new TopVakilsRecyclerListener(this, mTopVakilsRecycler, new TopVakilsRecyclerListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                TopVakilsItem topVakilsItem = topVakilsList.get(position);

                Log.e("This is Top man-->",topVakilsItem.getName());

                Intent iGoToSoal=new Intent(SearchVakil.this,VakilInfoForUser.class);
                iGoToSoal.putExtra("VakilId",topVakilsItem.getVakilID());
                startActivity(iGoToSoal);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        RQ=Volley.newRequestQueue(this);
        mSeachTextBar=findViewById(R.id.mSeachTextBar);
        mSeachTextBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Search();
            }
        });
        
        

    }





    private void Search()
    {

        StringRequest topVakilRequest=new StringRequest(Request.Method.POST, USERS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response of Top",response.toString());
                try{
                    JSONArray jsonArray=new JSONArray(response.toString());
                    for(int c=0;c<jsonArray.length();c++)
                    {
                        JSONObject object=jsonArray.getJSONObject(c);
                        TopVakilsItem topVakilsItem=new TopVakilsItem(object.getString("U_Name")+" "+object.getString("U_Family")
                                ,object.getString("U_Education"),
                                object.getString("U_Expertise"),
                                object.getString("U_Rate"),
                                object.getString("U_ProfilePic"),
                                object.getString("U_AboutVakil"),
                                object.getString("U_LastOnline"),
                                object.getString("U_ID"));
                        topVakilsList.clear();

                        topVakilsList.add(topVakilsItem);
                    }
                    mVakilsAdapter.notifyDataSetChanged();

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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("U_RqType","searchVakil");
                map.put("U_Name",mSeachTextBar.getText().toString());
                map.put("U_Family",mSeachTextBar.getText().toString());
                map.put("U_RqCode",generateRqCode(86639842,95632547));
                map.put("U_ID",mSeachTextBar.getText().toString());
                return map;
            }
        };
        RQ.add(topVakilRequest);
    }




    public static String generateRqCode(int min, int max) {
        String finalCode;
        Random r = new Random();
        int requestCode = r.nextInt(max - min + 1) + min;
        finalCode = String.valueOf(requestCode);
        return finalCode;
    }

}
