public class Generous implements Player
{
    private int playerNum;
    public Generous(int num)
    {
        playerNum = num;
    }
    public String pickMove(String[] history, int moveNum)
    {
        return "c";
    }
}
