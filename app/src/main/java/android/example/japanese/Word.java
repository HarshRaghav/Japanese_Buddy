package android.example.japanese;

public class Word {

    private String jDefaultTranslation;
    private String japaneseTranslation;
    private int japImageResourceId= NO_IMAGEPROVIDED;
    private static final int NO_IMAGEPROVIDED = -1;
    private int jAudioResourceId;

    public Word(String defaultTranslation, String japTranslation,int audio) {
        jDefaultTranslation = defaultTranslation;
        japaneseTranslation = japTranslation;
        jAudioResourceId=audio;
    }

    public Word(String defaultTranslation, String japTranslation,int imageId,int audio) {
        jDefaultTranslation = defaultTranslation;
        japaneseTranslation = japTranslation;
        japImageResourceId = imageId;
        jAudioResourceId=audio;
    }

    public String getDefaultTranslation() {
        return jDefaultTranslation;
    }

    public String getJapaneseTranslation() {
        return japaneseTranslation;
    }

    public int getImageResourceId(){ return japImageResourceId; }
    public boolean hasImage(){
        return japImageResourceId != NO_IMAGEPROVIDED;
    }

    public int getjAudioResourceId(){
        return jAudioResourceId;
    }

}
