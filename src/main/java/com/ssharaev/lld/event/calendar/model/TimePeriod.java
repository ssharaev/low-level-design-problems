package com.ssharaev.lld.event.calendar.model;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class TimePeriod implements Comparable<TimePeriod> {
    private final LocalTime startTime;
    private final LocalTime endTime;

    public boolean timeInPeriod(@NotNull LocalTime localTime) {
        return (startTime == null || localTime.isAfter( startTime ))
            && (endTime == null || localTime.isBefore(endTime));
    }

    public boolean periodInPeriod(@NotNull TimePeriod period) {
        return (this.startTime == null || period.startTime.isAfter( startTime ))
            && (this.endTime == null || period.endTime.isBefore(endTime));
    }

    public boolean isMoreOrEqual(@NotNull TimePeriod period) {
        return (this.startTime.equals( period.startTime ) || period.startTime.isAfter( startTime ))
            && (this.endTime.equals( period.endTime ) || period.endTime.isBefore(endTime));
    }

    @Override
    public int compareTo(@NotNull TimePeriod o) {
        return 0;
    }
}
