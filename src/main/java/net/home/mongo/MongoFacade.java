package net.home.mongo;

import java.math.BigDecimal;

import net.home.pojo.Budget;
import net.home.pojo.Recipe;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

public class MongoFacade {

    private static final Logger logger = LoggerFactory.getLogger(MongoFacade.class.getName());
    private MongoClient mongo = null;
    private Morphia morphia = null;
    private Datastore ds = null;

    private static MongoFacade instance = null;

    public static final int DEFAULT_CONNECTIONS_PER_HOST = 5;

    public static final int DEFAULT_CONN_TIMEOUT_MS = 15000;

    public static final int DEFAULT_SOCKET_TIMEOUT_MS = 15000;

    public static final int DEFAULT_THREAD_WAIT_MS = 30000;

    public static final int DEFAULT_BLOCKING_CONNECTION_MULTIPLIER = 3;

    private MongoFacade(String mongoServer) {

        ServerAddress address = new ServerAddress(mongoServer);
        MongoClientOptions options = MongoClientOptions.builder().connectTimeout(DEFAULT_CONN_TIMEOUT_MS)
                .connectionsPerHost(DEFAULT_CONNECTIONS_PER_HOST).socketTimeout(DEFAULT_SOCKET_TIMEOUT_MS)
                .threadsAllowedToBlockForConnectionMultiplier(DEFAULT_BLOCKING_CONNECTION_MULTIPLIER)
                .maxWaitTime(DEFAULT_THREAD_WAIT_MS).socketKeepAlive(true).build();
        mongo = new MongoClient(address, options);
        morphia = new Morphia();
        morphia.map(Recipe.class).map(Budget.class);
        ds = morphia.createDatastore(mongo, "journal");
        ds.ensureIndexes();
        ;
    }

    public static synchronized MongoFacade getInstance() {
        if (instance == null) {
            instance = new MongoFacade("10.100.1.136");
        }
        return instance;
    }

    public final Datastore getdataStore() {
        return ds;
    }

    /**
     * create or update a recipe
     * 
     * @throws MongoAccessException
     */

    public void createOrUpdateRecipe(Recipe recipe, String userId) {
        Query<Recipe> query = ds.createQuery(Recipe.class).field("userId").equal(recipe.getUserId());
        ds.updateFirst(query, recipe, true);

    }

    /**
     * Delete a recipe by recipeId
     */

    public void deleteRecipeById(long recipeId) {
        Query<Recipe> query = ds.createQuery(Recipe.class).field("myLongId").equal(recipeId);
        ds.delete(query);
    }

    public long createRecipe(Recipe recipe) {
        Key<Recipe> key = ds.save(recipe);
        return ((Long) key.getId()).longValue();
    }

    
    public BigDecimal calculateSum(String userId, int year, int month, int day, Recipe.TYPE type) {
        
        Query<Recipe> query = ds.createQuery(Recipe.class).filter("user_id =", userId).filter("year =", year).filter("type =", String.valueOf(type));
        if (month != 0){
            query = query.filter("month =", month);
        }
        if (day !=0){
            query = query.filter("day =", day);
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (Recipe recipe : query) {
            BigDecimal money = new BigDecimal(recipe.getSum());
            sum = sum.add(money);
        }
        return sum;
    }

   

    public BigDecimal getBudgetByUser(String userId) {
        Query<Budget> query = ds.createQuery(Budget.class).field("user_id").equal(userId);
        Budget budget = query.get();
        if (budget != null) {
            return new BigDecimal(budget.getBudget());
        } else {
            return null;
        }
    }

    public void setBudget(Budget budget) {
        Query<Budget> query = ds.createQuery(Budget.class).field("user_id").equal(budget.getUserId());
        ds.updateFirst(query, budget, true);
    }

}
