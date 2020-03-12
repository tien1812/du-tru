package vn.tien.photo_world.screen.home;

import java.util.List;

import vn.tien.photo_world.data.model.Photo;

public class HomeContract {
    interface View {
        void onGetPhotoSuccess(List<Photo> photo);

        void onGetDataFailure(String message);
    }

    interface Presenter {
        void getPhoto();
    }
}
