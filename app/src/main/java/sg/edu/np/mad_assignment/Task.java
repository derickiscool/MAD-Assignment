package sg.edu.np.mad_assignment;

public class Task {
    private String text;
    private Achievement achievement;

    public Task(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public Task(){

    }

    private Boolean gotoupload;

    public Boolean getGotoupload() {
        return gotoupload;
    }

    public void setGotoupload(Boolean gotoupload) {
        this.gotoupload = gotoupload;
    }
}
