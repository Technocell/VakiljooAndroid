package ir.technocell.vakiljoo.Activity;

import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.technocell.vakiljoo.HqClient;
import ir.technocell.vakiljoo.R;
import ir.technocell.vakiljoo.VisualUtility;
import mehdi.sakout.fancybuttons.FancyButton;

public class VakilInformationActivity extends AppCompatActivity {


    private ViewPager mVakilInfoPager;
    private PlaceholderFragment.SectionsPagerAdapter mSectionPageAdapter;
    private FancyButton mContactWays, mAboutMe;
    private TextView mDName,mDFamily;
    CircleImageView profile_image;
    View view;
    ImageView mMohasebat,mMap,mChats,mTrhSoal,mHome,mProfile;
    HashMap<String, String> hashMap = new HashMap<>();
    VisualUtility visualUtility;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vakil_information);
        Init();
        TopButtonsOperator();
        SetupHeader();
        InitBottomMenu();
        BottomMenuOperations();
        InitDrawerOperations(GetDaMapData());

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

            mDName = findViewById(R.id.mDName);
            mDFamily = findViewById(R.id.mDFamily);
            mDName.setText(hashMap.get("U_Name_Show").toString());
            mDFamily.setText(hashMap.get("U_Family_Show").toString());
            Picasso.get().load(hashMap.get("U_ProfilePic").toString()).into(profile_image);

         }catch (Exception e)
        {
            e.printStackTrace();
        }

    }



    private void InitBottomMenu()
    {

        view=findViewById(R.id.mBottomMenuMotherClient);
      //  mMap=view.findViewById(R.id.mMap);
        mChats=view.findViewById(R.id.mChats);
        mHome=view.findViewById(R.id.mHome);
        mProfile=view.findViewById(R.id.mProfile);
    }
    private void BottomMenuOperations()
    {

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Tager:->","yes");
                Intent iGOToActivity=new Intent(VakilInformationActivity.this,HqClient.class);
                startActivity(iGOToActivity);
            }
        });

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(VakilInformationActivity.this,"شما اینجا هستید",Toast.LENGTH_SHORT).show();
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
        mHeaderTitle.setText("اطلاعات شما");
        mHBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BACK = new Intent(VakilInformationActivity.this,HqClient.class);
                startActivity(BACK);
            }
        });
    }


    private void Init() {
        new VisualUtility(this).InitCalliGraphy();
        profile_image=findViewById(R.id.profile_image);

        mContactWays = findViewById(R.id.mContactWays);
        mAboutMe = findViewById(R.id.mAboutMe);
        mVakilInfoPager = findViewById(R.id.mVakilInfoPager);
        mSectionPageAdapter = new PlaceholderFragment.SectionsPagerAdapter(getSupportFragmentManager());

        mVakilInfoPager.setAdapter(mSectionPageAdapter);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "تصویر پروانه را انتخاب کنید"), 276);
            }
        });
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(VakilInformationActivity.this);
        editor=sharedPreferences.edit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 276) {
            if (data != null) {
                try {
                  editor.putString("userImage",visualUtility.encodeTobase64(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData())));
                  editor.commit();
                    profile_image.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData()));
                    Toasty.info(VakilInformationActivity.this,"تصویر پروفایل انتخاب شد",Toast.LENGTH_SHORT,true).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


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


    public static class PlaceholderFragment extends DialogFragment
    {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private EditText U_AboutVakil;
        private RequestQueue RQ;
        private SharedPreferences userData;
        private FancyButton mSendAboutBtn,mSendContactBtn;
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
                rootView[0] = inflater.inflate(R.layout.layout_fragment_a, container, false);
                InitAboutFrag(rootView[0]);
                return rootView[0];
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                rootView[0] = inflater.inflate(R.layout.fragment_fragment_b, container, false);
                InitVakilContact(rootView[0]);
                SetTimers();
                SetWeedDays();
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
            mSendContactBtn=rootView.findViewById(R.id.mSendContactBtn);
            sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getContext());
            editor=sharedPreferences.edit();
            editor.remove("userImage");
            editor.commit();

            mSendContactBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new MaterialDialog.Builder(getContext()).title("ثبت و ارسال").content("آیا از ازسال مطمئن هستید؟")
                            .titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf", "vazir.ttf")
                            .positiveText("بله").negativeText("خیر").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            SendVakilTimes();
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




        private void SetTimers()
        {

            mHMFrom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                     timePickerDialog = new TimePickerDialog(getContext(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    mHMFrom.setText(hourOfDay + ":" + minute);
                                }
                            }, 11, 30, true);
                    timePickerDialog.show();

                }
            });
            mHMTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    timePickerDialog = new TimePickerDialog(getContext(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    mHMTo.setText(hourOfDay + ":" + minute);
                                }
                            }, 11, 30, true);
                    timePickerDialog.show();

                }
            });
            mHAFrom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    timePickerDialog = new TimePickerDialog(getContext(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    mHAFrom.setText(hourOfDay + ":" + minute);
                                }
                            }, 11, 30, true);
                    timePickerDialog.show();

                }
            });

            mHATo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    timePickerDialog = new TimePickerDialog(getContext(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    mHATo.setText(hourOfDay + ":" + minute);
                                }
                            }, 11, 30, true);
                    timePickerDialog.show();

                }
            });
            mCMFrom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    timePickerDialog = new TimePickerDialog(getContext(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    mCMFrom.setText(hourOfDay + ":" + minute);
                                }
                            }, 11, 30, true);
                    timePickerDialog.show();

                }
            });
            mCMTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    timePickerDialog = new TimePickerDialog(getContext(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    mCMTo.setText(hourOfDay + ":" + minute);
                                }
                            }, 11, 30, true);
                    timePickerDialog.show();

                }
            });
            mCAFrom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    timePickerDialog = new TimePickerDialog(getContext(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    mCAFrom.setText(hourOfDay + ":" + minute);
                                }
                            }, 11, 30, true);
                    timePickerDialog.show();

                }
            });
            mCATo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    timePickerDialog = new TimePickerDialog(getContext(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    mCATo.setText(hourOfDay + ":" + minute);
                                }
                            }, 11, 30, true);
                    timePickerDialog.show();

                }
            });

        }

        private void SetWeedDays(){
            mShanbeh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked())
                    {
                        WeekDays[0]="1";

                    }else {
                        WeekDays[0]="0";

                    }
                }
            });
            mYekShanbeh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked())
                    {
                        WeekDays[1]="1";

                    }else {
                        WeekDays[1]="0";

                    }
                }
            });
            mDoShanbeh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked())
                    {
                        WeekDays[2]="1";

                    }else {
                        WeekDays[2]="0";

                    }
                }
            });

            mDoShanbeh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked())
                    {
                        WeekDays[3]="1";

                    }else {
                        WeekDays[3]="0";

                    }
                }
            });
            mSeShanbeh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked())
                    {
                        WeekDays[4]="1";

                    }else {
                        WeekDays[4]="0";

                    }
                }
            });
            mChaharShanbeh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked())
                    {
                        WeekDays[5]="1";

                    }else {
                        WeekDays[5]="0";

                    }
                }
            });

            mPanjShanbeh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked())
                    {
                        WeekDays[6]="1";

                    }else {
                        WeekDays[6]="0";

                    }
                }
            });
            mJomeh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked())
                    {
                        WeekDays[7]="1";

                    }else {
                        WeekDays[7]="0";

                    }
                }
            });

        }

        private void SendVakilTimes()
        {
            request=new StringRequest(Request.Method.POST,USER_URL , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("Technocell:Ok")) {
                        Log.e("OnlineTime-->", "Setted");

                        new MaterialDialog.Builder(getContext()).title("ارسال موفق").content("ارسال با موفقیت انجام شد")
                                .titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf", "vazir.ttf")
                                .positiveText("تایید").onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        }).show();


                    } else {
                        new MaterialDialog.Builder(getContext()).title("ارسال ناموفق").content("ارسال با مشکل مواجه شد")
                                .titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf", "vazir.ttf")
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

                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("U_RqType", "setVakilTime");
                    map.put("U_RqCode", generateRqCode(86639842, 95632547));
                    map.put("U_WeekDay", WeekDays[0]+","+WeekDays[1]+","+WeekDays[2]+","+
                            WeekDays[3]+","+WeekDays[4]+","+WeekDays[5]+","+WeekDays[6]+","+WeekDays[7]);

                    map.put("U_HMorning",mHMFrom.getText().toString());
                    map.put("U_HAfternoon", mHAFrom.getText().toString());
                    map.put("U_TMorning", mCMFrom.getText().toString());
                    map.put("U_Tafternoon", mCAFrom.getText().toString());

                    map.put("U_HMorningE", mHMTo.getText().toString());
                    map.put("HAfternoonE", mHATo.getText().toString());
                    map.put("U_TMorningE", mCMTo.getText().toString());
                    map.put("U_TafternoonE", mCATo.getText().toString());

                    map.put("U_ProfilePic", sharedPreferences.getString("userImage","not"));

                    return map;
                }
            };
            RQ.add(request);
        }





        private void SendInfoVakil() {
            request = new StringRequest(Request.Method.POST, USER_URL, new Response.Listener<String>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(String response) {
                    if (response.equals("Technocell:Ok")) {
                        Log.e("OnlineTime-->", "Setted");

                        new MaterialDialog.Builder(getContext()).title("ارسال موفق").content("ارسال با موفقیت انجام شد")
                                .titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf", "vazir.ttf")
                                .positiveText("تایید").onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        }).show();


                    } else {
                        new MaterialDialog.Builder(getContext()).title("ارسال ناموفق").content("ارسال با مشکل مواجه شد")
                                .titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf", "vazir.ttf")
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
                    map.put("U_RqType", "setVakilAbout");
                    map.put("U_RqCode", generateRqCode(86639842, 95632547));
                    map.put("U_ID", GetUserID());
                    map.put("U_AboutVakil", U_AboutVakil.getText().toString());
                    return map;
                }
            };
            RQ.add(request);
        }
        private String GetUserID() {
            return userData.getString("User_ID", "no");
        }

        private void InitAboutFrag(View rootView) {
            mSendAboutBtn = rootView.findViewById(R.id.mSendAboutBtn);
            U_AboutVakil = rootView.findViewById(R.id.mAboutMeText);
            mSendAboutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new MaterialDialog.Builder(getContext()).title("ثبت و ارسال").content("آیا از ازسال مطمئن هستید؟")
                            .titleGravity(GravityEnum.END).contentGravity(GravityEnum.END).typeface("vazir.ttf", "vazir.ttf")
                            .positiveText("بله").negativeText("خیر").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            SendInfoVakil();
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



    public static String generateRqCode(int min, int max) {
        String finalCode;
        Random r = new Random();
        int requestCode = r.nextInt(max - min + 1) + min;
        finalCode = String.valueOf(requestCode);
        return finalCode;
    }



}
