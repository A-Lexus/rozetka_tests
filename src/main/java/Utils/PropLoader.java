package Utils;

import java.io.*;
import java.util.Properties;


public class PropLoader {

    static private Properties prop = new Properties();
    private static final String fileName = "rozetka.properties";
    private static String propertiesFilePath;

    static {
        new PropLoader();
    }


    public PropLoader() {

        InputStream input = null;
        OutputStream output = null;

        try {
            ClassLoader cl = PropLoader.class.getClassLoader();
            propertiesFilePath = new File(cl.getResource(fileName).getFile()).getAbsolutePath();
            input = new FileInputStream(propertiesFilePath);
            if (input == null) {
                System.out.println("Sorry, file is unable! " + fileName);
            }
            prop.load(input);
            input.close();


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getProp(String key) {
        return (prop == null) ? String.valueOf("Property is not setted!") : prop.getProperty(key);
    }

}

