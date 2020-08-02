package sg.edu.np.mad_assignment;

import java.util.ArrayList;

public class Member {
    private String PhoneNumber;
    private String Username;
    private String Email;
    private String Password;
    private String Bio;
    private String Name;
    private String ProfilePicture;
    private ArrayList<Task> Tasks;
    private ArrayList<Achievement> Achievements;

    public Member(String name, String bio, String num, String username, String email, String password, String pfp, ArrayList<Task> tList, ArrayList<Achievement> aList)
    {
        this.PhoneNumber = num;
        this.Username = username;
        this.Email = email;
        this.Password = password;
        this.Name = name;
        this.Bio = bio;
        this.ProfilePicture = pfp;
        this.Tasks = tList;
        this.Achievements = aList;
    }

    public Member(){ }

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

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    public ArrayList<Achievement> getAchievements() {
        return Achievements;
    }

    public void setAchievements(ArrayList<Achievement> achievements) {
        Achievements = achievements;
    }

    public ArrayList<Task> getTasks() {
        return Tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        Tasks = tasks;
    }
}
