package com.jsoncomparator.util;

import com.google.gson.*;

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
     * Flattens a Json Array string.
     * Iterates over each JsonObject in array and flattens the object.
     * <p>
     * If the string is not a json array or an element of json array is not a json object, throws exception.
     *
     * @param originalJson the json of an array to be flattened
     * @return flattened json string where each element of the array has been flattened.
     */
    public String flattenJsonArrayString(String originalJson) {
        JsonElement root = jsonParser.parse(originalJson);
        if (!root.isJsonArray()) {
            throw new IllegalArgumentException("Expected a string representing a JSON array.");
        }
        JsonArray rootArray = root.getAsJsonArray();
        JsonArray flattenedArray = new JsonArray();
        for (JsonElement elem : rootArray) {
            if (!elem.isJsonObject()) {
                throw new IllegalArgumentException(String.format("Each element of the root array is " +
                        "expected to be a JsonObject. Found %s", elem.toString()));
            }
            JsonObject flattenedObject = new JsonObject();
            recFlatten(flattenedObject, elem.getAsJsonObject(), "");
            flattenedArray.add(flattenedObject);
        }
        return gson.toJson(flattenedArray);
    }

    /**
     * Only flattens nested objects, does not manipulate arrays.
     * If the root object has a property that has an array as a value,
     * the array is ignored as it is and not parsed.
     */
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
