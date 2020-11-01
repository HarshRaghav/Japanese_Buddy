package android.example.japanese;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    AudioManager jAudioManager;

    AudioManager.OnAudioFocusChangeListener jOnAudio = new AudioManager.OnAudioFocusChangeListener(){
        public void onAudioFocusChange(int focusChange){
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();;
                mediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener jCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        jAudioManager =(AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        setContentView(R.layout.word_list);
        words.add(new Word("father","Otōsan",R.drawable.family_father,R.raw.fatherj));
        words.add(new Word("mother","Haha",R.drawable.family_mother,R.raw.motherj));
        words.add(new Word("son","Musuko",R.drawable.family_son,R.raw.sonj));
        words.add(new Word("daughter","Musume",R.drawable.family_daughter,R.raw.daughterj));
        words.add(new Word("older brother","Nīsan",R.drawable.family_older_brother,R.raw.obrotherj));
        words.add(new Word("younger brother","Otōto",R.drawable.family_younger_brother,R.raw.ybrotherj));
        words.add(new Word("older sister","Onēsan",R.drawable.family_older_sister,R.raw.osisterj));
        words.add(new Word("younger sister","Imōto",R.drawable.family_younger_sister,R.raw.ysisterj));
        words.add(new Word("grandmother","Sobo",R.drawable.family_grandmother,R.raw.grandmotherj));
        words.add(new Word("grandfather","Sofu",R.drawable.family_grandfather,R.raw.grandfatherj));


        WordAdapter adapter = new WordAdapter(this, words,R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                releaseMediaPlayer();

                int result=jAudioManager.requestAudioFocus(jOnAudio, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getjAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(jCompletionListener);
                }}
        });
    }

    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            jAudioManager.abandonAudioFocus(jOnAudio);
        }
    }
}
