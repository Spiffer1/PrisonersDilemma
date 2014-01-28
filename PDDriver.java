import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

public class PDDriver
{  
    /* 
     * We look in the current directory for a directory named 'plugins'.  If that directory
     * contains any .class files, we dynamically load them into ArrayList playerList. We then
     * test them to see if they implement the PDPlayer interface. If not, we remove them from
     * the playerList.
     */
    public static void main(String[] args)
    {
        // Get the current directory
        File currentDir = new File(System.getProperty("user.dir"));
        System.out.println("The current directory is: " + currentDir.getAbsolutePath());

        // Look for the "plugins" directory and get a list of the class files
        File pluginsDir = new File(currentDir + "/" + "plugins");
        ArrayList<Class<?>> playerList = new ArrayList<Class<?>>();
        if (pluginsDir.exists() && pluginsDir.isDirectory()) {
            // First, get a list of all the Java class files
            File[] classFileList = getClassFiles(pluginsDir);

            // Next, from the list of class files, create a list of Class objects
            playerList = getClasses(pluginsDir, classFileList);
        } else {
            System.err.println(String.format("The directory '%s' is missing.", pluginsDir.getAbsolutePath()));
        }
        // Remove player classes that do not implement PDPlayer Interface. Do this by constructing
        // an array with the indices of the classes that need removing.
        System.out.println("Array has " + playerList.size() + " classes");
        int[] removals = new int[playerList.size()];
        int numToRemove = 0;
        for (int i = 0; i < playerList.size(); i++)
        {
            if (PDPlayer.class.isAssignableFrom(playerList.get(i)) == false)
            {
                System.out.println("Class does not implement PDPlayer interface: Removing " + playerList.get(i) );
                removals[numToRemove] = i;
                numToRemove++;
            }
        }
        for (int i = numToRemove - 1; i >= 0; i--)
        {
            playerList.remove(removals[i]);
        }
        System.out.println("Array now has " + playerList.size() + " classes.\n");
        
        PDTournament tourney = new PDTournament(playerList.size());
        tourney.run(playerList);
    }

    /*
     * Returns an ArrayList of Java Class objects that exist in the directory "pluginsDir"
     */
    private static ArrayList<Class<?>> getClasses(File pluginsDir, File[] classFileList) {
        ArrayList<Class<?>> result = null;

        try {
            URL[] urls = { pluginsDir.toURI().toURL() };
            URLClassLoader classLoader = new URLClassLoader(urls, PDDriver.class.getClassLoader());

            result = new ArrayList<Class<?>>();
            for (File classFile : classFileList) {
                ClassParser classParser = new ClassParser(classFile.getAbsolutePath());
                JavaClass javaClass = classParser.parse();
                String className = javaClass.getClassName();
                System.out.println(String.format("Class name added: '%s'.", className));
                Class<?> theClass = classLoader.loadClass(className);
                result.add(theClass);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    /*
     * Returns the list of files in the 'pluginsDir' that end with ".class"
     */
    private static File[] getClassFiles(File pluginsDir) {
        File[] result = null;

        // create new filename filter for ".class" files
        FilenameFilter fileNameFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    boolean result = false;
                    if (name.lastIndexOf('.') > 0) {
                        // get last index for '.' char
                        int lastIndex = name.lastIndexOf('.');

                        // get extension
                        String str = name.substring(lastIndex);

                        // match path name extension
                        if (str.equals(".class")) {
                            result = true;
                        }
                    }
                    return result;
                }
            };

        //
        // Use the FilenameFilter we created "in-line" above to get a list of all files
        // ending with ".class".
        //
        result = pluginsDir.listFiles(fileNameFilter);

        return result;
    }
}
