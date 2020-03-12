package vn.tien.photo_world.screen.collection;

import java.util.List;

import vn.tien.photo_world.data.model.Collection;

public class CollectionContract {
    interface View {
        void onGetDataSuccess(List<Collection> collections);

        void onGetDataFailure(String message);
    }

    interface Presenter {
        void getCollection();
    }
}
