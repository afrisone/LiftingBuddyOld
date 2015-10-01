package afrisone.liftingbuddy;


//Class to hold macro sqlite table data
public class Macro {
    int calories;
    int protein;
    int fat;
    int carbohydrates;
    int ID;

    public Macro(){}

    public Macro(int calories, int protein, int fat, int carbohydrates){
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.ID = 1;
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

    public int getID(){
        return this.ID;
    }
}
