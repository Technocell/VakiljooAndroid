package ir.technocell.vakiljoo.RecyclerItems;

public class TopSoalsItem {

   private String SoalTitle,SoalKholase,NameoFamily,Date,id,isPublic,userId;

    public String getSoalKholase() {
        return SoalKholase;
    }

    public void setSoalKholase(String soalKholase) {
        SoalKholase = soalKholase;
    }

    public TopSoalsItem(String soalTitle, String soalKholase, String nameoFamily, String date, String id, String isPublic, String userId) {
        SoalTitle = soalTitle;
        SoalKholase = soalKholase;
        NameoFamily = nameoFamily;
        Date = date;
        this.id = id;
        this.isPublic = isPublic;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
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
