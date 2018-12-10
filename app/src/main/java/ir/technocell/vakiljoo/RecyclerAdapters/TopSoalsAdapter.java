package ir.technocell.vakiljoo.RecyclerAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.technocell.vakiljoo.R;
import ir.technocell.vakiljoo.RecyclerItems.TopSoalsItem;

public class TopSoalsAdapter extends RecyclerView.Adapter<TopSoalsAdapter.MyViewHolder> {

    private List<TopSoalsItem> SoalList;

    public TopSoalsAdapter(List<TopSoalsItem> soalList) {
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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        TopSoalsItem tops=SoalList.get(i);
        myViewHolder.mSKolase.setText(tops.getSoalTitle());
        myViewHolder.mSNameFamily.setText(tops.getNameoFamily());
        myViewHolder.mSDate.setText(tops.getDate());
        myViewHolder.mSId.setText(tops.getId());

    }

    @Override
    public int getItemCount() {
        return SoalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mSKolase,mSNameFamily,mSDate,mSId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mSKolase =  itemView.findViewById(R.id.mSKolase);
            mSNameFamily =  itemView.findViewById(R.id.mSNameFamily);
            mSDate =  itemView.findViewById(R.id.mSDate);
            mSId =  itemView.findViewById(R.id.mSId);
        }
    }
}
