package sg.edu.np.mad_assignment;

public class Achievement {
    private String imageUrl;
    private String textAchieved;
    private Boolean isAchieved;

    public Achievement(){

    }

    public void Achievement(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageID(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public Boolean getIsAchieved() {
        return isAchieved;
    }

    public void setIsAchieved(Boolean isAchieved) {
        this.isAchieved = isAchieved;
    }

    public Achievement(String imageUrl){
        this.imageUrl = imageUrl;
        this.isAchieved = false;
    }
}
