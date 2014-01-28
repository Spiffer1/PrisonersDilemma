public class BigGrudge implements PDPlayer
{
    private boolean grudge;
    public BigGrudge()
    {
        grudge = false;
    }

    public String chooseCorD(String opponentsLastMove)
    {
        if (opponentsLastMove.equals("d"))
        {
            grudge = true;
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
    public String getAuthor()
    {
        return "Mr. Fottrell";
    }
    public String toString()
    {
        return "Cooperates until the other player defects. After that, BigGrudge always defects.";
    }
}

