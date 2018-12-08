package ir.technocell.vakiljoo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.daasuu.cat.CountAnimationTextView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.raycoarana.codeinputview.CodeInputView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import androidx.annotation.NonNull;
import es.dmoral.toasty.Toasty;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import mehdi.sakout.fancybuttons.FancyButton;

public class Register extends AppCompatActivity {


    VisualUtility visualUtility;
    private PlaceholderFragment.SectionsPagerAdapter mSectionsPagerAdapter;

    public static ViewPager mViewPager;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        visualUtility=new VisualUtility(this);
        visualUtility.InitCalliGraphy();

        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new PlaceholderFragment.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOffscreenPageLimit(1);
       mViewPager.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               return false;
           }
       });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String SMS_URL="http://vakiljoo.com/AppData/smsService.php";
        private static final String USER_URL="http://vakiljoo.com/AppData/Users.php";
        private static final HashMap<String,String> userData=new HashMap<>();
        private Button mVakilBtn,mMovakelBtn;
        private FancyButton mRegisterBtn,mVorodBtn,mSendCodeTwice,mTimerBtn
                ,mPrvanehImage,mSendMoreDataBtn;
        private CountAnimationTextView mCodeCounter;
            private EditText mName,mFamily,mNumber;
            private MaterialDialog dialog;
            private RequestQueue RQ;
            private CodeInputView mVerifyCode;
            private boolean canSendTwice=false;
            private String UserNumber;
            private String isVakil;
            private EditText mPavanehNumber,mTahselat,mTakhasos,mTeleSabet,mAddress;
            private RadioButton mVokala,mMoshaveran;
            private String VoKalaVSMoshaveran;
            private VisualUtility visualUtility;
            private String ParvaneImageEncoded;

        private SharedPreferences userPrefrence;
        private SharedPreferences.Editor userDataEdit;
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
            final View[] rootView = {null};
            RQ=Volley.newRequestQueue(getContext());
            userPrefrence=PreferenceManager.getDefaultSharedPreferences(getContext());
            userDataEdit=userPrefrence.edit();

            if(getArguments().getInt(ARG_SECTION_NUMBER)==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rootView[0] = inflater.inflate(R.layout.fragment_who_are_you, container, false);
                        InitWhoAreYou(rootView[0]);
                        mVakilBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               userData.put("userType","lawyer");
                                mViewPager.setCurrentItem(1);
                            }
                        });
                        mMovakelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                userData.put("userType","client");

                                mViewPager.setCurrentItem(1);
                            }
                        });
                    }
                });



                return rootView[0];
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==2)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rootView[0] = inflater.inflate(R.layout.fragment_login_register, container, false);
                        InitLoginRegister(rootView[0]);
                        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(CheckInput()) {
                                    userData.put("U_Name_Show",mName.getText().toString());
                                    userData.put("U_Family_Show",mFamily.getText().toString());
                                    userData.put("U_Telephone_Show",mNumber.getText().toString());
                                    userData.put("smsCode",generateRqCode(7615,9323));
                                    SendSms();
                                }else {
                                    Toasty.warning(getContext(), "لطفا ورودی های خود را چک کنید.", Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        });
                    }
                });
                return rootView[0];
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==3)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rootView[0] = inflater.inflate(R.layout.fragment_code_verify, container, false);
                        InitCodeVerify(rootView[0]);

                        mVorodBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String smsCode=userData.get("smsCode");
                                Log.e("Code-->",smsCode);
                                Log.e("VERIFYC-->",mVerifyCode.getCode().toString());

                                if(smsCode.equals(mVerifyCode.getCode().trim()))
                                {
                                    Log.e("Code Verifyed"," Yes");

                                    SendFirstUserData();
                                }else {
                                    Toasty.error(getContext(), "کد وارد شده اشتباه است !", Toast.LENGTH_SHORT, true).show();

                                }

                            }
                        });

                        mSendCodeTwice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(canSendTwice)
                                {
                                    SendSms();
                                }else {
                                    Toasty.warning(getContext(), "لطفا صبر کنید...", Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        });
                    }
                });

                return rootView[0];
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==4){
                rootView[0] = inflater.inflate(R.layout.fragment_vakil_more_data, container, false);
                InitAddMoreDataVakil(rootView[0]);
                mSendMoreDataBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(VakilMoreDataValidator())
                        {
                            SendVakilMoreData();
                        }else {
                            Toasty.warning(getContext(),"لطفا تمام اطلاعات را تکمیل کنید",Toast.LENGTH_SHORT,true).show();
                        }
                    }
                });
                mPrvanehImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CheckGalleryPremession();
                    }
                });
                mMoshaveran.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(compoundButton.isChecked())
                        {
                            VoKalaVSMoshaveran="true";
                        }
                    }
                });
                mVokala.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(compoundButton.isChecked())
                        {
                            VoKalaVSMoshaveran="false";
                        }
                    }
                });

                return rootView[0];
            }
            return rootView[0];
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            if (requestCode == 276) {
                if (data != null) {
                    try {
                       ParvaneImageEncoded= visualUtility.encodeTobase64(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData()));
                       Toasty.info(getContext(),"تصویر پروانه انتخاب شد",Toast.LENGTH_SHORT,true).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        private boolean CheckGalleryPremession()
        {
            final boolean[] res = {false};
                final Register reg = new Register();
            Dexter.withActivity(getActivity())
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                            res[0] =true;
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "تصویر پروانه را انتخاب کنید"), 276);

                            }
                        @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                            CheckGalleryPremession();
                            /* ... */}
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            Toasty.info(getContext(),"برای تصویر پروانه دسترسی به فایل را مجاز کنید.",Toast.LENGTH_SHORT,true).show();

                            reg.shouldShowRequestPermissionRationale(permission.getName());
                            /* ... */}
                    }).check();
            return res[0];
        }

        private void SendVakilMoreData()
        {
            dialog = new MaterialDialog.Builder(getContext()).title("لطفا صبر کنید").content("درحال اعتبارسنجی اطلاعات شما").progress(true, 0).titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf","vazir.ttf").show();

            StringRequest moreDataRequest=new StringRequest(Request.Method.POST, USER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("Technocell:UserUpdated"))
                    {
                        Log.e("hauououou",response.toString());
                        dialog.dismiss();
                        new MaterialDialog.Builder(getContext()).typeface("vazir.ttf","vazir.ttf")
                                .title("ثبت موفق").content("همکار گرامی ، اطلاعات شما با موفقیت ثبت شد")
                                .titleGravity(GravityEnum.END).contentGravity(GravityEnum.END)
                                .positiveText("متوجه شدم")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        //go to Hq
                                    }
                                }).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                        Log.e("ImageCode->",ParvaneImageEncoded);
                    Log.e("ImageCode->",ParvaneImageEncoded);

                    map.put("U_RqCode",generateRqCode(86639842,95632547));
                    map.put("U_RqType","updateVakilData");
                    map.put("U_ProfilePic",ParvaneImageEncoded);
                    map.put("U_ID",userPrefrence.getString("User_ID",null));
                    map.put("U_Address",mAddress.getText().toString());
                    map.put("U_KanoonType",VoKalaVSMoshaveran);
                    map.put("U_Expertise",mTakhasos.getText().toString());
                    map.put("U_Education",mTahselat.getText().toString());
                    map.put("U_LicenseNum",mPavanehNumber.getText().toString());


                    return map;
                }
            };
            RQ.add(moreDataRequest);
        }

        private boolean VakilMoreDataValidator()
        {
            if(TextUtils.isEmpty(mPavanehNumber.getText()) ||
                    TextUtils.isEmpty(mTahselat.getText()) ||
                    TextUtils.isEmpty(mTakhasos.getText()) ||
                    TextUtils.isEmpty(VoKalaVSMoshaveran) ||
                    TextUtils.isEmpty(ParvaneImageEncoded)){
                return false;
            }
            return true;
        }

        private void InitWhoAreYou(View rootView)
        {
            mVakilBtn=rootView.findViewById(R.id.mVakilBtn);
                    mMovakelBtn=rootView.findViewById(R.id.mMovakelBtn);

        }

        private void InitAddMoreDataVakil(View rootView)
        {
            visualUtility=new VisualUtility(getContext());
            mPrvanehImage=rootView.findViewById(R.id.mPrvanehImage);
            mSendMoreDataBtn=rootView.findViewById(R.id.mSendMoreDataBtn);

            mPavanehNumber=rootView.findViewById(R.id.mPavanehNumber);
            mTahselat=rootView.findViewById(R.id.mTahselat);
            mTakhasos=rootView.findViewById(R.id.mTakhasos);
            mTeleSabet=rootView.findViewById(R.id.mTeleSabet);

            mVokala=rootView.findViewById(R.id.mVokala);
            mMoshaveran=rootView.findViewById(R.id.mMoshaveran);
            mAddress=rootView.findViewById(R.id.mAddress);



        }

        private void InitLoginRegister(View rootView)
        {
            mName=rootView.findViewById(R.id.mName);
            mFamily=rootView.findViewById(R.id.mFamily);
            mNumber=rootView.findViewById(R.id.mNumber);
            mRegisterBtn=rootView.findViewById(R.id.mRegisterBtn);



        }

        private void InitCodeVerify(View rootView)
        {
            int[] timerRadius=new int[4];
            timerRadius[0]=20;timerRadius[1]=0;
            timerRadius[2]=20;timerRadius[3]=0;
            int[] twiceRadius=new int[4];
            twiceRadius[0]=0;twiceRadius[1]=5;
            twiceRadius[2]=0;twiceRadius[3]=5;

            mCodeCounter=rootView.findViewById(R.id.mCodeCounter);

            mVorodBtn=rootView.findViewById(R.id.mVorodBtn);
            mSendCodeTwice=rootView.findViewById(R.id.mSendCodeTwice);
            mTimerBtn=rootView.findViewById(R.id.mTimerBtn);
            mTimerBtn.setRadius(timerRadius);
            mSendCodeTwice.setRadius(twiceRadius);
            mVerifyCode=rootView.findViewById(R.id.mVerifyCode);
            mCodeCounter.setAnimationDuration(30000)
                    .countAnimation(50, 0);
            mCodeCounter.setCountAnimationListener(new CountAnimationTextView.CountAnimationListener() {
                @Override
                public void onAnimationStart(Object animatedValue) {

                }

                @Override
                public void onAnimationEnd(Object animatedValue) {
                    mSendCodeTwice.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    canSendTwice=true;
                }
            });
        }

        private void SendFirstUserData()
        {
            int t=0;
            Log.e("Tager->",String.valueOf(t+1));
            dialog = new MaterialDialog.Builder(getContext()).title("لطفا صبر کنید").content("درحال اعتبارسنجی اطلاعات شما").progress(true, 0).titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf","vazir.ttf").show();

            StringRequest firstDataRequest=new StringRequest(Request.Method.POST, USER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Response of add first",response.toString());
                    if(response.trim().equals("Technocell:Ok"))
                    {
                        dialog.dismiss();
                        Log.e("yayayay","ojokokjokj");
                        isVakil=userData.get("userType");
                        if(isVakil.equals("lawyer"))
                        {
                            Log.e("dadadad","fafafaf");
                            mViewPager.setCurrentItem(4);
                        }else {
                            Log.e("lkjhlkj","12313");

                            //go to hq
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    Log.e("DataFirstSend-->",userData.get("U_Name_Show")+userData.get("U_Family_Show")+userData.get("U_Telephone_Show")+userData.get("userType"));
                    map.put("U_RqCode",generateRqCode(2769,9563));
                    map.put("U_RqType","AddUser");
                        map.put("U_Type",userData.get("userType"));
                        map.put("U_Telephone",userData.get("U_Telephone_Show"));
                        map.put("U_Family",userData.get("U_Family_Show"));
                        map.put("U_Name",userData.get("U_Name_Show"));
                        map.put("U_ID",GenerateUserId());
                        return map;
                }
            };
            RQ.add(firstDataRequest);
        }

        private String GenerateUserId()
        {

            String id= "TVJ-".concat(generateRqCode(2769,9563)).concat(userData.get("U_Telephone_Show"));
            userDataEdit.putString("User_ID",id);
            userDataEdit.commit();
            Log.e("User id that>",id);
            return id;

        }

        private boolean CheckInput()
        {
            if(TextUtils.isEmpty(mName.getText()) ||
                    TextUtils.isEmpty(mFamily.getText()) ||
                    TextUtils.isEmpty(mNumber.getText()) )
            {
                return false;
            }
            return true;
        }

        private void SendSms()
        {
            dialog = new MaterialDialog.Builder(getContext()).title("لطفا صبر کنید").content("درحال اعتبارسنجی اطلاعات شما").progress(true, 0).titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf","vazir.ttf").show();
            StringRequest smsRequest=new StringRequest(Request.Method.POST, SMS_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Sms Response-->",response.toString());
                    dialog.dismiss();
                    mViewPager.setCurrentItem(2);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("userNumberTechnocell",userData.get("U_Telephone_Show"));
                    map.put("userKey",userData.get("smsCode"));

                    return map;
            }
        };
    RQ.add(smsRequest);
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
            return 4;
        }
    }
}
}
