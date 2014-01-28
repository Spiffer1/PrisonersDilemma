import java.util.ArrayList;
import java.lang.reflect.Method;

public class PDTournament
{
    final int REWARD = 1;
    final int PUNISHMENT = 0;
    final int TEMPTATION = 2;
    final int SUCKER = -1;
    final int NUMROUNDS = 200;  // each round consists of two plays
    private int numPlayers;
    private PlayerRecord[] player;  // parallel array to the playerList of classes

    public PDTournament(int numPlayers_)
    {
        numPlayers = numPlayers_;
        player = new PlayerRecord[numPlayers];
        for (int p = 0; p < player.length; p++)
        {
            player[p] = new PlayerRecord();
        }
    }

    public void run(ArrayList<Class<?>> playerList)
    {
        for (int p0 = 0; p0 < player.length - 1; p0++)
        {
            System.out.println(playerList.get(p0).toString().substring(6) + " versus remaining players...");
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
            }
            for (int playerNum = 0; playerNum < player.length; playerNum++)
            {
                System.out.println(playerList.get(playerNum).toString().substring(6) + ": " + player[playerNum].getScore());
            }
            System.out.println();
        }

        // Print three winners:
        int firstPlaceScore = player[0].getScore();
        int firstPlaceIndex = 0;
        for (int i = 1; i < numPlayers; i++)
        {
            if (player[i].getScore() > firstPlaceScore)
            {
                firstPlaceScore = player[i].getScore();
                firstPlaceIndex = i;
            }
        }
        int secondPlaceScore;
        int secondPlaceIndex;
        if (firstPlaceIndex != 0)
        {
            secondPlaceScore = player[0].getScore();
            secondPlaceIndex = 0;
        }
        else
        {
            secondPlaceScore = player[1].getScore();
            secondPlaceIndex = 1;
        }
        for (int i = 1; i < numPlayers; i++)
        {
            if (i != firstPlaceIndex && player[i].getScore() > secondPlaceScore)
            {
                secondPlaceScore = player[i].getScore();
                secondPlaceIndex = i;
            }
        }
        int thirdPlaceScore;
        int thirdPlaceIndex;
        if (firstPlaceIndex != 0 && secondPlaceIndex != 0)
        {
            thirdPlaceScore = player[0].getScore();
            thirdPlaceIndex = 0;
        }
        else if (firstPlaceIndex != 1 && secondPlaceIndex != 1)
        {
            thirdPlaceScore = player[1].getScore();
            thirdPlaceIndex = 1;
        }
        else
        {
            thirdPlaceScore = player[2].getScore();
            thirdPlaceIndex = 2;
        }
        for (int i = 1; i < numPlayers; i++)
        {
            if (i != firstPlaceIndex && i != secondPlaceIndex && player[i].getScore() > thirdPlaceScore)
            {
                thirdPlaceScore = player[i].getScore();
                thirdPlaceIndex = i;
            }
        }
        try
        {
            String firstPlaceName = invokeMethod( playerList.get(firstPlaceIndex), "getAuthor", playerList.get(firstPlaceIndex).newInstance() );
            System.out.println("First Place: " + firstPlaceName + " with " + playerList.get(firstPlaceIndex).toString().substring(6));
            String secondPlaceName = invokeMethod( playerList.get(secondPlaceIndex), "getAuthor", playerList.get(secondPlaceIndex).newInstance() );
            System.out.println("Second Place: " + secondPlaceName + " with " + playerList.get(secondPlaceIndex).toString().substring(6));
            String thirdPlaceName = invokeMethod( playerList.get(thirdPlaceIndex), "getAuthor", playerList.get(thirdPlaceIndex).newInstance() );
            System.out.println("Third Place: " + thirdPlaceName + " with " + playerList.get(thirdPlaceIndex).toString().substring(6));
        }
        catch(Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /*
     * Invokes the method 'theMethod' from the class 'theClass' in Object instance
     */
    private static String invokeMethod(Class<?> theClass, String theMethod, Object player) {
        String result = "Method failed.";
        try {
            Method accessorMethod = theClass.getMethod(theMethod);
            result = (String) accessorMethod.invoke(player);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
