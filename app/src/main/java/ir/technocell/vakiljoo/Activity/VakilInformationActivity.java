package ir.technocell.vakiljoo.Activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import ir.technocell.vakiljoo.R;
import ir.technocell.vakiljoo.RecyclerAdapters.TopSoalsAdapter;
import ir.technocell.vakiljoo.RecyclerAdapters.TopVakilAdapter;
import ir.technocell.vakiljoo.RecyclerAdapters.TopVakilsRecyclerListener;
import ir.technocell.vakiljoo.RecyclerItems.TopSoalsItem;
import ir.technocell.vakiljoo.RecyclerItems.TopVakilsItem;
import mehdi.sakout.fancybuttons.FancyButton;

public class VakilInformationActivity extends AppCompatActivity {
       
       
        private ViewPager mVakilInfoPager;
        private PlaceholderFragment.SectionsPagerAdapter mSectionPageAdapter;
        
        private FancyButton mContactWays,mAboutMe;
        
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_vakil_information);
            Init();
            TopButtonsOperator();

        }


        private void Init()
        {
            mContactWays=findViewById(R.id.mContactWays);
            mAboutMe=findViewById(R.id.mAboutMe);
            mVakilInfoPager=findViewById(R.id.mVakilInfoPager);
            mSectionPageAdapter = new PlaceholderFragment.SectionsPagerAdapter(getSupportFragmentManager());
            
        }
    private void TopButtonsOperator()
    {
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



    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        private RequestQueue RQ;

        private TextView mAboutMeText;
        private FancyButton mSendAboutBtn;

        public PlaceholderFragment() {

        }


        public static HqActivity.PlaceholderFragment newInstance(int sectionNumber) {
            HqActivity.PlaceholderFragment fragment = new HqActivity.PlaceholderFragment();
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
                rootView[0] = inflater.inflate(R.layout.layout_fragment_a, container, false);

                return rootView[0];
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==1)
            {
                rootView[0] = inflater.inflate(R.layout.fragment_fragment_b, container, false);

                return rootView[0];
            }

            return rootView[0];
        }


        private void InitAboutFrag(View rootView)
        {
            mAboutMeText=rootView.findViewById(R.id.mAboutMeText);
            mSendAboutBtn=rootView.findViewById(R.id.mSendAboutBtn);
            mSendAboutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //sednaboutQues
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
                return HqActivity.PlaceholderFragment.newInstance(position + 1);
            }

            @Override
            public int getCount() {
                // Show 3 total pages.
                return 2;
            }
        }



    }





}
