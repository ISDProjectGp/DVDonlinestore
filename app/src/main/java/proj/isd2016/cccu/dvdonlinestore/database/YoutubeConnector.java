package proj.isd2016.cccu.dvdonlinestore.database;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;

import proj.isd2016.cccu.dvdonlinestore.R;

/**
 * Created by cheng on 16/11/2016.
 */
public class YoutubeConnector {
    private YouTube youtube;
    private YouTube.Search.List query;
    private static final long NUMBER_OF_VIDEOS_RETURNED = 1;

    public YoutubeConnector(Context context) {
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName("Apps").build();

        try{
            query = youtube.search().list("id,snippet");
            query.setKey(Config.DEVELOPER_KEY);
            query.setType("video");
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        }catch(IOException e){
            Log.d("YC", "Could not initialize: "+e);
        }
    }


    public String search(String keywords){
        query.setQ(keywords);
        try{
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();
            for(SearchResult result:results){
                return result.getId().getVideoId();
            }
            return null;
        }catch(IOException e){
            Log.d("YC", "Could not search: "+e);
            return null;
        }
    }
}
