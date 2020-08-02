package sg.edu.np.mad_assignment;

import com.google.firebase.database.Exclude;

public class Post {
    private String mCaption;
    private String mImageUrl;
    private String mUsername;
    private String mName;
    private String mProfileUrl;
    private String mKey;

    public Post() {
        // empty constructor needed
    }

    public  Post(String caption, String imageUrl, String username, String name, String profileUrl)
    {
        if(caption.trim().equals("")) // if there is no caption
        {
            caption = "New Post!";
        }
        mCaption = caption;
        mImageUrl = imageUrl;
        mUsername = username;
        mName = name;
        mProfileUrl = profileUrl;
    }

    public String getCaption()
    {
        return mCaption;
    }
    public void setCaption(String name)
    {
        mCaption = name;
    }
    public String getImageUrl()
    {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl)
    {
        mImageUrl = imageUrl;
    }
    public String getUsername()
    {
        return  mUsername;
    }
    public void setUsername(String username)
    {
        mUsername = username;
    }
    public String getName()
    {
        return  mName;
    }
    public void setName(String name)
    {
        mName = name;
    }
    public String getProfileUrl()
    {
        return  mProfileUrl;
    }
    public void setProfileUrl(String profileUrl)
    {
        mProfileUrl = profileUrl;
    }
    @Exclude // not needed in firebase
    public String getKey()
    {
        return mKey;
    }
    @Exclude
    public void setKey(String key)
    {
        mKey = key;
    }

}
