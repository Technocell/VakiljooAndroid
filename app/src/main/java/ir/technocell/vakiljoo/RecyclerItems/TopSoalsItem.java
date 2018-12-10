package ir.technocell.vakiljoo.RecyclerItems;

public class TopSoalsItem {

   private String SoalTitle,NameoFamily,Date,id;

    public TopSoalsItem(String soalTitle, String nameoFamily, String date, String id) {
        SoalTitle = soalTitle;
        NameoFamily = nameoFamily;
        Date = date;
        this.id = id;
    }

    public String getSoalTitle() {
        return SoalTitle;
    }

    public void setSoalTitle(String soalTitle) {
        SoalTitle = soalTitle;
    }

    public String getNameoFamily() {
        return NameoFamily;
    }

    public void setNameoFamily(String nameoFamily) {
        NameoFamily = nameoFamily;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
