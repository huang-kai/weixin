package net.home.handlers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import net.home.exception.ErrCode;
import net.home.exception.GeneralException;
import net.home.mongo.MongoFacade;
import net.home.pojo.IncomingMessage;
import net.home.pojo.Recipe;
import net.home.util.Validater;

import org.apache.commons.lang.StringUtils;

public class JournalHandler implements Handler{

    private MongoFacade mongoFacade = MongoFacade.getInstance();

    public String handleMsg(IncomingMessage msg) {
        String context = msg.getContent();
        if (!context.startsWith("$")) {
            return "";
        }
        String userId = msg.getFromUserName();
        Recipe recipe = prepareRecipe(context);
        recipe.setUserId(userId);
        long id = mongoFacade.createRecipe(recipe);
        BigDecimal out = mongoFacade.calculateSumByDate(msg.getFromUserName(), recipe.getYear(), recipe.getMonth(),0,Recipe.TYPE.out);
        BigDecimal in = mongoFacade.calculateSumByDate(msg.getFromUserName(), recipe.getYear(), recipe.getMonth(),0,Recipe.TYPE.in);
        BigDecimal budget = mongoFacade.getBudgetByUser(userId);
        String result = prepareResult(id, in,out, budget);
        return result;
    }

    private String prepareResult(long id, BigDecimal in, BigDecimal out, BigDecimal budget) {
        StringBuffer sb = new StringBuffer();
        DecimalFormat df = new DecimalFormat("#,###.00");
        sb.append("流水账记录成功，记录ID：");
        sb.append(id);
        sb.append("，本月支出");
        sb.append(df.format(out));
        sb.append("元, 本月收入");
        sb.append(df.format(in));
        sb.append("元");
        if (budget.equals(BigDecimal.ZERO)) {
            sb.append("，您还没有设置预算。");
        } else {
            sb.append("，本月结余");
            sb.append(df.format(budget.subtract(out)));
        }
        return sb.toString();
    }

    private Recipe prepareRecipe(String context) {
        Recipe recipe = new Recipe();
        String journalStr = context.substring(1);
        // TODO validate user input.
        String[] args = journalStr.split(" ", 5);
        if (!StringUtils.isBlank(args[0])) {
            Validater.validateMoney(args[0]);
            recipe.setType(args[0].charAt(0) == '-' ? String.valueOf(Recipe.TYPE.out) : String.valueOf(Recipe.TYPE.in));
            String money = args[0].substring(1);
            recipe.setSum(Double.parseDouble(money));
        }else{
            throw new GeneralException(ErrCode.NOT_NULL,"金额");
        }

        if (!StringUtils.isBlank(args[1])) {
            String[] levels = args[1].split("|", 2);
            recipe.setLevel1(levels[0]);
            recipe.setLevel2(levels[1]);
        } else {
            recipe.setLevel1("食物");
        }

        if (!StringUtils.isBlank(args[2])) {
            recipe.setAccount(args[2]);
        } else {
            recipe.setAccount("现金");
        }

        if (!StringUtils.isBlank(args[3])) {
            Validater.validateDate(args[3]);
            LocalDate date = LocalDate.parse(args[3], DateTimeFormatter.BASIC_ISO_DATE);
            recipe.setYear(date.getYear());
            recipe.setMonth(date.getMonthValue());
            recipe.setDay(date.getDayOfMonth());
            recipe.setDate(date.toEpochDay());
        } else {
            LocalDate today = LocalDate.now();
            recipe.setYear(today.getYear());
            recipe.setMonth(today.getMonthValue());
            recipe.setDay(today.getDayOfMonth());
            recipe.setDate(LocalDate.now().toEpochDay());
        }
        return recipe;
    }

}
