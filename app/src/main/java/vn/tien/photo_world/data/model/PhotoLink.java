package vn.tien.photo_world.data.model;

public class PhotoLink {

    private String mSelf;
    private String mHtml;
    private String mLocation;

    public PhotoLink(String self, String html, String location) {
        mSelf = self;
        mHtml = html;
        mLocation = location;
    }


    public String getSelf() {
        return mSelf;
    }

    public void setSelf(String self) {
        mSelf = self;
    }

    public String getHtml() {
        return mHtml;
    }

    public void setHtml(String html) {
        mHtml = html;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }
}
