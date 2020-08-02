package sg.edu.np.mad_assignment;

public class Events {
    String EVENT,DATE,MONTH;

    public String getEVENT() {
        return EVENT;
    }

    public void setEVENT(String EVENT) {
        this.EVENT = EVENT;
    }


    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }


    public Events(String EVENT, String DATE, String MONTH) {
        this.EVENT = EVENT;
        this.DATE = DATE;
        this.MONTH = MONTH;
    }
}
