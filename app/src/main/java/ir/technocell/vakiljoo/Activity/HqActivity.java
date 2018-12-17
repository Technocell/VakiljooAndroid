package ir.technocell.vakiljoo.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import ir.technocell.vakiljoo.R;
import ir.technocell.vakiljoo.RecyclerAdapters.TopSoalsAdapter;
import ir.technocell.vakiljoo.RecyclerAdapters.TopVakilAdapter;
import ir.technocell.vakiljoo.RecyclerAdapters.TopVakilsRecyclerListener;
import ir.technocell.vakiljoo.RecyclerItems.TopSoalsItem;
import ir.technocell.vakiljoo.RecyclerItems.TopVakilsItem;
import ir.technocell.vakiljoo.SearchVakil;
import ir.technocell.vakiljoo.UserQuestionsActivity;
import ir.technocell.vakiljoo.VakilInfoForUser;
import ir.technocell.vakiljoo.VisualUtility;
import mehdi.sakout.fancybuttons.FancyButton;
import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class HqActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Slider slider;
    ViewPager mTopsViewPager;
    FancyButton mTopSoalsBtn,mTopVakils;
    private  boolean drawerState=false;
    private RequestQueue RQ;
    private LayoutInflater inflater;
    private ImageView mDrawerIcon,mSearchImgBtn;
    private TextView mSearchTxtBtn;
    private static final String USERS_URL="http://vakiljoo.com/AppData/Users.php";
    private static final String QUESTIONS_URL="http://vakiljoo.com/AppData/Questions.php";

    private TextView mDName,mDFamily,mDMoney;
    CircleImageView profile_image;
    NavigationView navigationView;
    private PlaceholderFragment.SectionsPagerAdapter mSectionsPagerAdapter;
    View view;
    ImageView mMohasebat,mMap,mChats,mTrhSoal,mHome,mProfile;

    HashMap<String, String> hashMap = new HashMap<>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hq);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "vazir.ttf");

        Init();
        TopButtonsOperator();
        InitDrawerMenu();
        try {


            if (savedInstanceState.isEmpty() || savedInstanceState == null) {
                InitDrawerOperations(GetDaMapData());
            } else {
                InitDrawerSaveLoadOperations(savedInstanceState.getBundle("mapData"));
            }
        }catch (Exception e){ e.printStackTrace(); }
        InitDrawerOperations(GetDaMapData());
        InitBottomMenu();
        BottomMenuOperations();

    }

    private void BottomMenuOperations()
    {
        mTrhSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Tager:->","yes");
                Intent iGOToActivity=new Intent(HqActivity.this,UserQuestionsActivity.class);
                startActivity(iGOToActivity);
            }
        });

    }

private void InitBottomMenu()
{
    view=findViewById(R.id.mBottomMenuMother);
    mTrhSoal=view.findViewById(R.id.mTrhSoal);
   // mMohasebat=view.findViewById(R.id.mMohasebat);
  //  mMap=view.findViewById(R.id.mMap);
    mChats=view.findViewById(R.id.mChats);
  //  mTrhSoal=view.findViewById(R.id.mTrhSoal);
    mHome=view.findViewById(R.id.mHome);
    mProfile=view.findViewById(R.id.mProfile);

}


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    private void InitDrawerMenu()
    {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerIcon=findViewById(R.id.mDrawerIcon);
        mSearchImgBtn=findViewById(R.id.mSearchImgBtn);
        mSearchTxtBtn=findViewById(R.id.mSearchTxtBtn);
        mSearchImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGoToSoal=new Intent(HqActivity.this,SearchVakil.class);
                startActivity(iGoToSoal);
            }
        });
        mSearchTxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGoToSoal=new Intent(HqActivity.this,SearchVakil.class);
                startActivity(iGoToSoal);
            }
        });

        final DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@android.support.annotation.NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@android.support.annotation.NonNull View view) {
                drawerState=true;

            }
            @Override
            public void onDrawerClosed(@android.support.annotation.NonNull View view) {
                drawerState=false;

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
        mDrawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerState){
                    drawer.closeDrawer(GravityCompat.END);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });



        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);




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

    private void InitDrawerOperations(HashMap hashMap)
    {
        try {

            View view=navigationView.getHeaderView(0);
            mDName = view.findViewById(R.id.mDName);
            mDFamily = view.findViewById(R.id.mDFamily);
            mDMoney = view.findViewById(R.id.mDMoney);
            profile_image=view.findViewById(R.id.profile_image);
            mDName.setText(hashMap.get("U_Name_Show").toString());
            mDFamily.setText(hashMap.get("U_Family_Show").toString());
            mDMoney.setText(hashMap.get("U_Money").toString());
            Picasso.get().load(hashMap.get("U_ProfilePic").toString()).into(profile_image);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void InitDrawerSaveLoadOperations(Bundle data)
    {
         HashMap<String,String> hashMap=(HashMap<String, String>) data.getSerializable("mapData");
        try {

            View view=navigationView.getHeaderView(0);
            mDName = view.findViewById(R.id.mDName);
            mDFamily = view.findViewById(R.id.mDFamily);
            mDMoney = view.findViewById(R.id.mDMoney);
            profile_image=view.findViewById(R.id.profile_image);
            mDName.setText(hashMap.get("U_Name_Show").toString());
            mDFamily.setText(hashMap.get("U_Family_Show").toString());
            mDMoney.setText(hashMap.get("U_Money").toString());
            Picasso.get().load(hashMap.get("U_ProfilePic").toString()).into(profile_image);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }




    private void Init()
    {
        new VisualUtility(this).InitCalliGraphy();
        RQ=Volley.newRequestQueue(this);
        mTopSoalsBtn=findViewById(R.id.mTopSoalsBtn);
        mTopVakils=findViewById(R.id.mTopVakils);
        slider=findViewById(R.id.mSlider);
        slider.init(new PicassoImageLoadingService(this));
        slider.setAdapter(new MainSliderAdapter());
        mTopsViewPager = findViewById(R.id.mTopsViewPager);
        mSectionsPagerAdapter = new PlaceholderFragment.SectionsPagerAdapter(getSupportFragmentManager());

        mTopsViewPager.setAdapter(mSectionsPagerAdapter);

    }

    private void TopButtonsOperator()
    {
        mTopSoalsBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                mTopSoalsBtn.setBackgroundColor(getColor(R.color.colorPrimary));
                mTopSoalsBtn.setTextColor(getColor(R.color.white));
                mTopVakils.setBackgroundColor(getColor(R.color.white));
                mTopVakils.setTextColor(getColor(R.color.colorPrimary));
                mTopsViewPager.setCurrentItem(0);

            }
        });
        mTopVakils.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                mTopSoalsBtn.setBackgroundColor(getColor(R.color.white));
                mTopSoalsBtn.setTextColor(getColor(R.color.colorPrimary));
                mTopVakils.setBackgroundColor(getColor(R.color.colorPrimary));
                mTopVakils.setTextColor(getColor(R.color.white));
                mTopsViewPager.setCurrentItem(1);

            }
        });

    }
    public static String generateRqCode(int min, int max) {
        String finalCode;
        Random r = new Random();
        int requestCode = r.nextInt(max - min + 1) + min;
        finalCode = String.valueOf(requestCode);
        return finalCode;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("mapData",hashMap);
        super.onSaveInstanceState(outState);
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
       // getMenuInflater().inflate(R.menu.hq, menu);

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

            Toasty.info(HqActivity.this,"به زودی ...",Toast.LENGTH_SHORT).show();


        } else if (id == R.id.site) {
            String url = "https://44951295.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } else if (id == R.id.boutus) {
            Toasty.info(HqActivity.this,"به زودی ...",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.support) {
            Toasty.info(HqActivity.this,"به زودی ...",Toast.LENGTH_SHORT).show();


        } else if (id == R.id.sendapp) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, R.string.shareText);
            startActivity(Intent.createChooser(sharingIntent, "یک مورد را انتخاب کنید."));

        } else if (id == R.id.settings) {
            Toasty.info(HqActivity.this,"به زودی ...",Toast.LENGTH_SHORT).show();


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




    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private List<TopVakilsItem> topVakilsList = new ArrayList<>();
        private List<TopSoalsItem> topSoalsList = new ArrayList<>();

        private RecyclerView mTopVakilsRecycler,mTopSoalsRecycler;
        private TopVakilAdapter mVakilsAdapter;
        private TopSoalsAdapter mTopSoalsAdapter;
        private RequestQueue RQ;



        public PlaceholderFragment() {

        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            RQ=Volley.newRequestQueue(getContext());
            final View[] rootView = {null};
            if(getArguments().getInt(ARG_SECTION_NUMBER)==2)
            {
                rootView[0] = inflater.inflate(R.layout.fragment_top_vakils, container, false);
                InitTopVakilData(rootView[0]);
                GetTopVakilsFromServer();

                return rootView[0];
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==1)
            {
                rootView[0] = inflater.inflate(R.layout.fragment_top_soals, container, false);
                InitTopSoalsData(rootView[0]);
                GetTopSoalsFromServer();
                return rootView[0];
            }

            return rootView[0];
        }



        public static class SectionsPagerAdapter extends FragmentPagerAdapter {

            public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                // getItem is called to instantiate the fragment for the given page.
                // Return a PlaceholderFragment (defined as a static inner class below).
                return PlaceholderFragment.newInstance(position + 1);
            }

            @Override
            public int getCount() {
                // Show 3 total pages.
                return 2;
            }
        }

        private void GetTopVakilsFromServer()
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
                    map.put("U_RqCode", HqActivity.generateRqCode(75639842, 95632547));
                    map.put("U_RqType", "GetPremiumUsers");
                    return map;
                }
            };
            RQ.add(topVakilRequest);
        }

        private void GetTopSoalsFromServer()
        {
            StringRequest topVakilRequest=new StringRequest(Request.Method.POST, QUESTIONS_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("ResponseDDDDDDD",response.toString());
                    try{
                        JSONArray jsonArray=new JSONArray(response.toString());
                        for(int c=0;c<jsonArray.length();c++)
                        {
                            JSONObject object=jsonArray.getJSONObject(c);
                            TopSoalsItem topSoalsItem=new TopSoalsItem(object.getString("Q_Title"),object.getString("Q_Question"),object.getString("Q_UName")+" "+object.getString("Q_UFamily"), object.getString("Q_Date"), object.getString("Q_ID"),object.getString("Q_isPublic"),object.getString("Q_UID"));
                            topSoalsList.add(topSoalsItem);
                        }
                        mTopSoalsAdapter.notifyDataSetChanged();

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
                    map.put("Q_RqCode", generateRqCode(54639842, 76632547));
                    map.put("Q_RqType", "GetQuestion");
                    return map;
                }
            };
            RQ.add(topVakilRequest);
        }


        private void InitTopVakilData(View rootView)
        {

            mTopVakilsRecycler = rootView.findViewById(R.id.mTopVakilsRecycler);
            mTopVakilsRecycler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("Yaaa","ReCYCLER");
                }
            });



            mVakilsAdapter = new TopVakilAdapter(topVakilsList,getContext());
            mTopVakilsRecycler.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            mTopVakilsRecycler.setLayoutManager(mLayoutManager);
            mTopVakilsRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            mTopVakilsRecycler.setItemAnimator(new DefaultItemAnimator());
            mTopVakilsRecycler.setAdapter(mVakilsAdapter);

            mTopVakilsRecycler.addOnItemTouchListener(new TopVakilsRecyclerListener(getContext(), mTopVakilsRecycler, new TopVakilsRecyclerListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    TopVakilsItem topVakilsItem = topVakilsList.get(position);

                    Log.e("This is Top man-->",topVakilsItem.getName());

                    Intent iGoToSoal=new Intent(getContext(),VakilInfoForUser.class);
                    iGoToSoal.putExtra("QID",topVakilsItem.getVakilID());
                    startActivity(iGoToSoal);

                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        }
        private void InitTopSoalsData(View rootView)
        {

            mTopSoalsRecycler = rootView.findViewById(R.id.mTopSoalsRecycler);
            mTopSoalsRecycler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("Yaaa","ReCYCLER");
                }
            });

            mTopSoalsAdapter = new TopSoalsAdapter(topSoalsList,getContext());

            mTopSoalsRecycler.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            mTopSoalsRecycler.setLayoutManager(mLayoutManager);
            mTopSoalsRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            mTopSoalsRecycler.setItemAnimator(new DefaultItemAnimator());
            mTopSoalsRecycler.setAdapter(mTopSoalsAdapter);

            mTopSoalsRecycler.addOnItemTouchListener(new TopVakilsRecyclerListener(getContext(), mTopSoalsRecycler, new TopVakilsRecyclerListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    TopSoalsItem topSoalsItem = topSoalsList.get(position);

                    Log.e("This is Top man-->",topSoalsItem.getNameoFamily());


                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        }


    }



}


 class PicassoImageLoadingService implements ImageLoadingService {
    public Context context;

    public PicassoImageLoadingService(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {

        Picasso.get().load(url).into(imageView);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        Picasso.get().load(resource).into(imageView);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        Picasso.get().load(url).placeholder(placeHolder).error(errorDrawable).into(imageView);
    }

}
 class MainSliderAdapter extends SliderAdapter {

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        switch (position) {
            case 0:
                viewHolder.bindImageSlide("http://vakiljoo.com/AppData/Core/Slider/Slider1.png");
                break;
            case 1:
                viewHolder.bindImageSlide("http://vakiljoo.com/AppData/Core/Slider/Slider2.png");
                break;
            case 2:
                viewHolder.bindImageSlide("http://vakiljoo.com/AppData/Core/Slider/Slider3.png");
                break;
        }
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