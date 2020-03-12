package vn.tien.photo_world.data.source.collections;

import vn.tien.photo_world.utils.StringUtil;

public class CollectionRemoteDataSource implements CollectionDataSource.RemoteDataSource {
    private static CollectionRemoteDataSource sInstance ;

    private CollectionRemoteDataSource() {
    }
    public static CollectionRemoteDataSource getInstance(){
        if (sInstance == null){
            sInstance = new CollectionRemoteDataSource() ;
        }
        return sInstance ;
    }

    @Override
    public void getCollection(CollectionDataSource.OnFetchDataListener listener) {
        FecthCollectionFromApi fecthCollectionFromApi = new FecthCollectionFromApi(listener);
        fecthCollectionFromApi.execute(StringUtil.formatCollectionApi());
    }
}
