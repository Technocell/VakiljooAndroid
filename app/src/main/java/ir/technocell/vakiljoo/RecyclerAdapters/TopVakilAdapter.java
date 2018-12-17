package ir.technocell.vakiljoo.RecyclerAdapters;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.technocell.vakiljoo.R;
import ir.technocell.vakiljoo.RecyclerItems.TopVakilsItem;

public class TopVakilAdapter extends RecyclerView.Adapter<TopVakilAdapter.MyViewHolder>{

    private List<TopVakilsItem> vakilList;
    private Context context;
    public TopVakilAdapter(List<TopVakilsItem> vakilList, Context context) {
        this.vakilList = vakilList;
        this.context=context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.top_vakils, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        TopVakilsItem tops=vakilList.get(i);
        myViewHolder.mTVakilName.setText(tops.getName());
        myViewHolder.mTVakilTahselat.setText(tops.getTahselat());
        myViewHolder.mTVakilTakhasos.setText(tops.getTakhasos());
        myViewHolder.mTVakilRotbeh.setText(tops.getRotbeh());
        myViewHolder.mVakilAbout.setText(tops.getmVakilAbout());
        Log.e("Profile URL-->",tops.getProfilePic().toString());
        Picasso.get().load("http://"+tops.getProfilePic().toString()).placeholder(R.drawable.vakile_profile).into(myViewHolder.mTVakilImage);

        if(IsOnline(tops.getmIsOnline()))
        {
            myViewHolder.mIsOnline.setBackground(context.getResources().getDrawable(R.drawable.online));
        }else {
            myViewHolder.mIsOnline.setBackground(context.getResources().getDrawable(R.drawable.offline));
        }


    }

    private boolean IsOnline(final String uid) {
        String TIME_URL = "http://vakiljoo.com/AppData/Timer.php";
        RequestQueue RQ=Volley.newRequestQueue(context);
        final boolean[] IsOnline = new boolean[1];
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, TIME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Technocell:offline"))
                {
                    IsOnline[0] =false;
                }else {
                    IsOnline[0] =true;

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();


                map.put("T_RqCode", generateRqCode(86639842, 95632547));
                map.put("T_RqType", "isOnline");
                map.put("U_ID", uid);


                return map;

            }
        };
        RQ.add(stringRequest);
        return IsOnline[0];
    }
    private String generateRqCode(int min, int max) {
        String finalCode;
        Random r = new Random();
        int requestCode = r.nextInt(max - min + 1) + min;
        finalCode = String.valueOf(requestCode);
        return finalCode;
    }

    /*
    private void IsOnline(String lastDate)
    {
        String time2="00:15";
        Date date1 = null,date2=null;
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
try {

     date1 = timeFormat.parse(lastDate);
     date2 = timeFormat.parse(time2);
}catch (Exception e){
    e.printStackTrace();
}
        long sum = date1.getTime() + date2.getTime();

        String date3 = timeFormat.format(new Date(sum));
     //  if(date3<)
    }
    */

    @Override
    public int getItemCount() {
        return vakilList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTVakilName,mTVakilTahselat,mTVakilTakhasos,mTVakilRotbeh,mVakilAbout;
        public CircleImageView mIsOnline;

        public CircleImageView mTVakilImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTVakilName =  itemView.findViewById(R.id.mTVakilName);
            mTVakilTahselat =  itemView.findViewById(R.id.mTVakilTahselat);
            mTVakilRotbeh =  itemView.findViewById(R.id.mTVakilRotbeh);
            mTVakilTakhasos =  itemView.findViewById(R.id.mTVakilTakhasos);
            mTVakilImage =  itemView.findViewById(R.id.mTVakilImage);
            mVakilAbout=itemView.findViewById(R.id.mVakilAbout);
            mIsOnline=itemView.findViewById(R.id.mIsOnline);

        }
    }
}
