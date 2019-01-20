/*
 * Created on 09-Jun-2005
 *
 */
package uk.co.indigo.play.array_combine;

/**
 * @author milbuw
 *
 */
public class ArrayCombiner {

    public Object[] combineArrays(Object[] array1, Object[] array2) {
        Object[] result = new Object[array1.length + array2.length];
        int j = 0;
        for (int i = 0; i < array1.length; i++) {
            result[j] = array1[i];
            j++;
        }
        for (int i = 0; i < array2.length; i++) {
            result[j] = array2[i];
            j++;
        }
        return result;
    }
    
}
