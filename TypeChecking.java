/*
 *  Author: Alex Thomas
 *  Creation Date: 10/16/2020
 *  Last Modified: 10/16/2020
 *  Purpose: Functionality for checking Types and Parameterized Types in Runtime
 * 
 */

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class TypeChecking {
    
    // Static Methods

    // Check to see if two objects share the same parameterized types AKA <T> = <T>
    public static boolean sameParameterizedTypes(Object obj1, Object obj2) {
        Type[] obj1ParameterizedTypes = getParameterizedTypes(obj1);
        Type[] obj2ParameterizedTypes = getParameterizedTypes(obj2);
        return Arrays.equals(obj1ParameterizedTypes,obj2ParameterizedTypes);
    }

    // Check to see if an object matches this ParameterizedType type
    public static boolean parameterizedTypeIs(Object obj, Type type) {
        Type[] objParameterizedTypes = getParameterizedTypes(obj);        
        return sameParameterizedTypes(objParameterizedTypes, type);
    }

    // Returns the Parameterized Types of an Object
    public static Type getParameterizedType(Object obj) {
        if (obj == null) return null;
        Type[] objParameterizedTypes = getParameterizedTypes(obj);        
        return (objParameterizedTypes.length == 0 ? null : objParameterizedTypes[0]);
    }

    // Check to see if two objects share the same base Class type
    public static boolean sameClassTypes(Object obj1, Object obj2) {  
        if (obj1 == obj2) return true;
        if (obj1 == null || obj2 == null) return false;      
        return getClass(obj1).equals(getClass(obj2));
    }

    // Returns the class of an object
    public static Class<?> getClass(Object obj) {
        return obj.getClass();
    }

    // NOTE: you CAN'T cast getGenericSuperclass() to ParameterizedType unless there is a ParameterizedType
    // Returns an array of all the parameterized types of an Object if it has any, otherwise an empty array
    public static Type[] getParameterizedTypes(Object obj) {
        try {
            return ((ParameterizedType) obj.getClass().getGenericSuperclass()).getActualTypeArguments();
        } catch(Exception e) {
            return new Type[] {};
        }
    }

}
