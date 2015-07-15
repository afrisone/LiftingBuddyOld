package afrisone.liftingbuddy;

/**
 * Created by Dascki on 7/14/2015.
 */

public class Calculator {
    private String getGender(){
        String gender = (new CalculatorActivity().getGender());

        return gender;
    }

    

    private double getHeight() {
        Boolean metric = true;
        double height;
        int feet = 1;
        int inches = 1;
        if (metric) {
            height = 1;
        } else {
            height = feetToCm(feet, inches);
        }
        return height;
    }

    private double feetToCm(int feet, int inches){
        inches += (feet * 12);
        double centimeters = (double)inches * 2.54;
        return centimeters;
    }
}
