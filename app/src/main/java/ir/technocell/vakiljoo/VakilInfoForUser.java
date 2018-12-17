package ir.technocell.vakiljoo;

import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.technocell.vakiljoo.Activity.HqActivity;
import mehdi.sakout.fancybuttons.FancyButton;

public class VakilInfoForUser extends AppCompatActivity {

   public static CircleImageView profile_image;
    public static TextView mDName,mDFamily;
    private ViewPager mVakilInfoPager;
    private PlaceholderFragment.SectionsPagerAdapter mSectionPageAdapter;
    Bundle bundle;
    private FancyButton mContactWays, mAboutMe;
    View view;
    ImageView mMohasebat,mMap,mChats,mTrhSoal,mHome,mProfile;



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vakil_info_for_user);
        Init();
        SetupHeader();
        TopButtonsOperator();
        InitBottomMenu();
        BottomMenuOperations();
    }

    private void InitBottomMenu()
    {

        view=findViewById(R.id.mBottomMenuMother);
         mMap=view.findViewById(R.id.mMap);
        mChats=view.findViewById(R.id.mChats);
        mHome=view.findViewById(R.id.mHome);
        mProfile=view.findViewById(R.id.mProfile);
    }
    private void BottomMenuOperations()
    {
        mTrhSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Tager:->","yes");
                Intent iGOToActivity=new Intent(VakilInfoForUser.this,UserQuestionsActivity.class);
                startActivity(iGOToActivity);
            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Tager:->","yes");
                Intent iGOToActivity=new Intent(VakilInfoForUser.this,HqActivity.class);
                startActivity(iGOToActivity);
            }
        });


    }


    private void SetupHeader()
    {

        View HeaderView=findViewById(R.id.mHeaderMother);
        ImageButton
                mHBackBtn=HeaderView.findViewById(R.id.mHBackBtn);
        TextView
                mHeaderTitle=HeaderView.findViewById(R.id.mHeaderTitle);
        mHeaderTitle.setText("اطلاعات وکیل");
        mHBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BACK = new Intent(VakilInfoForUser.this,HqActivity.class);
                startActivity(BACK);
            }
        });
    }

    private void TopButtonsOperator() {
        mContactWays.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                mContactWays.setBackgroundColor(getColor(R.color.colorPrimary));
                mContactWays.setTextColor(getColor(R.color.white));
                mAboutMe.setBackgroundColor(getColor(R.color.white));
                mAboutMe.setTextColor(getColor(R.color.colorPrimary));
                mVakilInfoPager.setCurrentItem(0);

            }
        });
        mAboutMe.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                mContactWays.setBackgroundColor(getColor(R.color.white));
                mContactWays.setTextColor(getColor(R.color.colorPrimary));
                mAboutMe.setBackgroundColor(getColor(R.color.colorPrimary));
                mAboutMe.setTextColor(getColor(R.color.white));
                mVakilInfoPager.setCurrentItem(1);

            }
        });

    }

    private void Init()
    {
        mContactWays = findViewById(R.id.mContactWays);
        mAboutMe = findViewById(R.id.mAboutMe);
        mTrhSoal=findViewById(R.id.mTrhSoal);
        profile_image=findViewById(R.id.profile_image);
        mDName = findViewById(R.id.mDName);
        mDFamily = findViewById(R.id.mDFamily);
        mVakilInfoPager = findViewById(R.id.mVakilInfoPager);
        mSectionPageAdapter = new PlaceholderFragment.SectionsPagerAdapter(getSupportFragmentManager());
        mVakilInfoPager.setAdapter(mSectionPageAdapter);
        bundle=getIntent().getExtras();
        try {

            new PlaceholderFragment().GetInfo(bundle.getString("VakilId"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static class PlaceholderFragment extends DialogFragment
    {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private RequestQueue RQ;
        private SharedPreferences userData;
        private TextView mAboutMeText;
        private FancyButton mSendAboutBtn,CallVakilBtn;
        private StringRequest request;
        private static String USER_URL = "http://vakiljoo.com/AppData/Users.php";

        private TextView mHMFrom,mHMTo,mHAFrom,mHATo,mCMFrom,
                mCMTo,mCAFrom,mCATo;

        private CheckBox mShanbeh,mYekShanbeh,mDoShanbeh,mSeShanbeh,
                mChaharShanbeh,mPanjShanbeh,mJomeh;

        private Context context;
        TimePickerDialog timePickerDialog;
        String[] WeekDays=new String[8];
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        private String Telephone;

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
            RQ = Volley.newRequestQueue(getContext());
            final View[] rootView = {null};
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                rootView[0] = inflater.inflate(R.layout.fragment_about_vakil_for_users, container, false);
                InitAboutFrag(rootView[0]);
                return rootView[0];
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                rootView[0] = inflater.inflate(R.layout.fragment_contact_vakil_for_users, container, false);
                InitVakilContact(rootView[0]);

                return rootView[0];
            }

            return rootView[0];
        }

        private void InitVakilContact(View rootView)
        {
            mHMFrom=rootView.findViewById(R.id.mHMFrom);
            mHMTo=rootView.findViewById(R.id.mHMTo);
            mHAFrom=rootView.findViewById(R.id.mHAFrom);
            mHATo=rootView.findViewById(R.id.mHATo);
            mCMFrom=rootView.findViewById(R.id.mCMFrom);
            mCMTo=rootView.findViewById(R.id.mCMTo);
            mCAFrom=rootView.findViewById(R.id.mCAFrom);
            mCATo=rootView.findViewById(R.id.mCATo);

            mShanbeh=rootView.findViewById(R.id.mShanbeh);
            mYekShanbeh=rootView.findViewById(R.id.mYekShanbeh);
            mDoShanbeh=rootView.findViewById(R.id.mDoShanbeh);
            mSeShanbeh=rootView.findViewById(R.id.mSeShanbeh);
            mChaharShanbeh=rootView.findViewById(R.id.mChaharShanbeh);
            mPanjShanbeh=rootView.findViewById(R.id.mPanjShanbeh);
            mJomeh=rootView.findViewById(R.id.mJomeh);
            CallVakilBtn=rootView.findViewById(R.id.CallVakilBtn);
            sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getContext());
            editor=sharedPreferences.edit();
            editor.remove("userImage");
            editor.commit();

        }


        private String GetUserID() {
            return userData.getString("User_ID", "no");
        }

        private void InitAboutFrag(View rootView) {
            mAboutMeText = rootView.findViewById(R.id.mAboutMeText);
        }

        public boolean GetInfo(final String id)
        {

            request = new StringRequest(Request.Method.POST, USER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try{
                        JSONArray jsonArray=new JSONArray(response.toString());
                        for(int c=0;c<jsonArray.length();c++)
                        {
                            JSONObject object=jsonArray.getJSONObject(c);
                            mDName.setText(object.getString("U_Name"));
                           mDFamily.setText(object.getString("U_Family"));
                            Telephone=object.getString("U_Telephone");

                            mHMFrom.setText(object.getString("U_HMorning"));
                            mHMTo.setText(object.getString("U_HMorningE"));
                            mHAFrom.setText(object.getString("U_HAfternoon"));
                            mHATo.setText(object.getString("HAfternoonE"));
                            mCMFrom.setText(object.getString("U_TMorning"));
                            mCMTo.setText(object.getString("U_HMorningE"));
                            mCAFrom.setText(object.getString("U_Tafternoon"));
                            mCATo.setText(object.getString("U_TafternoonE"));
                            Picasso.get().load(object.getString("U_ProfilePic")).placeholder(R.drawable.vakile_profile).into(profile_image);
                            mAboutMeText.setText(object.getString("U_AboutVakil"));
                            SetHftehDays(object.getString("U_WeekDay").trim());
                        }

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

        private void SetHftehDays(String days)
        {

            String[] separated = days.split(",");
            if(separated[0].trim().trim().equals("1"))
            {
                mShanbeh.setChecked(true);
                mSeShanbeh.setEnabled(false);
            }else {
                mShanbeh.setChecked(false);
                mSeShanbeh.setEnabled(false);


            }
            if(separated[1].trim().equals("1"))
            {
                mYekShanbeh.setChecked(true);
                mYekShanbeh.setEnabled(false);

            }else {
                mYekShanbeh.setChecked(false);
                mYekShanbeh.setEnabled(false);

            }
            if(separated[2].trim().equals("1"))
            {
                mDoShanbeh.setChecked(true);
                mDoShanbeh.setEnabled(false);

            }else {
                mDoShanbeh.setChecked(false);
                mDoShanbeh.setEnabled(false);

            }
            if(separated[3].trim().equals("1"))
            {
                mSeShanbeh.setChecked(true);
                mSeShanbeh.setEnabled(false);

            }else {
                mSeShanbeh.setChecked(false);
                mSeShanbeh.setEnabled(false);


            }
            if(separated[4].trim().equals("1"))
            {
                mChaharShanbeh.setChecked(true);
                mChaharShanbeh.setEnabled(false);

            }else {
                mChaharShanbeh.setChecked(false);
                mChaharShanbeh.setEnabled(false);

            }
            if(separated[5].trim().equals("1"))
            {
                mPanjShanbeh.setChecked(true);
                mPanjShanbeh.setEnabled(false);

            }else {
                mPanjShanbeh.setChecked(false);
                mPanjShanbeh.setEnabled(false);

            }
            if(separated[6].trim().equals("1"))
            {
                mJomeh.setChecked(true);
                mJomeh.setEnabled(false);

            }else {
                mJomeh.setChecked(false);
                mJomeh.setEnabled(false);

            }



        }
        private String generateRqCode(int min, int max) {
            String finalCode;
            Random r = new Random();
            int requestCode = r.nextInt(max - min + 1) + min;
            finalCode = String.valueOf(requestCode);
            return finalCode;
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


    }

}
