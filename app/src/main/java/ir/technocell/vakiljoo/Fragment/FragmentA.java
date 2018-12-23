package ir.technocell.vakiljoo.Fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.technocell.vakiljoo.R;

public class FragmentA  extends Fragment {
   // private RecyclerView recyclerView;
    //private AdressAdapter adressAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_a, container, false);
    }

}
