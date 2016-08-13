package com.nirmit.markbook;

/**
 * Created by ninizinzu on 16-07-21.
 */
public class finalGPAScale  {

    public static final String FAIL = "Inadeqate";
    public static final String GRADE_D = "Marginal";
    public static final String GRADE_C = "Adequate";
    public static final String GRADE_B = "Good";
    public static final String GRADE_A = "Excellent";

    private static double aPlusGPA = 4.0, aGPA = 4.0, aMinusGPA = 3.7,
            bPlusGPA = 3.3, bGPA = 3.0, bMinusGPA = 2.7,
            cPlusGPA = 2.3, cGPA = 2.0, cMinusGPA = 1.7,
            dPlusGPA = 1.3, dGPA = 1.0, dMinusGPA = 0.7,
            fGPA = 0;

    public double GPAperCourse (double mark) {
        double gpa = 0;

        if (mark <= 49) {
            return getfGPA();
        } else if (mark <= 52) {
           return getdMinusGPA();
        } else if (mark <= 56) {
            return getdGPA();
        } else if (mark <= 59) {
            return getdPlusGPA();
        } else if (mark <= 62) {
            return getcMinusGPA();
        } else if (mark <= 66) {
            return getcGPA();
        } else if (mark <= 69) {
            return getcPlusGPA();
        } else if (mark <= 72) {
            return getbMinusGPA();
        }else if (mark <= 76) {
            return getbGPA();
        }else if (mark <= 79) {
            return getbPlusGPA();
        } else if (mark <= 84) {
            return getaMinusGPA();
        } else if (mark <= 89) {
            return getaGPA();
        } else if (mark <= 100) {
            return getaPlusGPA();
        }
            return 0;
    }

    public static double getaPlusGPA() {
        return aPlusGPA;
    }

    public static double getaGPA() {
        return aGPA;
    }

    public static double getbPlusGPA() {
        return bPlusGPA;
    }

    public static double getaMinusGPA() {
        return aMinusGPA;
    }

    public static double getbGPA() {
        return bGPA;
    }

    public static double getbMinusGPA() {
        return bMinusGPA;
    }

    public static double getcPlusGPA() {
        return cPlusGPA;
    }

    public static double getcGPA() {
        return cGPA;
    }

    public static double getcMinusGPA() {
        return cMinusGPA;
    }

    public static double getdPlusGPA() {
        return dPlusGPA;
    }

    public static double getdGPA() {
        return dGPA;
    }

    public static double getdMinusGPA() {
        return dMinusGPA;
    }

    public static double getfGPA() {
        return fGPA;
    }


    // Setters
    public static void setaPlusGPA(double aPlusGPA) {
        finalGPAScale.aPlusGPA = aPlusGPA;
    }

    public static void setaGPA(double aGPA) {
        finalGPAScale.aGPA = aGPA;
    }

    public static void setaMinusGPA(double aMinusGPA) {
        finalGPAScale.aMinusGPA = aMinusGPA;
    }

    public static void setbMinusGPA(double bMinusGPA) {
        finalGPAScale.bMinusGPA = bMinusGPA;
    }

    public static void setbGPA(double bGPA) {
        finalGPAScale.bGPA = bGPA;
    }

    public static void setbPlusGPA(double bPlusGPA) {
        finalGPAScale.bPlusGPA = bPlusGPA;
    }

    public static void setcPlusGPA(double cPlusGPA) {
        finalGPAScale.cPlusGPA = cPlusGPA;
    }

    public static void setdPlusGPA(double dPlusGPA) {
        finalGPAScale.dPlusGPA = dPlusGPA;
    }

    public static void setcGPA(double cGPA) {
        finalGPAScale.cGPA = cGPA;
    }

    public static void setcMinusGPA(double cMinusGPA) {
        finalGPAScale.cMinusGPA = cMinusGPA;
    }

    public static void setdGPA(double dGPA) {
        finalGPAScale.dGPA = dGPA;
    }

    public static void setfGPA(double fGPA) {
        finalGPAScale.fGPA = fGPA;
    }

    public static void setdMinusGPA(double dMinusGPA) {
        finalGPAScale.dMinusGPA = dMinusGPA;
    }
}
