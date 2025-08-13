package consular.stacked;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ConfigManager {

    private static JsonObject config;
    private static final Path CONFIG_PATH = Path.of("config", "stacked.json");

    // Load the config file from the server's config directory
    public static void loadConfig() {
        try {
            if (!Files.exists(CONFIG_PATH)) {
                // Create default config if it doesn't exist
                Files.createDirectories(CONFIG_PATH.getParent());
                Files.write(CONFIG_PATH, getDefaultConfig().toString().getBytes());
            }

            // Read and parse the JSON config file
            InputStreamReader reader = new InputStreamReader(Files.newInputStream(CONFIG_PATH));
            config = JsonParser.parseReader(reader).getAsJsonObject();

            // Ensure all expected keys exist in the config
            boolean updated = false;
            JsonObject defaultConfig = getDefaultConfig();

            for (String key : defaultConfig.keySet()) {
                if (!config.has(key)) {
                    config.add(key, defaultConfig.get(key));
                    updated = true;
                }
            }

            // Save updated config if new keys were added
            if (updated) {
                Files.write(CONFIG_PATH, config.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Generate the default config structure
    private static JsonObject getDefaultConfig() {
        JsonObject defaultConfig = new JsonObject();
        defaultConfig.addProperty("maxStackSize", 99);
        return defaultConfig;
    }

    // Accessor methods for config values
    public static int getMaxStackSize() {
        return config.get("maxStackSize").getAsInt();
    }
}