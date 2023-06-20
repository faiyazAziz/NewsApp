public class NewsModel {
    private String mTitle;
    private String mImgUrl;

    public NewsModel(String title,String imgUrl){      mTitle = title;
        mImgUrl = imgUrl;
    }
    public String getTitle(){return mTitle;}
    public String getmImgUrl(){return mImgUrl;}
}
