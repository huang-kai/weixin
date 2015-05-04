package net.home.pojo;

import java.time.LocalDate;

import net.home.mongo.MongoFacade;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.utils.LongIdEntity;

@Entity(value="recipes", noClassnameStored=true)
public class Recipe extends LongIdEntity{
    public enum TYPE {
        in ,out;
    }

    public Recipe() {
        super(MongoFacade.getInstance().getdataStore());
    }
    
    @Indexed
    @Property("user_id")
    private String userId;
    
    @Indexed
    private String type;
    
    private double sum;
    
    private String account;
    
    @Indexed
    private long date=-1;
    
    @Indexed 
    private int year;
    
    private int month;
    
    private int day;
    
    @Indexed
    private String level1;
    @Indexed
    private String level2;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getDate() {
        if (date==-1){
            LocalDate localDate = LocalDate.of(year, month, day);
            date = localDate.toEpochDay();
        }
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getLevel1() {
        return level1;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getLevel2() {
        return level2;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    @Override
    public String toString() {
        return "Recipe [id=" + getMyLongId() + ", userId=" + userId + ", type=" + type + ", sum=" + sum + ", account=" + account
                + ", date=" + date + ", level1=" + level1 + ", level2=" + level2 + "]";
    }
}
