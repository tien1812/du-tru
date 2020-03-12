package vn.tien.photo_world.screen.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.tien.photo_world.R;
import vn.tien.photo_world.constant.Constant;
import vn.tien.photo_world.data.model.Photo;
import vn.tien.photo_world.screen.edit.EditActivity;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Photo> mPhotos;
    private Context mConText;

    public HomeAdapter(Context context, List<Photo> photos) {
        mPhotos = photos;
        mConText = context;
    }

    public void setSearchResult(List<Photo> photos) {
        mPhotos = photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Photo photo = mPhotos.get(position);
        Glide.with(holder.mPhoto.getContext()).load(photo.getUrls().getFull())
                .centerCrop()
                .into(holder.mPhoto);
        holder.mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = photo.getUrls().getFull();
                String author = photo.getAuthor().getName();
                sendData(url, author);
            }
        });

    }

    private void sendData(String url, String author) {
        Intent intent = EditActivity.getIntent(mConText);
        intent.putExtra(Constant.KEY_IMAGE, url);
        intent.putExtra(Constant.KEY_AUTHOR, author);
        mConText.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mPhotos == null ? 0 : mPhotos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPhoto = itemView.findViewById(R.id.image_photo);
        }
    }
}
