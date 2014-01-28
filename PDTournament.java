import java.util.ArrayList;
import java.lang.reflect.Method;

public class PDTournament
{
    final int NUMPLAYERS = 4;
    final int REWARD = 1;
    final int PUNISHMENT = 0;
    final int TEMPTATION = 2;
    final int SUCKER = -1;
    final int NUMROUNDS = 3;  // each round consists of two plays
    private PlayerRecord[] player;

    public PDTournament()
    {
        player = new PlayerRecord[NUMPLAYERS];
        for (int p = 0; p < player.length; p++)
        {
            player[p] = new PlayerRecord();
        }
    }

    public void run(ArrayList<Class<?>> playerList)
    {
        for (int p0 = 0; p0 < player.length - 1; p0++)
        {
            System.out.println("p0 = " + p0);
            for (int p1 = p0 + 1; p1 < player.length; p1++)
            {
                // Below is the main algorithm for game play
                try
                {
                    Object firstPlayer = playerList.get(p0).newInstance();
                    Object secondPlayer = playerList.get(p1).newInstance();
                    String[] history = new String[NUMROUNDS * 2];    
                    String p0LastMove = "";
                    String p1LastMove = "";
                    for (int round = 0; round < NUMROUNDS; round++)
                    {
                        String p0Move = invokeMethod(playerList.get(p0), "chooseCorD", firstPlayer, p1LastMove);
                        // firstPlayer.chooseCorD(p1LastMove);
                        String p1Move = invokeMethod(playerList.get(p1), "chooseCorD", secondPlayer, p0LastMove);
                        //secondPlayer.chooseCorD(p0LastMove);
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
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                for (int playerNum = 0; playerNum < player.length; playerNum++)
                {
                    System.out.println("Player " + playerNum + ": " + player[playerNum].getScore());
                }
            }
        }
    }
    /*
     * Invokes the method 'theMethod' from the class 'theClass' in Object instance
     */
    private static String invokeMethod(Class<?> theClass, String theMethod, Object player, String opponentsLastMove) {
        String result = "Method failed.";
        try {
            Method chooseCorDMethod = theClass.getMethod(theMethod, String.class);
            result = (String) chooseCorDMethod.invoke(player, opponentsLastMove);
            //System.out.println(String.format("The result of calling 'getName()' on '%s' is '%s'.", theClass.getName(), result));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
