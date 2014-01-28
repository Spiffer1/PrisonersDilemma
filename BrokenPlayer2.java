/*
 * does not implement PDPlayer
 */
public class BrokenPlayer2
{
    public BrokenPlayer2()
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
            return "c";
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


