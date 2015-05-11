package net.home.mongo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import junit.framework.TestCase;
import net.home.pojo.Recipe;

import org.junit.Test;
import org.mongodb.morphia.query.Query;

public class MongoFacadeTest extends TestCase{
    private MongoFacade mongo = MongoFacade.getInstance();
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    
    public void setUp(){
//        createRecipe();
    }
    
    public void tearDown(){
        
    }
    
//    @Test
    private void createRecipe() {
        
        Recipe recipe = new Recipe();
        recipe.setAccount("现金");
        recipe.setDate(LocalDate.now().toEpochDay());
        recipe.setYear(2015);
        recipe.setMonth(5);
        recipe.setDay(6);
        recipe.setLevel1("食物");
        recipe.setLevel2("中饭");
        recipe.setSum(50);
        recipe.setType(Recipe.TYPE.out.toString());
        recipe.setUserId("kyne");
        long id = mongo.createRecipe(recipe);
        System.out.println(id);
        Recipe rec = mongo.queryRecipeById(id);
        assertEquals("现金", rec.getAccount());
    }

    @Test
    public void testQueryRecipe() {
        List<Recipe> recipeList = mongo.queryRecipeByDate("kyne", 2015, 5, 7);
        for (Recipe recipe: recipeList){
            System.out.println(recipe);
        }
        recipeList = mongo.queryRecipeByDate("kyne", 2015, 5, 0);
        for (Recipe recipe: recipeList){
            System.out.println(recipe);
        }
    }
    
    @Test
    public void testCalculateSumByType(){
        BigDecimal sum = mongo.calculateSumByType("kyne", 2015, 5, 0, Recipe.TYPE.out);
        System.out.println("Sum in 5 month: " + df.format(sum));
    }
    
    @Test
    public void testCalculateSumByLevel(){
        BigDecimal sum = mongo.calculateSumByLevel("kyne", 2015, 5, 0, "食物","");
        System.out.println("Sum in 食物 : " + df.format(sum));
    }
}
