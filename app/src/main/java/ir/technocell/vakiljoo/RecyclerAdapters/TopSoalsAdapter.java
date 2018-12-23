package ir.technocell.vakiljoo.RecyclerAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.technocell.vakiljoo.R;
import ir.technocell.vakiljoo.RecyclerItems.TopSoalsItem;

public class TopSoalsAdapter extends RecyclerView.Adapter<TopSoalsAdapter.MyViewHolder> {

    private List<TopSoalsItem> SoalList;
    private Context context;
    public TopSoalsAdapter(List<TopSoalsItem> soalList, Context context) {
        this.context=context;
        SoalList = soalList;
    }




    @NonNull
    @Override
    public TopSoalsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.top_soals, viewGroup, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        TopSoalsItem tops=SoalList.get(i);
        myViewHolder.mSKolase.setText(tops.getSoalKholase());
        myViewHolder.mSNameFamily.setText(tops.getNameoFamily());
        myViewHolder.mSDate.setText(tops.getDate());
        myViewHolder.mSId.setText(tops.getId());
        myViewHolder.mSTitle.setText(tops.getSoalTitle());
        if(isPublicOperation(tops.getIsPublic()))
        {
            myViewHolder.mSNameFamily.setText(tops.getNameoFamily());
            Picasso.get().load("http://vakiljoo.com/AppData/Core/ProfilePics/"+tops.getUserId()+".jpeg").placeholder(R.drawable.vakile_profile).into(myViewHolder.mClientImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                   // Picasso.get().load("android.resource://ir.technocell.vakiljoo/drawable/vakile_profile.jpeg").into(myViewHolder.mClientImage);

                }
            });
        }else {
            myViewHolder.mSNameFamily.setText("ناشناس");
            Picasso.get().load("android.resource://ir.technocell.vakiljoo/drawable/vakile_profile.jpeg").placeholder(R.drawable.vakile_profile).into(myViewHolder.mClientImage);
        }


    }

    private boolean isPublicOperation(String isPublic)
    {
            if(isPublic.equals("True") || isPublic.equals("true"))
            {
                return true;
            }else {
                return false;
            }
    }

    @Override
    public int getItemCount() {
        return SoalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mSKolase,mSNameFamily,mSDate,mSId,mSTitle;
        public CircleImageView mClientImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mSKolase =  itemView.findViewById(R.id.mSKolase);
            mSNameFamily =  itemView.findViewById(R.id.mSNameFamily);
            mSDate =  itemView.findViewById(R.id.mSDate);
            mSId =  itemView.findViewById(R.id.mSId);
            mSTitle=itemView.findViewById(R.id.mSTitle);
            mClientImage=itemView.findViewById(R.id.mClientImage);

        }
    }
}
