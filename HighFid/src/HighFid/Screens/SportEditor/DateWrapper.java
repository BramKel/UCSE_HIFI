package HighFid.Screens.SportEditor;

import java.sql.Time;
import java.time.DayOfWeek;

public class DateWrapper{
    public String day, begin, end, place;
    DateWrapper(DayOfWeek day, Time begin, Time end, String place) {
        this.day = DayToStr(day);
        this.begin = begin.toString().substring(0,5);
        this.end = end.toString().substring(0,5);
        this.place = place;
    }
    public String getDay() {
        return day;
    }
    public String getBegin() {
        return begin;
    }
    public String getEnd() {
        return end;
    }
    public String getPlace() {
        return place;
    }

    public void setDay(String day) {
        this.day = day;
    }
    public void setBegin(String begin) {
        this.begin = begin;
    }
    public void setEnd(String end) {
        this.end = end;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public Time getBeginTime() {
        begin += ":00";
        return Time.valueOf(begin);
    }
    public Time getEndTime() {
        end += ":00";
        return Time.valueOf(end);
    }
    public static Time StringToTime(String time) {
        try{
            time += ":00";
            Time t = Time.valueOf(time);
            return t;
        } catch (Exception e) {
            return null;
        }


    }
    public static DayOfWeek StringToDay(String day) {
        day = day.toLowerCase();
        switch(day) {
            case "maandag":
                return DayOfWeek.MONDAY;
            case "dinsdag":
                return DayOfWeek.TUESDAY;
            case "woensdag":
                return DayOfWeek.WEDNESDAY;
            case "donderdag":
                return DayOfWeek.THURSDAY;
            case "vrijdag":
                return DayOfWeek.FRIDAY;
            case "zaterdag":
                return DayOfWeek.SATURDAY;
            case "zondag":
                return DayOfWeek.SUNDAY;
        }
        return null;

    }
    private String DayToStr(DayOfWeek day) {
        String result = "";
        switch(day) {
            case MONDAY:
                result = "Maandag";
                break;
            case TUESDAY:
                result ="Dinsdag";
                break;
            case WEDNESDAY:
                result = "Woensdag";
                break;
            case THURSDAY:
                result = "Donderdag";
                break;
            case FRIDAY:
                result = "Vrijdag";
                break;
            case SATURDAY:
                result = "Zaterdag";
                break;
            case SUNDAY:
                result = "Zondag";
                break;
        }
        return result;
    }
}