package sg.edu.np.mad_assignment;

import java.util.ArrayList;

public class Member {
    private String PhoneNumber;
    private String Username;
    private String Email;
    private String Password;
    private String Bio;
    private String Name;
    private ArrayList<String> Tasks;
    private ArrayList<String> Achievements;

    public Member(){
}

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<String> getAchievements() {
        return Achievements;
    }

    public void setAchievements(ArrayList<String> achievements) {
        Achievements = achievements;
    }

    public ArrayList<String> getTasks() {
        return Tasks;
    }

    public void setTasks(ArrayList<String> tasks) {
        Tasks = tasks;
    }
}
