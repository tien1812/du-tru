package vn.tien.photo_world.data.source.collections;

import java.util.List;

import vn.tien.photo_world.data.model.Collection;
import vn.tien.photo_world.data.source.photo.PhotoDataSource;

public interface CollectionDataSource {
    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<Collection> data);

        void onFetchDataFailure(Exception e);
    }

    interface RemoteDataSource {
        void getCollection(CollectionDataSource.OnFetchDataListener listener);
    }
}
