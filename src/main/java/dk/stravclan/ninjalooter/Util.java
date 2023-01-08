package dk.stravclan.ninjalooter;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.*;
import java.util.*;


public class Util {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String fileName = Constants.CONFIG_FILEPATH;
    private static final Properties defaultValues = new Properties();

    public static List<String> dictEnumeratorToBlockNameList(Enumeration<String> dict) {
        List<String> list = new ArrayList<>();
        while (dict.hasMoreElements()) {
            String[] elem = dict.nextElement().split("\\.");
            list.add(elem[elem.length - 1]);
        }
        return list;
    }
    public static List<String> dictEnumeratorToBlockIDList(Enumeration<String> dict) {
        List<String> list = new ArrayList<>();
        while (dict.hasMoreElements()) {
            list.add(dict.nextElement());
        }
        return list;
    }

    public static String describtionIdToName(String id) {
        String[] elem = id.split("\\.");
        return elem[elem.length - 1];
    }


    public static void saveBlacklist() {
        try {
            File config = new File(fileName);
            boolean existed = config.exists();
            File parentDir = config.getParentFile();
            if (!parentDir.exists())
                parentDir.mkdirs();

            FileWriter configWriter = new FileWriter(config);

            writeList(configWriter, Constants.CONFIG_BLACKLIST,  dictEnumeratorToBlockIDList(HelperFunctions.lootBlacklist.keys()));
            configWriter.close();

            if (!existed)
                LOGGER.info("Created the config file.");
        } catch (IOException e) {
            LOGGER.info("Failed to write the config file: " + fileName);
            e.printStackTrace();
        }
    }

    public static void loadBlacklist() {
        Properties properties = new Properties(defaultValues);

        try {
            FileReader configReader = new FileReader(fileName);
            properties.load(configReader);
            configReader.close();
        } catch (FileNotFoundException ignored) {
            // If the config does not exist, generate the default one.
            LOGGER.info("Generating the config file at: " + fileName);
            saveBlacklist();
            return;
        } catch (IOException e) {
            LOGGER.info("Failed to read the config file: " + fileName);
            e.printStackTrace();
            return;
        }

        //rmbTweak = parseIntOrDefault(properties.getProperty(Constants.CONFIG_RMB_TWEAK), 1) != 0;
        String[] blacklist = properties.getProperty(Constants.CONFIG_BLACKLIST).split(",");
        HelperFunctions.addDescribtionsToBlacklist(blacklist);

    }
    private static void writeList(FileWriter configWriter, String key, List<String> list) throws IOException {
        configWriter.write(key + "=");
        for (String s : list) {
            configWriter.write(s + ",");
        }
        configWriter.write("\n");
    }
    private static void writeString(FileWriter configWriter, String name, String value) throws IOException {
        configWriter.write(name + '=' + value + '\n');
    }

    static {
        // defaultValues.setProperty(Constants.CONFIG_RMB_TWEAK, "1");
        defaultValues.setProperty(Constants.CONFIG_BLACKLIST, String.join(",", Constants.DEFAULT_BLACKLIST));

    }
}