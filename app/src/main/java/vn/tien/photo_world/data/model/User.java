package vn.tien.photo_world.data.model;

public class User {
    private String mName ;

    public User(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
