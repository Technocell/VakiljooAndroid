package ir.technocell.vakiljoo.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import ir.technocell.vakiljoo.Adapter.ViewPagerAdapter;
import ir.technocell.vakiljoo.R;

public class VakilInformationActivity extends AppCompatActivity {
        private ViewPagerAdapter adapter;
        private ViewPager viewPager;
        private android.support.design.widget.TabLayout tableLayout;
        private View tab1,tab2;
        private Button button;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_vakil_information);
            viewPager=findViewById(R.id.view_pager);
            tableLayout= findViewById(R.id.view_pager_tab);
            adapter=new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            tableLayout.setupWithViewPager(viewPager);
            tab1= LayoutInflater.from(this).inflate(R.layout.layout_tab_custom_b, null);
            button=tab1.findViewById(R.id.call);
            //button.setText(getString(R.string.name));
            //button.setBackgroundResource(R.color.colorAccent);
            tableLayout.getTabAt(0).setCustomView(tab1);
            tab2=LayoutInflater.from(this).inflate(R.layout.layout_tab_custom, null);
            button=tab2.findViewById(R.id.call);
            //button.setText(getString(R.string.name));
            //button.setBackgroundResource(R.color.colorPrimary);
            tableLayout.getTabAt(1).setCustomView(tab2);
        }
    }
