package net.home.handlers;

import java.math.BigDecimal;

import net.home.mongo.MongoFacade;
import net.home.pojo.IncomingMessage;
import net.home.pojo.Recipe;

public class StatisticHandler {
    private MongoFacade mongoFacade = MongoFacade.getInstance();

    public String handleInComingMsg(IncomingMessage msg) {
        String content = msg.getContent().substring(1);
        String userId = msg.getFromUserName();
        String result = "";
        String[] args = content.split(" ");
        int year = 0;
        int month = 0;
        int day = 0;
        switch (args[0]) {
        case "收入":
            
            if (args[1].length() == 8) {
                year = Integer.parseInt(args[1].substring(0, 4));
                month = Integer.parseInt(args[1].substring(4, 6));
                day = Integer.parseInt(args[1].substring(6));
            }else if (args[1].length() == 6) {
                year = Integer.parseInt(args[1].substring(0, 4));
                month = Integer.parseInt(args[1].substring(4, 6));
            }else if (args[1].length() == 4) {
                    year = Integer.parseInt(args[1].substring(0, 4));
                    
            }
            BigDecimal sum = mongoFacade.calculateSum(userId, year, month, day, Recipe.TYPE.in);
            break;
        case "支出":
            if (args[1].length() == 8) {
                year = Integer.parseInt(args[1].substring(0, 4));
                month = Integer.parseInt(args[1].substring(4, 6));
                day = Integer.parseInt(args[1].substring(6));
            }else if (args[1].length() == 6) {
                year = Integer.parseInt(args[1].substring(0, 4));
                month = Integer.parseInt(args[1].substring(4, 6));
            }else if (args[1].length() == 4) {
                    year = Integer.parseInt(args[1].substring(0, 4));
                    
            }
            sum = mongoFacade.calculateSum(userId, year, month, day, Recipe.TYPE.out);
            break;
        case "月预算":

            break;
        case "本月":

            break;
        default:
            break;
        }
        return result;
    }
}
