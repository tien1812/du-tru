package vn.tien.photo_world.screen.collection;

import java.util.List;

import vn.tien.photo_world.data.model.Collection;
import vn.tien.photo_world.data.repository.CollectionRepository;
import vn.tien.photo_world.data.source.collections.CollectionDataSource;

public class CollectionPresenter implements CollectionContract.Presenter,
        CollectionDataSource.OnFetchDataListener<Collection> {
    private CollectionContract.View mView;
    private CollectionRepository mCollectionRepository;

    public CollectionPresenter(CollectionContract.View view) {
        mView = view;
        mCollectionRepository = CollectionRepository.getInstance();
    }

    @Override
    public void onFetchDataSuccess(List<Collection> data) {
        mView.onGetDataSuccess(data);
    }

    @Override
    public void onFetchDataFailure(Exception e) {
        mView.onGetDataFailure(e.getMessage());
    }

    @Override
    public void getCollection() {
        mCollectionRepository.getCollections(this);
    }
}
