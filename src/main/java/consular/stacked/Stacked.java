package consular.stacked;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stacked implements ModInitializer {
	
    public static final Logger LOGGER = LoggerFactory.getLogger("stacked");

	@Override
	public void onInitialize() {
		ConfigManager.loadConfig();
		LOGGER.info("initialized");
	}
}