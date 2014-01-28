public class PDTournament
{
    final int NUMPLAYERS = 4;
    final int REWARD = 1;
    final int PUNISHMENT = 0;
    final int TEMPTATION = 2;
    final int SUCKER = -1;
    final int NUMROUNDS = 200;  // each round consists of two plays
    private PlayerRecord[] player;

    public PDTournament()
    {
        player = new PlayerRecord[NUMPLAYERS];
        for (int p = 0; p < player.length; p++)
        {
            player[p] = new PlayerRecord();
        }
    }

    public void run()
    {
        for (int p0 = 0; p0 < player.length; p0++)
        {
            System.out.println("p0 = " + p0);
            for (int p1 = p0 + 1; p1 < player.length; p1++)
            {
                // Below is the main algorithm for game play
                PDPlayer firstPlayer = new TitForTat();  // default player type is TitForTat
                PDPlayer secondPlayer = new TitForTat();
                //Enter actual type for each of the players below:
                if (p0 == 0)
                    firstPlayer = new Generous();
                if (p0 == 1)
                    firstPlayer = new Unhelpful();
                if (p0 == 2)
                    firstPlayer = new TitForTat();
                if (p0 == 3)
                    firstPlayer = new BigGrudge();
                if (p1 == 0)
                    secondPlayer = new Generous();
                if (p1 == 1)
                    secondPlayer = new Unhelpful();
                if (p1 == 2)
                    secondPlayer = new TitForTat();
                if (p1 == 3)
                    secondPlayer = new BigGrudge();

                String[] history = new String[NUMROUNDS * 2];    
                String p0LastMove = "";
                String p1LastMove = "";
                for (int round = 0; round < NUMROUNDS; round++)
                {
                    String p0Move = firstPlayer.chooseCorD(p1LastMove);
                    String p1Move = secondPlayer.chooseCorD(p0LastMove);
                    history[round] = p0Move + p1Move;
                    p0LastMove = p0Move;
                    p1LastMove = p1Move;
                    if (history[round].equals("cc"))
                    {
                        player[p0].addToScore(REWARD);
                        player[p1].addToScore(REWARD);
                    }
                    if (history[round].equals("cd"))
                    {
                        player[p0].addToScore(SUCKER);
                        player[p1].addToScore(TEMPTATION);
                    }
                    if (history[round].equals("dc"))
                    {
                        player[p0].addToScore(TEMPTATION);
                        player[p1].addToScore(SUCKER);
                    }
                    if (history[round].equals("dd"))
                    {
                        player[p0].addToScore(PUNISHMENT);
                        player[p1].addToScore(PUNISHMENT);
                    }
                }
                for (int playerNum = 0; playerNum < player.length; playerNum++)
                {
                    System.out.println("Player " + playerNum + ": " + player[playerNum].getScore());
                }
            }
        }
    }
}
