package sg.edu.np.mad_assignment;

public class Achievement {
    private int imageID;

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

    public Achievement(int imageID){
        imageID = this.imageID;
    }
}
