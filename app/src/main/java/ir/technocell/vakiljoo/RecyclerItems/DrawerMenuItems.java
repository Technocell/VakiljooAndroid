package ir.technocell.vakiljoo.RecyclerItems;

import android.widget.ImageView;
import android.widget.TextView;

public class DrawerMenuItems {

    private String Title;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public DrawerMenuItems(String title) {
        Title = title;
    }
}
