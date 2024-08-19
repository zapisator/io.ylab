package io.ylab.common.reflection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PropertyExtractorTest {

    @Test
    void getPropertyValue_existingProperty_shouldReturnValue() {
        final TestObject obj = new TestObject("John Doe", 25, true);
        final PropertyExtractor<TestObject> extractor = PropertyExtractor.of(TestObject.class);

        final Map<String, Object> fieldValues = extractor.fieldValuesOf(obj);

        assertAll(
                () -> assertEquals(obj.getName(), fieldValues.get("name")),
                () -> assertEquals(obj.getAge(), fieldValues.get("age")),
                () -> assertEquals(obj.isActive(), fieldValues.get("isActive"))
        );
    }

    @Test
    void getPropertyValue_passInterface_throws() {
        assertThrows(IllegalArgumentException.class, () -> PropertyExtractor.of(Serializable.class));
    }

    @Test
    void fieldValuesOf_shouldReturnEmptyMapForObjectWithNoFields() {
        final EmptyObject obj = new EmptyObject();
        final PropertyExtractor<EmptyObject> extractor = PropertyExtractor.of(EmptyObject.class);

        assertTrue(extractor.fieldValuesOf(obj).isEmpty());
    }

    @Test
    void fieldValuesOf_withNoGetterForField_shouldNotIncludeFieldInMap() {
        final TestObjectWithOutGetterOnOneField obj = new TestObjectWithOutGetterOnOneField("John Doe", 30, true);
        final PropertyExtractor<TestObjectWithOutGetterOnOneField> extractor
                = PropertyExtractor.of(TestObjectWithOutGetterOnOneField.class);
        final Map<String, Object> expectedFieldValues = new HashMap<>();

        expectedFieldValues.put("name", "John Doe");
        expectedFieldValues.put("isActive", true);

        assertEquals(expectedFieldValues, extractor.fieldValuesOf(obj));
    }

    @Value
    static class TestObject implements Serializable {
        String name;
        int age;
        boolean isActive;
    }

    @RequiredArgsConstructor
    static class TestObjectWithOutGetterOnOneField {
        @Getter
        private final String name;
        private final int age;
        @Getter
        private final boolean isActive;
    }

    @Value
    static class EmptyObject {
    }

}