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


    public Boolean getIsAchieved() {
        return isAchieved;
    }

    public void setIsAchieved(Boolean isAchieved) {
        this.isAchieved = isAchieved;
    }

    public Achievement(int imageID){
        this.imageID = imageID;
        this.isAchieved = false;
    }
}
