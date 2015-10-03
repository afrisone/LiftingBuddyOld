package afrisone.liftingbuddy;


//Class to hold macro sqlite table data
public class Macro {
    int _id;
    int _calories;
    int _protein;
    int _fat;
    int _carbohydrates;

    public Macro(){
        int _id = 1;
        int _calories = 0;
        int _protein = 0;
        int _fat = 0;
        int _carbohydrates = 0;
    }

    public Macro(int id, int calories, int protein, int fat, int carbohydrates){
        this._id = id;
        this._calories = calories;
        this._protein = protein;
        this._fat = fat;
        this._carbohydrates = carbohydrates;
    }

    public int getCalories(){
        return this._calories;
    }

    public int getProtein(){
        return this._protein;
    }

    public int getFat(){
        return this._fat;
    }

    public int getCarbohydrates(){
        return this._carbohydrates;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public void setCalories(int calories){
        this._calories = calories;
    }

    public void setProtein(int protein){
        this._protein = protein;
    }

    public void setFat(int fat){
        this._fat = fat;
    }

    public void setCarbohydrates(int carbohydrates){
        this._carbohydrates = carbohydrates;
    }
}
