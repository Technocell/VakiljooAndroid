package ir.technocell.vakiljoo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class BottomMenuCustomClass  {

    Context mContext;
    LayoutInflater inflater;
    View view;
    ImageView mMohasebat,mMap,mChats,mTrhSoal,mHome,mProfile;
    Intent iGOToActivity;

    public BottomMenuCustomClass(Context mContext) {
        this.mContext = mContext;
    }

    public void InitMovakel()
    {
         inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMohasebat=view.findViewById(R.id.mMohasebat);
        mMap=view.findViewById(R.id.mMap);
        mChats=view.findViewById(R.id.mChats);
        mTrhSoal=view.findViewById(R.id.mTrhSoal);
        mHome=view.findViewById(R.id.mHome);
    }
    public void IniVakil()
    {
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.bottom_layout__hq_client,null);
        mChats=view.findViewById(R.id.mChats);
        mHome=view.findViewById(R.id.mHome);
        mProfile=view.findViewById(R.id.mProfile);

    }

    public void GoToMyProfile()
    {
        mMohasebat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //iGOToActivity=new Intent(mContext,mohasebat);
            }
        });
    }

    public void GoToVakilChat()
    {
        mMohasebat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //iGOToActivity=new Intent(mContext,mohasebat);
            }
        });
    }


    public void GoToVakilHome()
    {
        mMohasebat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iGOToActivity=new Intent(mContext,HqClient.class);
                mContext.startActivity(iGOToActivity);
            }
        });
    }



    public void GoToMohasebat()
    {
        mMohasebat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //iGOToActivity=new Intent(mContext,mohasebat);
            }
        });
    }
    public void GoToHome()
    {
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iGOToActivity=new Intent(mContext.getApplicationContext(),HqClient.class);
                mContext.startActivity(iGOToActivity);
            }
        });
    }

    public void GoToTarhSoal()
    {
        mTrhSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iGOToActivity=new Intent(mContext.getApplicationContext(),UserQuestionsActivity.class);
                mContext.startActivity(iGOToActivity);
            }
        });
    }

    public void GoToChats()
    {
        mChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  iGOToActivity=new Intent(mContext,UserQuestionsActivity.class);
              //  mContext.startActivity(iGOToActivity);
            }
        });
    }
    public void GoToMap()
    {
        mMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //iGOToActivity=new Intent(mContext,mm.class);
               // mContext.startActivity(iGOToActivity);
            }
        });
    }


}
