package ir.technocell.vakiljoo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import ir.technocell.vakiljoo.Activity.HqActivity;
import ir.technocell.vakiljoo.Adapter.HqClientAdapter;
import ir.technocell.vakiljoo.DataModel.MyQuestionModel;
import ir.technocell.vakiljoo.RecyclerAdapters.TopVakilsRecyclerListener;


public class HqClient extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerviewQuestion;
    private HqClientAdapter hqClientAdapter;
    private MyQuestionModel myQuestionModel;
    private List<MyQuestionModel> myQuestionModelList=new ArrayList<>();
    private static String JSON_URL = "http://vakiljoo.com/AppData/Questions.php";
    private StringRequest QuestionsReauest;
    private RequestQueue Rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hq_client);

        init();
        InitRecycler();
        GetInfo();
        
    }

    private void init() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerviewQuestion = findViewById(R.id.mRecyclerview);
        Rq= Volley.newRequestQueue(this);



    }

    private void InitRecycler()
    {
        Rq=Volley.newRequestQueue(this);
        hqClientAdapter=new HqClientAdapter(myQuestionModelList);
        recyclerviewQuestion.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerviewQuestion.setLayoutManager(mLayoutManager);
        recyclerviewQuestion.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerviewQuestion.setItemAnimator(new DefaultItemAnimator());
        recyclerviewQuestion.setAdapter(hqClientAdapter);

        recyclerviewQuestion.addOnItemTouchListener(new TopVakilsRecyclerListener(this, recyclerviewQuestion, new TopVakilsRecyclerListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                MyQuestionModel topVakilsItem = myQuestionModelList.get(position);

                Log.e("This is Top man-->",topVakilsItem.getTitle());

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    private void GetInfo()
    {
        QuestionsReauest=new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray=new JSONArray(response.toString());
                    for(int c=0;c<jsonArray.length();c++)
                    {
                        JSONObject object=jsonArray.getJSONObject(c);
                        String picPath="http://vakiljoo.com/AppData/Core/ProfilePics/"+object.getString("Q_UID")+".png";
                            MyQuestionModel myQuestionModel=new MyQuestionModel(picPath,object.getString("Q_Title"),object.getString("Q_Question"),
                                    object.getString("Q_Date"),
                                    object.getString("Q_Group"),object.getString("Q_UName")+" "+object.getString("Q_UFamily"));
                        myQuestionModelList.add(myQuestionModel);
                    }
                    hqClientAdapter.notifyDataSetChanged();

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
                Map<String, String> map = new HashMap<String, String>();
                map.put("Q_RqType", "GetQuestion");
                map.put("Q_RqCode", generateRqCode(54639842, 76632547));
                return map;
            }
        };
        Rq.add(QuestionsReauest);
    }
    public static String generateRqCode(int min, int max) {
        String finalCode;
        Random r = new Random();
        int requestCode = r.nextInt(max - min + 1) + min;
        finalCode = String.valueOf(requestCode);
        return finalCode;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hq_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.wallet) {

            Toasty.info(HqClient.this,"به زودی ...",Toast.LENGTH_SHORT).show();


        } else if (id == R.id.site) {
            String url = "https://44951295.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } else if (id == R.id.boutus) {
            Toasty.info(HqClient.this,"به زودی ...",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.support) {
            Toasty.info(HqClient.this,"به زودی ...",Toast.LENGTH_SHORT).show();


        } else if (id == R.id.sendapp) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, R.string.shareText);
            startActivity(Intent.createChooser(sharingIntent, "یک مورد را انتخاب کنید."));

        } else if (id == R.id.settings) {
            Toasty.info(HqClient.this,"به زودی ...",Toast.LENGTH_SHORT).show();


        }else if (id == R.id.exit) {

            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }







}
