package com.jsoncomparator.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JSONFlattenUtilTest {
    private JSONFlattenUtil jsonFlattenUtil;
    private String propSeparator;

    @Before
    public void setUp() {
        jsonFlattenUtil = new JSONFlattenUtil();
        propSeparator = JSONFlattenUtil.NESTED_PROPERTY_SEPARATOR;
    }

    private void testWithExpectedJsonArray(String expectedJson, String originalJson) {
        Assert.assertEquals(expectedJson, jsonFlattenUtil.flattenJsonArrayString(originalJson));
    }

    private void testWithExpectedJsonObject(String expectedJson, String originalJson) {
        //converting expected and original json objects to json arrays of single element
        String expectedJsonArray = "[" + expectedJson + "]";
        String originalJsonArray = "[" + originalJson + "]";
        testWithExpectedJsonArray(expectedJsonArray, originalJsonArray);
    }

    @Test
    public void testEmptyJson() {
        testWithExpectedJsonObject("", "");
    }

    @Test
    public void testFlattenedJsonObject() {
        //{a: 1, b : 2}
        String json = "{\"a\":1,\"b\":2}";
        testWithExpectedJsonObject(json, json);
    }

    @Test
    public void testFlatteningJsonArray() {
        //[{a: 1}, {b : 2}]
        String json = "[{\"a\":1},{\"b\":2}]";
        testWithExpectedJsonArray(json, json);
    }

    @Test
    public void testNestedJsonObject() {
        //{a: 1, b : {c: 1, d: 2}}
        String originalJson = "{\"a\":1,\"b\":{\"c\":1,\"d\":2}}";
        String flattenedJson = String.format("{\"a\":1,\"b%sc\":1,\"b%sd\":2}", propSeparator, propSeparator);
        testWithExpectedJsonObject(flattenedJson, originalJson);
    }

    @Test
    public void testNestedMultipleLevelsJsonObject() {
        //{a: 1, b : {c: 1, d: 2}}
        String originalJson = "{\"a\":1,\"b\":{\"c\":1,\"d\":{e:3}}}";
        String flattenedJson = String.format("{\"a\":1,\"b%sc\":1,\"b%sd%se\":3}", propSeparator, propSeparator, propSeparator);
        testWithExpectedJsonObject(flattenedJson, originalJson);
    }

    @Test
    public void testNestedJsonObjectWithSamePropertyName() {
        //{a: 1, b : {a: 1, b: 2}}
        String originalJson = "{\"a\":1,\"b\":{\"a\":1,\"b\":2}}";
        String flattenedJson = String.format("{\"a\":1,\"b%sa\":1,\"b%sb\":2}", propSeparator, propSeparator);
        testWithExpectedJsonObject(flattenedJson, originalJson);
    }

    @Test
    public void testNestedJsonWithEmptyObject() {
        // {a: 1, b: {}} -> {a: 1}
        String originalJson = "{\"a\":1,\"b\":{}}";
        String flattenedJson = "{\"a\":1}";
        testWithExpectedJsonObject(flattenedJson, originalJson);
    }
}
