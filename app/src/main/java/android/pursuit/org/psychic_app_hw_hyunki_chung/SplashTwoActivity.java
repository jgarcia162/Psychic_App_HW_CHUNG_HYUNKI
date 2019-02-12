package android.pursuit.org.psychic_app_hw_hyunki_chung;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class SplashTwoActivity extends AppCompatActivity {
    private ImageView splash;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash = findViewById(R.id.splash_imageView);
        final Intent intent = new Intent(this, MainActivity.class);
        mp = MediaPlayer.create(getBaseContext(), R.raw.cartman);

        final Thread timer = new Thread() {
            public void run() {
                try {
                    mp.start();
                    sleep(7000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };

        Glide.with(splash.getContext())
                .load("https://media.giphy.com/media/f8oBoTpxOOWAM/giphy.gif")
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(new GlideDrawableImageViewTarget(splash) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                    }
                });
        timer.start();
    }

    //theres no guarantee onDestroy is called, it's only called when the system wants to reclaim resources or if you call finish(). Always use onStop. Read here for more info -> [https://developer.android.com/reference/android/app/Activity#onDestroy%28%29]
    @Override
    protected void onStop() {
        super.onStop();
        if (mp != null) {
            mp.release();
        }
    }
}
