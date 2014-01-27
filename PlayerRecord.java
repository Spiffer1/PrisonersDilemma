public class PlayerRecord
{
    private int score;

    public PlayerRecord()
    {
        score = 0;
    }
    public int getScore()
    {
        return score;
    }
    public void addToScore(int sc)
    {
        score += sc;
    }
}