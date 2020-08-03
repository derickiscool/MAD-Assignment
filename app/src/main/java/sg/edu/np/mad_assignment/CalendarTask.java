package sg.edu.np.mad_assignment;

public class CalendarTask {
    private String taskName;
    private String dateComplete;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDateComplete() {
        return dateComplete;
    }

    public void setDateComplete(String dateComplete) {
        this.dateComplete = dateComplete;
    }

    public CalendarTask(String taskName, String dateComplete, String imgUrl) {
        this.taskName = taskName;
        this.dateComplete = dateComplete;
        this.imgUrl = imgUrl;
    }
}
