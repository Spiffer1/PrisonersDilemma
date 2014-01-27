public class TitForTat implements Player
{
    private int playerNum;
    public TitForTat(int num)
    {
        playerNum = num;
    }
    public String pickMove(String[] history, int moveNum)
    {
        if (moveNum > 0)
        {
            int opponentNum;
            if (playerNum == 0)
            {
                opponentNum = 1;
            }
            else
            {
                opponentNum = 0;
            }
            return history[moveNum - 1].substring(opponentNum, opponentNum + 1);
        }
        else
        {
            return "c";
        }
    }
}
