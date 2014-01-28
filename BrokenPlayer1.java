public class BrokenPlayer1 implements PDPlayer
{
    public BrokenPlayer1()
    {
    }
    public String chooseCorD(String opponentsLastMove)
    {
        if (opponentsLastMove.equals("d"))
        {
            return "d";
        }
        else
        {
            return "e";
        }
    }
    public String getAuthor()
    {
        return "Mr. Fottrell";
    }
    public String toString()
    {
        return "Cooperates on the first round. After that it does whatever the opponent did\n" +
               "on the previous turn.";
    }
}

