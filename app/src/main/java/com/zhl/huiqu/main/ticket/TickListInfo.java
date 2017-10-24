package com.zhl.huiqu.main.ticket;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class TickListInfo implements Serializable {
    private List<TickTheme> theme;
    private List<TickGraeld> grade;

    public List<TickTheme> getTheme() {
        return theme;
    }

    public void setTheme(List<TickTheme> theme) {
        this.theme = theme;
    }

    public List<TickGraeld> getGrade() {
        return grade;
    }

    public void setGrade(List<TickGraeld> grade) {
        this.grade = grade;
    }
}
