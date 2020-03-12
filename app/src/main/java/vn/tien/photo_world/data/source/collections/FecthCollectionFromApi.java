package vn.tien.photo_world.data.source.collections;

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
import vn.tien.photo_world.constant.CollectionEntity;
import vn.tien.photo_world.constant.Constant;
import vn.tien.photo_world.constant.LinksEntity;
import vn.tien.photo_world.constant.PhotoEntity;
import vn.tien.photo_world.constant.UrlsEntity;
import vn.tien.photo_world.data.model.Collection;
import vn.tien.photo_world.data.model.CollectionHtml;
import vn.tien.photo_world.data.model.PhotoUrl;
import vn.tien.photo_world.data.model.User;

public class FecthCollectionFromApi extends AsyncTask<String, Void, List<Collection>> {
    private CollectionDataSource.OnFetchDataListener mListener;
    private Exception mException;

    public FecthCollectionFromApi(CollectionDataSource.OnFetchDataListener listener) {
        mListener = listener;
    }

    @Override
    protected List<Collection> doInBackground(String... strings) {
        String url = strings[0];
        try {
            String data = getStringDataFromUrl(url);
            return getCollectionFromStringData(data);
        } catch (IOException | JSONException e) {
            mException = e;
        }
        return null;
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

    private List<Collection> getCollectionFromStringData(String data) throws JSONException {
        List<Collection> collections = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String title = jsonObject.getString(CollectionEntity.TITLE);
            int totalPhoto = jsonObject.getInt(CollectionEntity.TOTAL_PHOTOS);

            JSONObject jsonUser = jsonObject.getJSONObject(CollectionEntity.AUTHOR);
            String nameAuthor = jsonUser.getString(AuthorEntity.NAME);
            User author = new User(nameAuthor);

            JSONObject jsonCoverPhoto = jsonObject.getJSONObject(CollectionEntity.COVER_PHOTO);
            JSONObject jsonUrl = jsonCoverPhoto.getJSONObject(PhotoEntity.PHOTO_URL);
            String raw = jsonUrl.getString(UrlsEntity.RAW);
            String full = jsonUrl.getString(UrlsEntity.FULL);
            String regular = jsonUrl.getString(UrlsEntity.REGULAR);
            String small = jsonUrl.getString(UrlsEntity.SMALL);
            String thumb = jsonUrl.getString(UrlsEntity.THUMB);
            PhotoUrl photoUrl = new PhotoUrl(raw, full, regular, small, thumb);

            JSONObject jsonLink =jsonObject.getJSONObject(CollectionEntity.LINK);
            String html =jsonLink.getString(LinksEntity.HTML);
            CollectionHtml collectionHtml = new CollectionHtml(html);

            Collection collection =
                    new Collection(title, author, totalPhoto, photoUrl,collectionHtml);
            collections.add(collection);
        }
        return collections;
    }

    @Override
    protected void onPostExecute(List<Collection> collections) {
        if (mListener == null) return;
        if (mException == null) {
            mListener.onFetchDataSuccess(collections);
        } else {
            mListener.onFetchDataFailure(mException);
        }
    }
}
