package ir.technocell.vakiljoo.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ir.technocell.vakiljoo.R;

public class VakilInformationActivity extends AppCompatActivity {
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
            tableLayout.setupWithViewPager(viewPager);

        }
    }
