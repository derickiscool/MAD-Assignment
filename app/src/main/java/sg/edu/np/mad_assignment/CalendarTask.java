package sg.edu.np.mad_assignment;

public class CalendarTask {
    private String taskName;
    private String dateComplete;

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

    public CalendarTask(String taskName, String dateComplete) {
        this.taskName = taskName;
        this.dateComplete = dateComplete;
    }
}
