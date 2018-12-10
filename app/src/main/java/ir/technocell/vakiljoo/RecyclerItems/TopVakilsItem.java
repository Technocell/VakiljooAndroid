package ir.technocell.vakiljoo.RecyclerItems;

public class TopVakilsItem {

    private String name,tahselat,takhasos,rotbeh,profilePic;

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfilePic() {

        return profilePic;
    }

    public TopVakilsItem(String name, String tahselat, String takhasos, String rotbeh, String profilePic) {
        this.name = name;
        this.tahselat = tahselat;
        this.takhasos = takhasos;
        this.rotbeh = rotbeh;
        this.profilePic=profilePic;
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
