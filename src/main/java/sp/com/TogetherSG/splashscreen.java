package sp.com.TogetherSG;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class splashscreen extends AppCompatActivity {
    MediaPlayer mySong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        /*mySong=MediaPlayer.create(this,R.raw.music);
        mySong.start();
        Thread timer=new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch(InterruptedException e){
                    e.printStackTrace();

                }finally{
                    nextActivity();
                }}} ;

        timer.start(); */
        nextActivity();
    }

    public void nextActivity(){
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }

    protected void onPause(){
        super.onPause();
        //mySong.release();
        finish();
    }
}