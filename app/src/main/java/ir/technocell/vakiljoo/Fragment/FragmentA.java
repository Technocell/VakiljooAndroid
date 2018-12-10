package ir.technocell.vakiljoo.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.technocell.vakiljoo.R;

public class FragmentA  extends android.support.v4.app.Fragment{
   // private RecyclerView recyclerView;
    //private AdressAdapter adressAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_a, container, false);
    }

  /*  @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       /* recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        adressAdapter=new AdressAdapter(getActivity());
        recyclerView.setAdapter(adressAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }*/
}
