package com.pleng.healthy.healthy.AlarmClock;

public class SleepStore {
    private String wakeUpTime;
    private String sleepTime;
    private String differenceTime;
    private  String date;

    public SleepStore(String date, String sleepTime, String wakeUpTime, String differenceTime) {
        this.wakeUpTime = wakeUpTime;
        this.sleepTime = sleepTime;
        this.differenceTime = differenceTime;
        this.date = date;
    }

    public String getWakeUpTime() {
        return wakeUpTime;
    }

    public void setWakeUpTime(String wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getDifferenceTime() {
        return differenceTime;
    }

    public void setDifferenceTime(String differenceTime) {
        this.differenceTime = differenceTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
