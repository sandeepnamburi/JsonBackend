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

    private void testWithExpectedJson(String expectedJson, String originalJson) {
        Assert.assertEquals(expectedJson, jsonFlattenUtil.flattenJsonString(originalJson));
    }

    @Test
    public void testEmptyJson() {
        testWithExpectedJson("", "");
    }

    @Test
    public void testFlattenedJson() {
        //{a: 1, b : 2}
        String json = "{\"a\":1,\"b\":2}";
        testWithExpectedJson(json, json);
    }

    @Test
    public void testNestedJson() {
        //{a: 1, b : {c: 1, d: 2}}
        String originalJson = "{\"a\":1,\"b\":{\"c\":1,\"d\":2}}";
        String flattenedJson = String.format("{\"a\":1,\"b%sc\":1,\"b%sd\":2}", propSeparator, propSeparator);
        testWithExpectedJson(flattenedJson, originalJson);
    }

    @Test
    public void testNestedMultipleLevelsJson() {
        //{a: 1, b : {c: 1, d: 2}}
        String originalJson = "{\"a\":1,\"b\":{\"c\":1,\"d\":{e:3}}}";
        String flattenedJson = String.format("{\"a\":1,\"b%sc\":1,\"b%sd%se\":3}", propSeparator, propSeparator, propSeparator);
        testWithExpectedJson(flattenedJson, originalJson);
    }

    @Test
    public void testNestedJsonWithSamePropertyName() {
        //{a: 1, b : {a: 1, b: 2}}
        String originalJson = "{\"a\":1,\"b\":{\"a\":1,\"b\":2}}";
        String flattenedJson = String.format("{\"a\":1,\"b%sa\":1,\"b%sb\":2}", propSeparator, propSeparator);
        testWithExpectedJson(flattenedJson, originalJson);
    }

    @Test
    public void testNestedJsonWithEmptyObject() {
        // {a: 1, b: {}} -> {a: 1}
        String originalJson = "{\"a\":1,\"b\":{}}";
        String flattenedJson = "{\"a\":1}";
        testWithExpectedJson(flattenedJson, originalJson);
    }
}
