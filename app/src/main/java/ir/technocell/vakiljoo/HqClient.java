package ir.technocell.vakiljoo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.technocell.vakiljoo.Activity.MatnSoal;
import ir.technocell.vakiljoo.Activity.VakilInformationActivity;
import ir.technocell.vakiljoo.Adapter.HqClientAdapter;
import ir.technocell.vakiljoo.DataModel.MyQuestionModel;
import ir.technocell.vakiljoo.RecyclerAdapters.TopVakilsRecyclerListener;





public class HqClient extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerviewQuestion;
    private HqClientAdapter hqClientAdapter;
    private MyQuestionModel myQuestionModel;
    private List<MyQuestionModel> myQuestionModelList = new ArrayList<>();
    private static String JSON_URL = "http://vakiljoo.com/AppData/Questions.php";
    private StringRequest QuestionsReauest;
    private RequestQueue Rq;
    private ImageView mDrawerIcon;
    private TextView mFilter;
    private boolean drawerState = false;
    View view;
    ImageView mMohasebat, mMap, mChats, mTrhSoal, mHome, mProfile;
    PopupMenu popupMenu;
    MaterialDialog dialog;
    NavigationView navigationView;

    private TextView mDName,mDFamily,mDMoney,mDProfileState;

    private Typeface typeface;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CircleImageView profile_image;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hq_client);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "vazir.ttf");

        init();
        InitRecycler();
        GetInfo();
        InitBottomMenu();
        BottomMenuOperations();
        InitDrawerOperations();

    }

    private void InitDrawerOperations()
    {
        try {

            View view=navigationView.getHeaderView(0);
            mDName = view.findViewById(R.id.mDName);
            mDFamily = view.findViewById(R.id.mDFamily);
            mDMoney = view.findViewById(R.id.mDMoney);
            mDProfileState=view.findViewById(R.id.mDProfileState);

            mDName.setTypeface(typeface);
            mDFamily.setTypeface(typeface);
            mDMoney.setTypeface(typeface);
            mDProfileState.setTypeface(typeface);


            profile_image=view.findViewById(R.id.profile_image);


            mDName.setText("نام : "+ sharedPreferences.getString("U_Name_Show","").toString());
            mDFamily.setText("نام خانوادگی : "+sharedPreferences.getString("U_Family_Show","").toString());

            if(sharedPreferences.getString("U_isConfirmed","").equals("true"))
            {
                mDProfileState.setText("پروفایل : تایید شده");
            }else {
                mDProfileState.setText("پروفایل : تایید نشده");
            }
            if(TextUtils.isEmpty(sharedPreferences.getString("U_Money","").toString()))
            {
                mDMoney.setText("موجودی : 00000");
            }else {
                mDMoney.setText("موجودی : "+sharedPreferences.getString("U_Money","").toString());
            }

            Picasso.get().load("http://"+sharedPreferences.getString("U_ProfilePic","").toString()).placeholder(R.drawable.vakile_profile).into(profile_image);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }




    private void BottomMenuOperations() {
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Tager:->", "yes");

                Toasty.info(HqClient.this, "شما اینجا هستید", Toast.LENGTH_SHORT).show();

            }
        });
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGOToActivity = new Intent(HqClient.this, VakilInformationActivity.class);
                startActivity(iGOToActivity);
            }
        });
    }

    private void InitBottomMenu() {
        view = findViewById(R.id.mBottomMenuMotherClient);
        //  mTrhSoal=view.findViewById(R.id.mTrhSoal);
        //   mMohasebat=view.findViewById(R.id.mMohasebat);
        mMap = view.findViewById(R.id.mMap);
        mChats = view.findViewById(R.id.mChats);
        // mTrhSoal=view.findViewById(R.id.mTrhSoal);
        mHome = view.findViewById(R.id.mHome);
        mProfile = view.findViewById(R.id.mProfile);

    }

    private void init() {
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        editor=sharedPreferences.edit();
        new VisualUtility(this).InitCalliGraphy();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFilter = findViewById(R.id.mFilter);
        mFilter.setTypeface(typeface);
        typeface=Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf");
        popupMenu = new PopupMenu(HqClient.this, mFilter);
        popupMenu.getMenuInflater().inflate(R.menu.hq_client, popupMenu.getMenu());
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
         navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerviewQuestion = findViewById(R.id.mRecyclerview);
        Rq = Volley.newRequestQueue(this);
        mDrawerIcon = findViewById(R.id.mDrawerIcon);
        FilterMenu();
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                drawerState = true;

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                drawerState = false;

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
        mDrawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerState) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });


    }

    private void InitRecycler() {
        Rq = Volley.newRequestQueue(this);
        hqClientAdapter = new HqClientAdapter(myQuestionModelList);
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

                Log.e("This is Top man-->", topVakilsItem.getProfile());
                Intent iGoToSoal = new Intent(HqClient.this, MatnSoal.class);
                iGoToSoal.putExtra("QID", topVakilsItem.getId());
                startActivity(iGoToSoal);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void FilterMenu()
    {
        final String[] items=new String[6];
        items[0]="کیفری";
        items[1]="حقوقی";
        items[2]="خانواده";
        items[3]="ثبت احوال";
        items[4]="امور مهاجرت";
        items[5]="همه سوالات";


        mFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(HqClient.this)
                        .items(items).itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {

                        if(which==5)
                        {
                            GetInfo();
                        }else {

                            GetCustomQuestion(items[which].toString().trim());
                        }
                        return false;
                    }
                }).title("فیلتر سوالات").positiveText("تایید").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }

   /* private void OpenCustopPopUpMenu() {
        mFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getTitle().equals("کیفری")) {
                            GetCustomQuestion(menuItem.getTitle().toString());
                        } else if (menuItem.getTitle().equals("حقوقی")) {
                            GetCustomQuestion(menuItem.getTitle().toString());

                        } else if (menuItem.getTitle().equals("خانواده")) {
                            GetCustomQuestion(menuItem.getTitle().toString());

                        } else if (menuItem.getTitle().equals("ثبت احوال")) {
                            GetCustomQuestion(menuItem.getTitle().toString());

                        } else if (menuItem.getTitle().equals("امور مهاجرت")) {
                            GetCustomQuestion(menuItem.getTitle().toString());

                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }
*/

    private void GetCustomQuestion(final String type) {
        myQuestionModelList.clear();
        Log.e("ItemSelected-->",type.toString());
        dialog = new MaterialDialog.Builder(HqClient.this).title("لطفا صبر کنید").content("درحال آنالیز اطلاعات").progress(true, 0).titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf", "vazir.ttf").show();
        QuestionsReauest = new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response of Filter-->",response.toString());
                dialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int c = 0; c < jsonArray.length(); c++) {
                        JSONObject object = jsonArray.getJSONObject(c);
                        String picPath = "http://vakiljoo.com/AppData/Core/ProfilePics/" + object.getString("Q_UID") + ".jpeg";
                        MyQuestionModel myQuestionModel = new MyQuestionModel(picPath, object.getString("Q_Title"), object.getString("Q_Question"),
                                object.getString("Q_Date"),
                                object.getString("Q_Group"), object.getString("Q_UName") + " " + object.getString("Q_UFamily"), object.getString("Q_ID"));
                        myQuestionModelList.add(myQuestionModel);
                    }
                    hqClientAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Q_RqType", "GetCustomQuestion");
                map.put("Q_RqCode", generateRqCode(54639842, 76632547));
                map.put("Q_CusType", type.trim());

                return map;
            }
        };
        Rq.add(QuestionsReauest);
    }

    private void GetInfo() {
        myQuestionModelList.clear();
        QuestionsReauest = new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("qqqqq",response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int c = 0; c < jsonArray.length(); c++) {
                        JSONObject object = jsonArray.getJSONObject(c);
                        String picPath = "http://vakiljoo.com/AppData/Core/ProfilePics/" + object.getString("Q_UID") + ".jpeg";
                        MyQuestionModel myQuestionModel = new MyQuestionModel(picPath, object.getString("Q_Title"), object.getString("Q_Question"),
                                object.getString("Q_Date"),
                                object.getString("Q_Group"), object.getString("Q_UName") + " " + object.getString("Q_UFamily"), object.getString("Q_ID"));
                        myQuestionModelList.add(myQuestionModel);
                    }
                    hqClientAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.hq_client, menu);
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

            Toasty.info(HqClient.this, "به زودی ...", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.site) {
            String url = "https://44951295.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } else if (id == R.id.boutus) {
            Toasty.info(HqClient.this, "به زودی ...", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.support) {
            Toasty.info(HqClient.this, "به زودی ...", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.sendapp) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, R.string.shareText);
            startActivity(Intent.createChooser(sharingIntent, "یک مورد را انتخاب کنید."));

        } else if (id == R.id.settings) {
            Toasty.info(HqClient.this, "به زودی ...", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.exit) {

            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }


}

final class FontsOverride {

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);

            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
