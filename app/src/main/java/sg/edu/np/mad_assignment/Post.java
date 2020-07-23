package sg.edu.np.mad_assignment;

public class Post {
    private String mCaption;
    private String mImageUrl;
    private String mUsername;

    public Post() {
        // empty constructor needed
    }

    public  Post(String caption, String imageUrl, String username)
    {
        if(caption.trim().equals("")) // if there is no caption
        {
            caption = "New Post!";
        }
        mCaption = caption;
        mImageUrl = imageUrl;
        mUsername = username;
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

}
