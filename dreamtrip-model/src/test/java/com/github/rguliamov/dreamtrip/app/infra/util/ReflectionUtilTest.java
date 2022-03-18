package com.github.rguliamov.dreamtrip.app.infra.util;

import com.github.rguliamov.dreamtrip.app.infra.util.exception.ConfigurationException;
import com.github.rguliamov.dreamtrip.app.infra.util.helperClasses.A;
import com.github.rguliamov.dreamtrip.app.infra.util.helperClasses.B;
import com.github.rguliamov.dreamtrip.app.infra.util.helperClasses.C;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * contatins unit-tests to check functionality of {@link ReflectionUtil} class
 *
 * @author Guliamov Rustam
 */
class ReflectionUtilTest {
    @Test
    public void testCreateInstanceSuccess() {
        A aInstance = ReflectionUtil.createInstance(A.class);
        assertTrue(aInstance != null);
        assertEquals(aInstance.getId(), 1);
    }

    @Test
    public void testCreateInstanceWithNullClassFailure() {
        assertThrows(NullPointerException.class, () ->
                ReflectionUtil.createInstance(null));
    }

    @Test
    public void testCreateInstanceWithClassWithoutDefaultConstructorFailure() {
        assertThrows(ConfigurationException.class, () ->
                ReflectionUtil.createInstance(C.class));
    }

    @Test
    public void testFindSimilarFieldsSuccess() {
        List<String> fieldsList = ReflectionUtil.findSimilarFields(A.class, B.class);
        assertEquals(fieldsList.size(), 3);
        assertTrue(fieldsList.contains("age"));
        assertTrue(fieldsList.contains("name"));
        assertTrue(fieldsList.contains("id"));
    }

    @Test
    public void testFindSimilarFieldsWithNullFailure() {
        assertThrows(NullPointerException.class,
                () -> ReflectionUtil.findSimilarFields(null, A.class));
    }

    @Test
    public void testFindSimilarFieldsReturnsEmptyList() {
        List<String> fieldsList = ReflectionUtil.findSimilarFields(A.class, C.class);

        assertTrue(fieldsList.isEmpty());
    }

    @Test
    public void testCopyFieldsSuccess() {
        A a = new A(1, "Rustam", 10, 1L);
        List<String> familiarFields = ReflectionUtil.findSimilarFields(A.class, B.class);
        B b = new B();
        ReflectionUtil.copyFields(b, a, familiarFields);

        assertEquals(b.getAge(), a.getAge());
        assertEquals(b.getName(), a.getName());
        assertEquals(b.getId(), a.getId());
    }

    @Test
    public void testCopyFieldsWithNullParameter1Failure() {
        A a = null;
        List<String> familiarFields = ReflectionUtil.findSimilarFields(A.class, B.class);
        B b = new B();
        assertThrows(NullPointerException.class, () ->
                ReflectionUtil.copyFields(b, a, familiarFields));
    }

    @Test
    public void testCopyFieldsWithNullParameter2Failure() {
        A a = new A(1, "Rustam", 10, 1L);
        List<String> familiarFields = ReflectionUtil.findSimilarFields(A.class, B.class);
        B b = null;
        assertThrows(NullPointerException.class, () ->
                ReflectionUtil.copyFields(b, a, familiarFields));
    }

    @Test
    public void testCopyFieldsWithNullParameter3Failure() {
        A a = new A(1, "Rustam", 10, 1L);
        List<String> familiarFields = null;
        B b = new B();
        assertThrows(NullPointerException.class, () ->
                ReflectionUtil.copyFields(b, a, familiarFields));
    }
}