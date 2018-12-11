package ir.technocell.vakiljoo.RecyclerItems;

public class MySoalsItem {

    String Title,Text,Date,Group,ImageUrl;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public MySoalsItem(String title, String text, String date, String group, String imageUrl) {
        Title = title;
        Text = text;
        Date = date;
        Group = group;
        ImageUrl = imageUrl;
    }
}
