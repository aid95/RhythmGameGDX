package kr.bugfix.game.datastruct;

public class StageInfo
{
    public String jsonPath;
    public String mp3Path;
    public String thumbnail;

    public StageInfo(String jsonPath, String mp3Path, String thumbnail)
    {
        this.jsonPath = jsonPath;
        this.mp3Path = mp3Path;
        this.thumbnail = thumbnail;
    }
}
