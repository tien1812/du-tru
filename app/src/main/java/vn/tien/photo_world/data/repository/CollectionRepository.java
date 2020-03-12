package vn.tien.photo_world.data.repository;

import vn.tien.photo_world.data.source.collections.CollectionDataSource;
import vn.tien.photo_world.data.source.collections.CollectionRemoteDataSource;

public class CollectionRepository {
    private static CollectionRepository sInstance ;
    private CollectionRemoteDataSource mCollectionRemoteDataSource;

    private CollectionRepository(CollectionRemoteDataSource collectionRemoteDataSource) {
        mCollectionRemoteDataSource = collectionRemoteDataSource;
    }
     public static CollectionRepository getInstance(){
        if (sInstance ==null){
            sInstance = new CollectionRepository(CollectionRemoteDataSource.getInstance());
        }
        return sInstance ;
     }

     public void getCollections(CollectionDataSource.OnFetchDataListener listener){
        mCollectionRemoteDataSource.getCollection(listener);
     }
}
