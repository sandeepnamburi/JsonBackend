package com.jsoncomparator.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

public class JSONFlattenUtil {
    public static final String NESTED_PROPERTY_SEPARATOR = "-";
    private Gson gson;
    private JsonParser jsonParser;

    public JSONFlattenUtil() {
        jsonParser = new JsonParser();
        gson = new Gson();
    }

    /**
     * Only flattens nested objects, does not manipulate arrays.
     * If the root object is an array or at any point a property is an array, it skips over that.
     *
     * @param originalJson the json to be flattened
     * @return flattened json string.
     */
    public String flattenJsonString(String originalJson) {
        JsonElement root = jsonParser.parse(originalJson);
        if (!root.isJsonObject()) {
            return originalJson;
        }
        JsonObject flattenedObject = new JsonObject();
        recFlatten(flattenedObject, root.getAsJsonObject(), "");
        return gson.toJson(flattenedObject);
    }

    private JsonObject recFlatten(JsonObject flattenedObject, JsonObject nestedObject, String property_prefix) {
        for (Map.Entry<String, JsonElement> entry : nestedObject.entrySet()) {
            if (entry.getValue().isJsonObject()) {
                recFlatten(flattenedObject, entry.getValue().getAsJsonObject(), property_prefix + entry.getKey() + NESTED_PROPERTY_SEPARATOR);
            } else {
                flattenedObject.add(property_prefix + entry.getKey(), entry.getValue());
            }
        }
        return flattenedObject;
    }
}
