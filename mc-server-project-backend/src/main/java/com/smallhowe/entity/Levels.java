package com.smallhowe.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Levels {
    private static final int MAX_LEVEL = 10;

    private Integer level;
    private Long exp;
    public Levels(Integer level, Long exp) {
        this.level = level;
        this.exp = exp;
    }

    public static void setAccountLevelStatus(Account account) {
        List<Levels> list=initLevels();
        account.setLevelList(list);

        for (int i = list.size() - 1; i >= 0; i--) {
            //如果账户的经验大于当前等级的经验，则设置为当前等级
            if (account.getExp() >= list.get(i).getExp()) {
                account.setLevel(list.get(i).getLevel());
                if (account.getExp() >= list.get(i).getExp()) {
                    account.setLevel(list.get(i).getLevel());

                    if (account.getLevel().equals(MAX_LEVEL)){
                        account.setNextExp(0L);
                    }else{
                        account.setNextExp(list.get(i+1).getExp());
                    }
                    break;
                }
            }
        }

    }

    public static Levels getMaxLevel(){
        List<Levels> list = initLevels();
        return list.get(list.size() - 1);
    }

    private static List<Levels> initLevels(){
        long exp = 0;
        List<Levels> list = new ArrayList<>();
        for (int i=0;i<=MAX_LEVEL;i++){
            exp += i * 100L;
            list.add(new Levels(i,exp));
        }
        return list;
    }
}
