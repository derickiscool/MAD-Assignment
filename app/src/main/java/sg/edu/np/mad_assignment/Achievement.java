package sg.edu.np.mad_assignment;

public class Achievement {
    private int imageID;
    private String textAchieved;
    private Boolean isAchieved;

    public Achievement(){

    }

    public void Achievement(int imageID){
        this.imageID = imageID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getTextAchieved() {
        return textAchieved;
    }

    public void setTextAchieved(String textAchieved) {
        this.textAchieved = textAchieved;
    }

    public Boolean getIsAchieved() {
        return isAchieved;
    }

    public void setIsAchieved(Boolean isAchieved) {
        this.isAchieved = isAchieved;
    }

    public Achievement(int imageID, String textAchieved){
        this.imageID = imageID;
        this.textAchieved = textAchieved;
        this.isAchieved = false;
    }
}
