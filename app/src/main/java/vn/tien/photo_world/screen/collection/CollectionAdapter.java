package vn.tien.photo_world.screen.collection;

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
import vn.tien.photo_world.data.model.Collection;
import vn.tien.photo_world.screen.wallpapers.WallpapersActivity;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    private List<Collection> mCollections;
    private Context mContext;

    public CollectionAdapter(Context context, List<Collection> collections) {
        mCollections = collections;
        mContext = context;
    }

    public void setSearchResult(List<Collection> collections) {
        mCollections = collections;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Collection collection = mCollections.get(position);
        holder.mTextAuthor.setText(collection.getUser().getName());
        holder.mTextTitle.setText(collection.getTitle());
        holder.mTextTotal.setText(collection.getTotalPhotos() + " Photos");

        Glide.with(holder.mImageCover).load(collection.getUrl().getFull())
                .centerCrop()
                .into(holder.mImageCover);
        holder.mImageCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String author = collection.getUser().getName();
                String url = collection.getCollectionHtml().getHtml();
                String title = collection.getTitle();
                sendData(url,author,title);
            }
        });
    }

    private void sendData(String url,String author,String title) {
        Intent intent = WallpapersActivity.getIntent(mContext);
        intent.putExtra(Constant.KEY_HTML,url);
        intent.putExtra(Constant.KEY_AUTHOR,author);
        intent.putExtra(Constant.KEY_TITLE,title);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mCollections == null ? 0 : mCollections.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageCover;
        private TextView mTextTotal;
        private TextView mTextTitle;
        private TextView mTextAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageCover = itemView.findViewById(R.id.image_cover);
            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextTotal = itemView.findViewById(R.id.text_total);
            mTextAuthor = itemView.findViewById(R.id.text_author);
        }
    }
}

