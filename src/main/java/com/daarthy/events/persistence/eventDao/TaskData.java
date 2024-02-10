package com.daarthy.events.persistence.eventDao;

import com.daarthy.events.persistence.missionDao.ActionType;

import java.time.LocalDateTime;

public class TaskData {

    private Long taskId;
    private LocalDateTime initDate;
    private LocalDateTime endDate;
    private String target;
    private ActionType actionType;
    private Integer level;
    private Integer amount;
    private Integer reqAmount;
    private Integer medals;

    public TaskData(Long taskId, LocalDateTime initDate, LocalDateTime endDate, String target,
                    ActionType actionType, Integer level, Integer amount, Integer reqAmount, Integer medals) {
        this.taskId = taskId;
        this.initDate = initDate;
        this.endDate = endDate;
        this.target = target;
        this.actionType = actionType;
        this.level = level;
        this.amount = amount;
        this.reqAmount = reqAmount;
        this.medals = medals;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDateTime initDate) {
        this.initDate = initDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getReqAmount() {
        return reqAmount;
    }

    public void setReqAmount(Integer reqAmount) {
        this.reqAmount = reqAmount;
    }

    public Integer getMedals() {
        return medals;
    }

    public void setMedals(Integer medals) {
        this.medals = medals;
    }
}
