/*
 * Project: visualBases
 * Author:  Saadat M. Baig
 * Date:    24.02.21
 * License: BSD-3-Clause-Attribution
 */
package de.saadatbaig.visualBases.Utils;

import java.math.BigInteger;


public class ConverterIR {


    public ConverterIR() {/*code*/}


    /*-------------------- Integral Conversions --------------------*/

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


    /*-------------------- Rational Conversions --------------------*/

    public String convertFromDecimalToHexR(String numberString) {
        String earlyReturn = perhapsNoRationalPart(10, 16, numberString);
        if (earlyReturn != null) {
            return earlyReturn;
        } else {
            String[] splitNum = numberString.split("\\.");
            String rationalPart = getRationalPartForBase(16, Double.parseDouble("0." + splitNum[1]));
            String integerPart = convertFromBaseTo(10, 16, splitNum[0]);
            return integerPart + "." + rationalPart;
        }
    }

    public String convertFromDecimalToBinR(String numberString) {
        String earlyReturn = perhapsNoRationalPart(10, 2, numberString);
        if (earlyReturn != null) {
            return earlyReturn;
        } else {
            String[] splitNum = numberString.split("\\.");
            String rationalPart = getRationalPartForBase(2, Double.parseDouble("0." + splitNum[1]));
            String integerPart = convertFromBaseTo(10, 2, splitNum[0]);
            return integerPart + "." + rationalPart;
        }
    }

    public String convertFromDecimalToOctR(String numberString) {
        String earlyReturn = perhapsNoRationalPart(10, 8, numberString);
        if (earlyReturn != null) {
            return earlyReturn;
        } else {
            String[] splitNum = numberString.split("\\.");
            String rationalPart = getRationalPartForBase(8, Double.parseDouble("0." + splitNum[1]));
            String integerPart = convertFromBaseTo(10, 8, splitNum[0]);
            return integerPart + "." + rationalPart;
        }
    }

    public String convertBinToDecimalR(String numberString) {
        String earlyReturn = perhapsNoRationalPart(2, 10, numberString);
        if (earlyReturn != null) {
            return earlyReturn;
        } else {
            String[] splitNum = numberString.split("\\.");
            String rationalPart = solveRationalPolynomialForDec(2, splitNum[1]);
            String integerPart = convertFromBaseTo(2, 10, splitNum[0]);
            return integerPart + "." + rationalPart;
        }
    }

    public String convertHexToDecimalR(String numberString) {
        String earlyReturn = perhapsNoRationalPart(16, 10, numberString);
        if (earlyReturn != null) {
            return earlyReturn;
        } else {
            String[] splitNum = numberString.split("\\.");
            String rationalPart = solveRationalPolynomialForDec(16, splitNum[1]);
            String integerPart = convertFromBaseTo(16, 10, splitNum[0]);
            return integerPart + "." + rationalPart;
        }
    }

    public String convertOctToDecR(String numberString) {
        String earlyReturn = perhapsNoRationalPart(8, 10, numberString);
        if (earlyReturn != null) {
            return earlyReturn;
        } else {
            String[] splitNum = numberString.split("\\.");
            String rationalPart = solveRationalPolynomialForDec(8, splitNum[1]);
            String integerPart = convertFromBaseTo(8, 10, splitNum[0]);
            return integerPart + "." + rationalPart;
        }
    }


    /*-------------------- Private Helpers --------------------*/

    private String convertFromBaseTo(int from, int target, String numberString) {
        BigInteger temp = new BigInteger(numberString, from);
        return temp.toString(target);
    }

    private String perhapsNoRationalPart(int base, int to, String numberString) {
        if (numberString.indexOf('.') == -1) {
            return convertFromBaseTo(base, to, numberString);
        } else if (numberString.endsWith(".")) {
            return convertFromBaseTo(base, to, numberString.substring(0, numberString.length()-1));
        } else if (numberString.startsWith(".")) {
            return convertFromBaseTo(base, to, numberString.substring(1));
        } else return null;
    }

    private String getRationalPartForBase(int target, double number) {
        StringBuilder out = new StringBuilder();
        int intPart;
        double rationalPart;
        for (int i = 0; i < 10; i++) {
            number *= target;
            rationalPart = number % 1;
            intPart = ((int)(number - rationalPart));
            switch (target) {
                case 2:
                    out.append(Integer.toBinaryString(intPart));
                    break;
                case 8:
                    out.append(Integer.toOctalString(intPart));
                    break;
                case 16:
                    out.append(Integer.toHexString(intPart));
                    break;
            }
            if (rationalPart == 0.0) {
                break;
            } else {
                number = rationalPart;
            }
        }
        return out.toString();
    }

    private String solveRationalPolynomialForDec(int originBase, String number) {
        double back = 0.0;
        for (int i = 0; i < number.length(); i++) {
            if (originBase == 16) {
                back += ((Character.digit(number.charAt(i), 16)) * (1/(Math.pow(originBase, (i+1)))));
            } else {
                back += (((number.charAt(i) - '0')) * (1/(Math.pow(originBase, (i+1)))));
            }
        }
        return String.valueOf(back).substring(2);
    }


    /* End */
}