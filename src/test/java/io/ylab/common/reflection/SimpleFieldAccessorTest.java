package io.ylab.common.reflection;

import lombok.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SimpleFieldAccessorTest {
    @Test
    void create_shouldExtractPropertiesFromInstance() {
        final TestObject obj = new TestObject("John Doe", 30, true);

        final SimpleFieldAccessor<TestObject> accessor = SimpleFieldAccessor.create(obj);

        assertAll(
                () -> assertEquals(obj.getName(), accessor.getFieldValue("name")),
                () -> assertEquals(obj.getAge(), accessor.getFieldValue("age")),
                () -> assertEquals(obj.isActive(), accessor.getFieldValue("isActive"))
        );
    }

    @Test
    void getFieldValue_nonExistingField_shouldReturnNull() {
        final TestObject obj = new TestObject("Test", 10, false);

        final SimpleFieldAccessor<TestObject> accessor = SimpleFieldAccessor.create(obj);

        assertNull(accessor.getFieldValue("nonExistingField"));
    }

    @Value
    public static class TestObject {
        String name;
        int age;
        boolean isActive;
    }
}