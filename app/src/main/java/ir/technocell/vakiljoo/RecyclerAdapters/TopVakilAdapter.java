package ir.technocell.vakiljoo.RecyclerAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.technocell.vakiljoo.R;
import ir.technocell.vakiljoo.RecyclerItems.TopVakilsItem;

public class TopVakilAdapter extends RecyclerView.Adapter<TopVakilAdapter.MyViewHolder>{

    private List<TopVakilsItem> vakilList;

    public TopVakilAdapter(List<TopVakilsItem> vakilList) {
        this.vakilList = vakilList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.top_vakils, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        TopVakilsItem tops=vakilList.get(i);
        myViewHolder.mTVakilName.setText(tops.getName());
        myViewHolder.mTVakilTahselat.setText(tops.getTahselat());
        myViewHolder.mTVakilTakhasos.setText(tops.getTakhasos());
        myViewHolder.mTVakilRotbeh.setText(tops.getRotbeh());
        Log.e("Profile URL-->",tops.getProfilePic().toString());
        Picasso.get().load("http://"+tops.getProfilePic().toString()).into(myViewHolder.mTVakilImage);

    }

    @Override
    public int getItemCount() {
        return vakilList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTVakilName,mTVakilTahselat,mTVakilTakhasos,mTVakilRotbeh;

        public CircleImageView mTVakilImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTVakilName =  itemView.findViewById(R.id.mTVakilName);
            mTVakilTahselat =  itemView.findViewById(R.id.mTVakilTahselat);
            mTVakilRotbeh =  itemView.findViewById(R.id.mTVakilRotbeh);
            mTVakilTakhasos =  itemView.findViewById(R.id.mTVakilTakhasos);
            mTVakilImage =  itemView.findViewById(R.id.mTVakilImage);

        }
    }
}
