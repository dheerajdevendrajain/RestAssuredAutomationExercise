package context;

import io.restassured.response.Response;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ScenarioContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioContext.class);
    private Map<String, Object> scenarioData = new HashMap<>();
    @Getter
    private Response latestResponse; // To store the last API response for easy access

    public void set(String key, Object value) {
        scenarioData.put(key, value);
        LOGGER.debug("ScenarioContext: Set '{}' with value: {}", key, value);
    }

    public <T> T get(String key) {
        LOGGER.debug("ScenarioContext: Attempting to get value for key '{}'", key);
        return (T) scenarioData.get(key);
    }


    public <T> Optional<T> getOptional(String key) {
        LOGGER.debug("ScenarioContext: Attempting to get optional value for key '{}'", key);
        return Optional.ofNullable((T) scenarioData.get(key));
    }

    public boolean contains(String key) {
        return scenarioData.containsKey(key);
    }

    public void setLatestResponse(Response response) {
        this.latestResponse = response;
        LOGGER.debug("ScenarioContext: Latest response set.");
    }

    public void clear() {
        scenarioData.clear();
        latestResponse = null;
        LOGGER.debug("ScenarioContext: Cleared all data.");
    }
}