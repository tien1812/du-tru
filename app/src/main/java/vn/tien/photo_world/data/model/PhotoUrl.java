package vn.tien.photo_world.data.model;

public class PhotoUrl {

    private String mRaw;
    private String mFull;
    private String mRegular;
    private String mSmall;
    private String mThumb;

    public PhotoUrl(String raw, String full, String regular, String small, String thumb) {
        mRaw = raw;
        mFull = full;
        mRegular = regular;
        mSmall = small;
        mThumb = thumb;
    }

    public String getRaw() {
        return mRaw;
    }

    public void setRaw(String raw) {
        mRaw = raw;
    }

    public String getFull() {
        return mFull;
    }

    public void setFull(String full) {
        mFull = full;
    }

    public String getRegular() {
        return mRegular;
    }

    public void setRegular(String regular) {
        mRegular = regular;
    }

    public String getSmall() {
        return mSmall;
    }

    public void setSmall(String small) {
        mSmall = small;
    }

    public String getThumb() {
        return mThumb;
    }

    public void setThumb(String thumb) {
        mThumb = thumb;
    }
}
