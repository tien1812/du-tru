package vn.tien.photo_world.screen.edit;

import android.Manifest;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;

import vn.tien.photo_world.R;
import vn.tien.photo_world.constant.Constant;
import vn.tien.photo_world.data.model.Wallpapers;
import vn.tien.photo_world.screen.Favorite.FavoriteFragment;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageEdit;
    private TextView mTextName;
    private CardView mCardView;
    private ScaleGestureDetector mScaleDetector;
    private FloatingActionButton mBtSelectScreen;
    private ImageView mBtTune, mBtCrop, mBtShared, mBtDownload, mBtFavorite;
    private String url, author;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        getData();
        setListener();
        new GetImageFromUrl(mImageEdit).execute(url);
    }

    private void setListener() {
        mBtSelectScreen.setOnClickListener(this);
        mBtTune.setOnClickListener(this);
        mBtShared.setOnClickListener(this);
        mBtCrop.setOnClickListener(this);
        mBtDownload.setOnClickListener(this);
        mBtFavorite.setOnClickListener(this);
    }

    private void getData() {
        Intent intent = getIntent();
        url = intent.getStringExtra(Constant.KEY_IMAGE);
        author = intent.getStringExtra(Constant.KEY_AUTHOR);
        Glide.with(mImageEdit.getContext()).load(url).
                centerCrop().into(mImageEdit);
        mTextName.setText(author);
    }

    private void initView() {
        mImageEdit = findViewById(R.id.image_edit);
        mTextName = findViewById(R.id.text_name_author);
        mCardView = findViewById(R.id.card_view);
        mBtSelectScreen = findViewById(R.id.btn_setscreen);
        mBtTune = findViewById(R.id.image_tune);
        mBtShared = findViewById(R.id.image_shared);
        mBtCrop = findViewById(R.id.image_crop);
        mBtDownload = findViewById(R.id.image_download);
        mBtFavorite = findViewById(R.id.image_favorite);

        mScaleDetector = new ScaleGestureDetector(this, new ScaleListener());
        mImageEdit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mScaleDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, EditActivity.class);
        return intent;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_setscreen:
                setWallpapers();
                break;
            case R.id.image_tune:
                break;
            case R.id.image_crop:
                break;
            case R.id.image_shared:
                sharedImage();
                break;
            case R.id.image_download:
                downloadFile();
                break;
            case R.id.image_favorite:
                favorite(url, author);
                break;
            default:
                break;
        }
    }

    private void setWallpapers() {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(EditActivity.this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int w = displayMetrics.widthPixels;
        int h = displayMetrics.heightPixels;

        try {
            if (mBitmap != null) {
                wallpaperManager.setBitmap(mBitmap);
                Toast.makeText(this, "Set Wallpaper Success", Toast.LENGTH_SHORT).show();
                wallpaperManager.suggestDesiredDimensions(w, h);
            } else {
                Toast.makeText(this, "Set Wallpaper Failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sharedImage() {
        Intent mSharingIntent = new Intent(Intent.ACTION_SEND);
        mSharingIntent.setType("text/plain");
        mSharingIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        mSharingIntent.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(mSharingIntent, "Share Image via"));
    }

    private void downloadFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, Constant.PERMISSION_STORAGE_CODE);
            } else {
                startDowloading();
            }
        } else {
            startDowloading();
        }
    }

    private void startDowloading() {
        Toast.makeText(this, "Downloading", Toast.LENGTH_SHORT).show();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Photo World"); //set title download notification
        request.setDescription("Downloading Image ...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, author + ".png");

        //get download service and enque file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constant.PERMISSION_STORAGE_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDowloading();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void favorite(String url, String author) {
        Toast.makeText(this, "Favorited", Toast.LENGTH_SHORT).show();
        mBtFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        Wallpapers wallpapers = new Wallpapers(url, author);
        if (FavoriteFragment.mWallpapers.contains(wallpapers)) {
            FavoriteFragment.mWallpapers.remove(wallpapers);
        } else {
            FavoriteFragment.mWallpapers.add(wallpapers);
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float mScale = 1f;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScale *= detector.getScaleFactor();
            mScale = Math.max(1.0f, Math.min(mScale, 3.0f));
            mImageEdit.setScaleX(mScale);
            mImageEdit.setScaleY(mScale);
            return true;
        }
    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
        ImageView mImageView;

        public GetImageFromUrl(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            mBitmap = null;
            try {
                InputStream str = new java.net.URL(urldisplay).openStream();
                mBitmap = BitmapFactory.decodeStream(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mImageView.setImageBitmap(bitmap);
        }
    }

}
