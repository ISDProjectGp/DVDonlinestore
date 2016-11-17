package proj.isd2016.cccu.dvdonlinestore;

import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import proj.isd2016.cccu.dvdonlinestore.database.Config;

/**
 * Created by cheng on 17/11/2016.
 */
public class CustomYoutubeBaseActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    // YouTube connector
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    protected YouTubePlayerView youTubeView;
    protected String VIDEO_CODE = null ;
    protected Handler handler = new Handler();

    // Youtube connector //

    protected void updateVideosFound(String vcode)
    {
        VIDEO_CODE = vcode;
        youTubeView.initialize(Config.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo(VIDEO_CODE);

            // Hiding player controls
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

}
