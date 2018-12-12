package ir.technocell.vakiljoo.DataModel;


public class MyQuestionModel {
    private String profile;
    private String title;
    private  String content;
    private String date;
    private String group;

    private String name;

    public MyQuestionModel(String profile, String title, String content, String date, String group, String name) {
        this.profile = profile;
        this.title = title;
        this.content = content;
        this.date = date;
        this.group = group;
        this.name = name;

    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

