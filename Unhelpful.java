public class Unhelpful implements Player
{
    private int playerNum;
    public Unhelpful(int num)
    {
        playerNum = num;
    }
    public String pickMove(String[] history, int moveNum)
    {
        return "d";
    }
}

