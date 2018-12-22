package ir.technocell.vakiljoo.RecyclerAdapters;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.technocell.vakiljoo.R;
import ir.technocell.vakiljoo.RecyclerItems.DrawerMenuItems;

public class DrawerMenuAdapter extends RecyclerView.Adapter<DrawerMenuAdapter.MyViewHolder>{

    public List<DrawerMenuItems> drawerMenuItems;

    Uri uri;

    public DrawerMenuAdapter(List<DrawerMenuItems> drawerMenuItems) {
        this.drawerMenuItems = drawerMenuItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.drawer_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        DrawerMenuItems menuItems=drawerMenuItems.get(i);
        myViewHolder.mDTitle.setText(menuItems.getTitle().toString());
        Picasso.get().load(uri.parse("android.resource://ir.technocell.vakiljoo/drawable/d_"+i+".jpeg")).into(myViewHolder.mDIcon);
    }

    @Override
    public int getItemCount() {
        return 0;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mDTitle;
        public ImageView mDIcon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mDIcon=itemView.findViewById(R.id.mDIcon);
            mDTitle=itemView.findViewById(R.id.mDTitle);


        }
    }
}
