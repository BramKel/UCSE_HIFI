package HighFid.Model;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class Enrolment {
    public Date day;
    public DayOfWeek dayOfWeek;
    public Time beginTime;
    public Time endTime;
    public String place;
    public Sport sport;

    public Enrolment(DayOfWeek dayOfWeek, Time beginTime, Time endTime, String place, Sport sport) {
        LocalDate next = LocalDate.now().with(TemporalAdjusters.next(dayOfWeek));
        day = Date.from(next.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        this.dayOfWeek = dayOfWeek;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.place = place;
        this.sport = sport;
    }
}
