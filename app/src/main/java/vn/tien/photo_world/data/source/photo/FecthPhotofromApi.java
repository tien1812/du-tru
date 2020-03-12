package vn.tien.photo_world.data.source.photo;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vn.tien.photo_world.constant.AuthorEntity;
import vn.tien.photo_world.constant.Constant;
import vn.tien.photo_world.constant.LinksEntity;
import vn.tien.photo_world.constant.PhotoEntity;
import vn.tien.photo_world.constant.UrlsEntity;
import vn.tien.photo_world.data.model.Photo;
import vn.tien.photo_world.data.model.PhotoLink;
import vn.tien.photo_world.data.model.PhotoUrl;
import vn.tien.photo_world.data.model.User;

public class FecthPhotofromApi extends AsyncTask<String, Void, List<Photo>> {
    private PhotoDataSource.OnFetchDataListener<Photo> mListener;
    private Exception mException;

    public FecthPhotofromApi(PhotoDataSource.OnFetchDataListener listener) {
        mListener = listener;
    }

    @Override
    protected List<Photo> doInBackground(String... strings) {
        String url = strings[0];
        try {
            String data = getStringDataFromUrl(url);
            return getPhotosFromStringData(data);
        } catch (IOException | JSONException e) {
            mException = e;
        }

        return null;

    }

    private List<Photo> getPhotosFromStringData(String data) throws JSONException {
        List<Photo> photos = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String id = jsonObject.getString(PhotoEntity.ID);
            String creat = jsonObject.getString(PhotoEntity.CREAT_DAY);

            JSONObject jsonUser = jsonObject.getJSONObject(PhotoEntity.AUTHOR);
            String name_author = jsonUser.getString(AuthorEntity.NAME);
            User author = new User(name_author);

            JSONObject jsonUrls = jsonObject.getJSONObject(PhotoEntity.PHOTO_URL);
            String raw = jsonUrls.getString(UrlsEntity.RAW);
            String regular = jsonUrls.getString(UrlsEntity.REGULAR);
            String full = jsonUrls.getString(UrlsEntity.FULL);
            String small = jsonUrls.getString(UrlsEntity.SMALL);
            String thumb = jsonUrls.getString(UrlsEntity.THUMB);
            PhotoUrl photoUrl = new PhotoUrl(raw, full, regular, small, thumb);

            JSONObject jsonLinks = jsonObject.getJSONObject(PhotoEntity.PHOTO_LINK);
            String self = jsonLinks.getString(LinksEntity.SELF);
            String html = jsonLinks.getString(LinksEntity.HTML);
            String location = jsonLinks.getString(LinksEntity.LOCATION);
            PhotoLink photoLink = new PhotoLink(self, html, location);

            Photo photo = new Photo(id, author, creat, photoUrl, photoLink);

            photos.add(photo);

        }
        return photos;
    }


    private String getStringDataFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(Constant.REQUEST_METHOD_GET);
        connection.setReadTimeout(Constant.READ_TIME_OUT);
        connection.setConnectTimeout(Constant.CONNECT_TIME_OUT);
        connection.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(Constant.BREAK_LINE);
        }
        br.close();
        connection.disconnect();
        return sb.toString();
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        if (mListener == null) return;
        if (mException == null) {
            mListener.onFetchDataSuccess(photos);
        } else {
            mListener.onFetchDataFailure(mException);
        }
    }
}
