package ir.technocell.vakiljoo.RecyclerItems;

public class TopVakilsItem {

    private String name,tahselat,takhasos,rotbeh,profilePic,mVakilAbout,mIsOnline,VakilID;

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfilePic() {

        return profilePic;
    }

    public String getmVakilAbout() {
        return mVakilAbout;
    }

    public void setmVakilAbout(String mVakilAbout) {
        this.mVakilAbout = mVakilAbout;
    }

    public String getmIsOnline() {
        return mIsOnline;
    }

    public void setmIsOnline(String mIsOnline) {
        this.mIsOnline = mIsOnline;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVakilID() {
        return VakilID;
    }

    public void setVakilID(String vakilID) {
        VakilID = vakilID;
    }

    public TopVakilsItem(String name, String tahselat, String takhasos, String rotbeh, String profilePic, String mVakilAbout, String mIsOnline, String vakilID) {
        this.name = name;
        this.tahselat = tahselat;
        this.takhasos = takhasos;
        this.rotbeh = rotbeh;
        this.profilePic = profilePic;
        this.mVakilAbout = mVakilAbout;
        this.mIsOnline = mIsOnline;
        VakilID = vakilID;
    }

    private void SetName(String name)
    {
        this.name=name;
    }
    private void setTahselat(String tahselat)
    {
        this.tahselat=tahselat;

    }
    private void setTakhasos(String takhasos)
    {
        this.takhasos=takhasos;
    }
    private void setRotbeh(String rotbeh)
    {
        this.rotbeh=rotbeh;
    }

    public String getName() {
        return name;
    }

    public String getTahselat() {
        return tahselat;
    }

    public String getTakhasos() {
        return takhasos;
    }

    public String getRotbeh() {
        return rotbeh;
    }
}
