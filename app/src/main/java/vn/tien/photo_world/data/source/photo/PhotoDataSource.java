package vn.tien.photo_world.data.source.photo;

import java.util.List;

import vn.tien.photo_world.data.model.Photo;

public interface PhotoDataSource {
    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<Photo> data);

        void onFetchDataFailure(Exception e);
    }

    interface RemoteDataSource {
        void getPhoto(OnFetchDataListener listener);
    }
}
