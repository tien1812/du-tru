package vn.tien.photo_world.data.source.photo;

import vn.tien.photo_world.utils.StringUtil;

public class PhotoRemoteDataSource implements PhotoDataSource.RemoteDataSource {
    private static PhotoRemoteDataSource sInstance;

    private PhotoRemoteDataSource() {
    }

    public static PhotoRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new PhotoRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getPhoto(PhotoDataSource.OnFetchDataListener listener) {
        FecthPhotofromApi fecthPhotofromApi = new FecthPhotofromApi(listener);
        fecthPhotofromApi.execute(StringUtil.formatPhotoApi());
    }
}
