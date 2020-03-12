package vn.tien.photo_world.screen.wallpapers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.tien.photo_world.R;
import vn.tien.photo_world.constant.Constant;
import vn.tien.photo_world.data.model.Wallpapers;
import vn.tien.photo_world.screen.edit.EditActivity;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHoder> {
    private List<Wallpapers> mWallpapers;
    private Context mContext;

    public WallpaperAdapter(Context context, List<Wallpapers> wallpapers) {
        mWallpapers = wallpapers;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.
                from(parent.getContext()).inflate(R.layout.item_wallpaper, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        final Wallpapers wallpapers = mWallpapers.get(position);
        Glide.with(holder.mWallpaper.getContext())
                .load(wallpapers.getmLinkImage()).centerCrop()
                .into(holder.mWallpaper);
        holder.mWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = wallpapers.getmLinkImage();
                String author = wallpapers.getAuthor();
                sendData(link, author);
            }
        });
    }

    private void sendData(String link, String author) {
        Intent intent = EditActivity.getIntent(mContext);
        intent.putExtra(Constant.KEY_IMAGE, link);
        intent.putExtra(Constant.KEY_AUTHOR, author);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mWallpapers == null ? 0 : mWallpapers.size();
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {
        private ImageView mWallpaper;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            mWallpaper = itemView.findViewById(R.id.img_wallpaper);
        }
    }
}
