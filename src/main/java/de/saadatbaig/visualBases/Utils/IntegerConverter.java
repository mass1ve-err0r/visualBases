/*
 * Project: visualBases
 * Author:  Saadat M. Baig
 * Date:    24.02.21
 * License: BSD-3-Clause-Attribution
 */
package de.saadatbaig.visualBases.Utils;

import java.math.BigInteger;


public class IntegerConverter {

    public IntegerConverter() {/*code*/}

    public String convertFromDecimalTo(String numberString, int targetBase) {
        return convertFromBaseTo(10, targetBase, numberString);
    }

    public String convertFromBinTo(String numberString, int targetBase) {
        return convertFromBaseTo(2, targetBase, numberString);
    }

    public String convertFromHexTo(String numberString, int targetBase) {
        return convertFromBaseTo(16, targetBase, numberString);
    }

    public String convertFromOctTo(String numberString, int targetBase) {
        return convertFromBaseTo(8, targetBase, numberString);
    }

    private String convertFromBaseTo(int from, int target, String numberString) {
        BigInteger temp = new BigInteger(numberString, from);
        return temp.toString(target);
    }


    /* End */
}