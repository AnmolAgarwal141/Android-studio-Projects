package model;

import com.google.firebase.Timestamp;



public class Journal {
    private String Title;
    private String Thought;
    private String imageUri;
    private String userid;
    private String username;
    private Timestamp timeadded;
    private boolean isSelected=false;
    public boolean showcheckbox=false;

    public Journal(){}

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
    public void setSelected(boolean selected){
        isSelected=selected;
    }
    public Boolean isSelected(){
        return isSelected;
    }

    public String getThought() {
        return Thought;
    }

    public void setThought(String thought) {
        Thought = thought;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getTimeadded() {
        return timeadded;
    }

    public void setTimeadded(Timestamp timeadded) {
        this.timeadded = timeadded;
    }

    public Journal(String title, String thought, String imageUri, String userid, String username, Timestamp timeadded) {
        Title = title;
        Thought = thought;
        this.imageUri = imageUri;
        this.userid = userid;
        this.username = username;
        this.timeadded = timeadded;
    }
}
