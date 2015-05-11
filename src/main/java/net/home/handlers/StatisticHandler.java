package net.home.handlers;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import net.home.exception.ErrCode;
import net.home.exception.GeneralException;
import net.home.mongo.MongoFacade;
import net.home.pojo.Budget;
import net.home.pojo.IncomingMessage;
import net.home.pojo.Recipe;

public class StatisticHandler implements Handler{
    private MongoFacade mongoFacade = MongoFacade.getInstance();
    private DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");

    public String handleMsg(IncomingMessage msg) throws GeneralException{
        String content = msg.getContent().substring(1);
        if (StringUtils.isBlank(content.substring(1))){
            return getHelp();
        }
        String userId = msg.getFromUserName();
        String result = "";
        String[] args = content.split(" ");
        switch (args[0]) {
        case "收入":
            if (args.length==2){
                result = calcMoneyByType(userId, args[1], Recipe.TYPE.in);
            }else{
                result = calcMoneyByType(userId, "", Recipe.TYPE.in);
            }
            break;
        case "支出":
            if (args.length==2){
                result = calcMoneyByType(userId, args[1], Recipe.TYPE.out);
            }else{
                result = calcMoneyByType(userId, "", Recipe.TYPE.out);
            }
            break;
        case "月预算":
            if (args.length ==2){
                result = setBudget(userId, Double.parseDouble(args[1]));
            }else{
                result = getBudget(userId);
            }
            break;
        case "本月":
            if (args.length ==2){
                result = calcMoneyByLevel(userId, args[1]);
            }else{
                throw new GeneralException(ErrCode.COMMAND_NOT_SUPPORT);
            }
            break;
        default:
            throw new GeneralException(ErrCode.COMMAND_NOT_SUPPORT);
        }
        return result;
    }

    private String setBudget(String userId, double num) {
        Budget budget = new Budget();
        budget.setUserId(userId);
        budget.setBudget(num);
        mongoFacade.setBudget(budget);
        return "预算设置成功！";
    }
    
    private String getBudget(String userId) {
        
        BigDecimal budget = mongoFacade.getBudgetByUser(userId);
        if (budget!=null){
            return moneyFormat.format(budget);
        }else{
            return "未设置预算。";
        }
        
    }


    private String calcMoneyByType(String userId, String date, Recipe.TYPE type) {
        int year = 0;
        int month = 0;
        int day = 0;
        if (date.length() == 8) {
            year = Integer.parseInt(date.substring(0, 4));
            month = Integer.parseInt(date.substring(4, 6));
            day = Integer.parseInt(date.substring(6));
        } else if (date.length() == 6) {
            year = Integer.parseInt(date.substring(0, 4));
            month = Integer.parseInt(date.substring(4, 6));
        } else if (date.length() == 4) {
            year = Integer.parseInt(date.substring(0, 4));
        }else {
            LocalDate today = LocalDate.now();
            year = today.getYear();
            month = today.getMonthValue();
        }
        BigDecimal sum = mongoFacade.calculateSumByType(userId, year, month, day, type);
        String sumStr = moneyFormat.format(sum);
        switch (type) {
        case in:
            return "收入：" + sumStr;
        case out:
            return "支出：" + sumStr;
        }
        return "计算错误。";
    }

    private String calcMoneyByLevel(String userId, String level) {
        String[] levels = level.split("|", 2);
        LocalDate today = LocalDate.now();
        BigDecimal sum = null;
        if (levels.length==2){
            sum= mongoFacade.calculateSumByLevel(userId,today.getYear(),today.getMonthValue(),0, levels[0], levels[1]);
        }else{
            sum = mongoFacade.calculateSumByLevel(userId,today.getYear(),today.getMonthValue(),0, levels[0], "");
        }
        
        return moneyFormat.format(sum);
    }
    
    public String getHelp(){
        StringBuffer result = new StringBuffer();
        try {
            result.append(IOUtils.toString(getClass().getResourceAsStream("statisticHelp.txt"), "utf-8"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result.toString();
    }
}
