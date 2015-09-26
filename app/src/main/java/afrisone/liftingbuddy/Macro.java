package afrisone.liftingbuddy;

/**
 * Created by Dascki on 9/25/2015.
 */
public class Macro {
    int calories;
    int protein;
    int fat;
    int carbohydrates;

    public Macro(){}

    public Macro(int calories, int protein, int fat, int carbohydrates){
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
    }

    public int getCalories(){
        return this.calories;
    }

    public int getProtein(){
        return this.protein;
    }

    public int getFat(){
        return this.fat;
    }

    public int getCarbohydrates(){
        return this.carbohydrates;
    }
}
