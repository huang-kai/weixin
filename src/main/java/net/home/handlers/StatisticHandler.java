package net.home.handlers;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import net.home.exception.ErrCode;
import net.home.exception.GeneralException;
import net.home.mongo.MongoFacade;
import net.home.pojo.Budget;
import net.home.pojo.IncomingMessage;
import net.home.pojo.Recipe;

public class StatisticHandler implements Handler{
    private MongoFacade mongoFacade = MongoFacade.getInstance();
    private DecimalFormat moneyFormat = new DecimalFormat("#,###.00");

    public String handleMsg(IncomingMessage msg) throws GeneralException{
        String content = msg.getContent().substring(1);
        String userId = msg.getFromUserName();
        String result = "";
        String[] args = content.split(" ");
        switch (args[0]) {
        case "收入":
            result = calcMoneyByType(userId, args[1], Recipe.TYPE.in);
            break;
        case "支出":
            result = calcMoneyByType(userId, args[1], Recipe.TYPE.out);
            break;
        case "月预算":
            result = setBudget(userId, Double.parseDouble(args[1]));
            break;
        case "本月":
            result = calcMoneyByLevel(userId, args[1]);
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
        }
        BigDecimal sum = mongoFacade.calculateSumByDate(userId, year, month, day, type);
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
        BigDecimal sum = mongoFacade.calculateSumByLevel(userId, levels[0], levels[1]);
        return moneyFormat.format(sum);
    }
}
