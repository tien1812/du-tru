package vn.tien.photo_world.constant;

import androidx.annotation.StringDef;

@StringDef({
        LinksEntity.SELF,
        LinksEntity.HTML,
        LinksEntity.LOCATION
})
public @interface LinksEntity {
    String SELF = "self" ;
    String HTML = "html" ;
    String LOCATION = "download";
}
