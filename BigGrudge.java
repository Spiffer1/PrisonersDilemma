public class BigGrudge implements Player
{
    private int playerNum;
    private boolean grudge;
    public BigGrudge(int num)
    {
        playerNum = num;
        grudge = false;
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
            String opponentLastMove =  history[moveNum - 1].substring(opponentNum, opponentNum + 1);
            if (opponentLastMove.equals("d"))
            {
                grudge = true;
            }
        }
        if (grudge)
        {
            return "d";
        }
        else
        {
            return "c";
        }
    }
}

