package com.daarthy.events.persistence.mission_dao;

public class ObjectiveData {

    private Long objectiveId;
    private Long missionId;
    private int reqAmount;
    private ActionType actionType;
    private String target;
    private Integer levels;

    // References to the amount completed by the player can be null if retrieved by guild.
    private Integer amount;

    public ObjectiveData() {}
    // For Guild
    public ObjectiveData(Long objectiveId, Long missionId, int reqAmount, String target, Integer levels,
                         ActionType actionType) {
        this.objectiveId = objectiveId;
        this.missionId = missionId;
        this.reqAmount = reqAmount;
        this.target = target;
        this.levels = levels;
        this.actionType = actionType;
    }

    public Long getObjectiveId() {
        return objectiveId;
    }

    public Long getMissionId() {
        return missionId;
    }

    public int getReqAmount() {
        return reqAmount;
    }

    public String getTarget() {
        return target;
    }

    public Integer getLevels() {
        return levels;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public void setReqAmount(int reqAmount) {
        this.reqAmount = reqAmount;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ActionType getActionType() {
        return actionType;
    }
}
