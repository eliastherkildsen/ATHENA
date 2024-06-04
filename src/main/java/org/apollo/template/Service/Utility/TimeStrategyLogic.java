package org.apollo.template.Service.Utility;

public class TimeStrategyLogic {

    private static TimeStrategyLogic INSTANCE;

    private TimeStrategyLogic(){}

    public static TimeStrategyLogic getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TimeStrategyLogic();
        }
        return INSTANCE;
    }

}
