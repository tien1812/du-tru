package vn.tien.photo_world.screen.home;

import java.util.List;

import vn.tien.photo_world.data.model.Photo;
import vn.tien.photo_world.data.repository.PhotoRepository;
import vn.tien.photo_world.data.source.photo.PhotoDataSource;

public class HomePresenter implements HomeContract.Presenter,
        PhotoDataSource.OnFetchDataListener<Photo> {
    private HomeContract.View mView;
    private PhotoRepository mPhotoRepository;

    public HomePresenter(HomeContract.View view) {
        mView = view;
        mPhotoRepository = PhotoRepository.getInstance();
    }

    @Override
    public void onFetchDataSuccess(List<Photo> data) {
        mView.onGetPhotoSuccess(data);
    }

    @Override
    public void onFetchDataFailure(Exception e) {
        mView.onGetDataFailure(e.getMessage());
    }

    @Override
    public void getPhoto() {
        mPhotoRepository.getPhoto(this);
    }
}
