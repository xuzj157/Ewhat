package oteher;

/**
 * Before you read this code, please make sure you have read the README in this project.Thanks!
 * <p>
 * Created by xuzj157 on 2016/11/9.
 *
 * 实体类
 */

public class FoodInfo {

    public static int BREAKFAST = 1;
    public static int LUNCH = 2;
    public static int DINNER = 3;

    private int foodKind;
    private String foodName;

    public FoodInfo(String foodName, int foodKind) {
        this.foodName = foodName;
        this.foodKind = foodKind;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodKind() {
        return foodKind;
    }

    public void setFoodKind(int foodKind) {
        this.foodKind = foodKind;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
