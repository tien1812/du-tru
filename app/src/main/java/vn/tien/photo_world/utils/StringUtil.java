package vn.tien.photo_world.utils;

import vn.tien.photo_world.BuildConfig;
import vn.tien.photo_world.constant.Constant;

public class StringUtil {
    public static String formatPhotoApi() {
        return String.format("%s%s", Constant.PHOTO_URL, BuildConfig.API_KEY);
    }

    public static String formatCollectionApi() {
        return String.format("%s%s", Constant.COLLECTION_URL, BuildConfig.API_KEY);
    }
}
