package com.api.growin.utils;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Utility class for performing partial updates (patch operations) on Java objects.
 * <p>
 *     This class provides methods to copy only the non-null properties from a source object
 *     to a target object, effectively enabling selective updates without overwriting
 *     existing values with {@code null}.
 * </p>
 */
public class PatchUtility {

    /**
     * Copies all non-null properties from the source object to the target object.
     * <p>
     *     This method uses Spring's {@link BeanUtils#copyProperties(Object, Object, String...)}
     *     internally while ignoring all properties with {@code null} values in the source object.
     *     It is especially useful for implementing PATCH endpoints or partial updates in services.
     * </p>
     *
     * @param source the object containing updated values (some may be null)
     * @param target the object to which non-null values should be copied
     */
    private static String[] getNonNullPropertyNames(Object source) {
        /* Initialize wrappers */
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();

        /* Map all property to the set */
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];

        return emptyNames.toArray(result);
    }

    /**
     * Retrieves the names of all properties with {@code null} values from the given object.
     * <p>
     *     This method uses Spring's {@link BeanWrapper} to introspect the properties of the
     *     source object and collects the names of those which have {@code null} values.
     *     These names are later used to ignore null fields during property copying.
     * </p>
     *
     * @param source the object to inspect
     * @return an array of property names that are {@code null} in the source object
     */
    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNonNullPropertyNames(source));
    }
}