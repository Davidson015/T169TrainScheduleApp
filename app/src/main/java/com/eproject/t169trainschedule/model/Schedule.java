package com.eproject.t169trainschedule.model;

public class Schedule {

    private int stationId;
    private String stationName;
    private String stationImage;
    private String depTime;
    private String arrTime;
    private String runningTime;
    private String timeFromLast;
    private String totalDist;
    private String distFromLast;
    private String hardSeat;
    private String hardSleeper;
    private String softSleeper;


    public Schedule(int stationId, String stationName, String stationImage, String depTime, String arrTime, String runningTime, String timeFromLast, String totalDist, String distFromLast, String hardSeat, String hardSleeper, String softSleeper) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.stationImage = stationImage;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.runningTime = runningTime;
        this.timeFromLast = timeFromLast;
        this.totalDist = totalDist;
        this.distFromLast = distFromLast;
        this.hardSeat = hardSeat;
        this.hardSleeper = hardSleeper;
        this.softSleeper = softSleeper;
    }

    public int getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public String getStationImage() {
        return stationImage;
    }

    public String getDepTime() {
        return depTime;
    }

    public String getArrTime() {
        return arrTime;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public String getTimeFromLast() {
        return timeFromLast;
    }

    public String getTotalDist() {
        return totalDist;
    }

    public String getDistFromLast() {
        return distFromLast;
    }

    public String getHardSeat() {
        return hardSeat;
    }

    public String getHardSleeper() {
        return hardSleeper;
    }

    public String getSoftSleeper() {
        return softSleeper;
    }
}
