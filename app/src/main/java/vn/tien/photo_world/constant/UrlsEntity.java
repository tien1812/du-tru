package vn.tien.photo_world.constant;

import android.webkit.JavascriptInterface;

import androidx.annotation.StringDef;

@StringDef({
        UrlsEntity.RAW,
        UrlsEntity.FULL,
        UrlsEntity.REGULAR,
        UrlsEntity.SMALL,
        UrlsEntity.THUMB
})
public @interface UrlsEntity  {
    String RAW = "raw";
    String FULL = "full";
    String REGULAR = "regular";
    String SMALL = "small";
    String THUMB = "thumb";
}
