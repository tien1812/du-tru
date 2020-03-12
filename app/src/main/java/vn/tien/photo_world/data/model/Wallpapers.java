package vn.tien.photo_world.data.model;

public class Wallpapers {
    private String mLinkImage ;
    private String mAuthor ;

    public Wallpapers(String linkImage, String author) {
        this.mLinkImage = linkImage;
        mAuthor = author;
    }

    public String getmLinkImage() {
        return mLinkImage;
    }

    public void setmLinkImage(String mLinkImage) {
        this.mLinkImage = mLinkImage;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }
}
