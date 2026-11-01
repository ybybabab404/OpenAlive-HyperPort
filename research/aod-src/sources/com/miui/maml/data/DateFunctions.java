package com.miui.maml.data;

import androidx.annotation.RequiresApi;
import com.miui.maml.util.Utils;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;

/* JADX INFO: loaded from: classes2.dex */
@RequiresApi
public class DateFunctions extends Expression.FunctionImpl {
    public static final int REPEAT_MONTH = 2;
    public static final int REPEAT_NO = 0;
    public static final int REPEAT_WEEK = 3;
    public static final int REPEAT_YEAR = 1;

    public DateFunctions(int i) {
        super(i);
    }

    private LocalDate changeYear(LocalDate localDate, int i) {
        return LocalDate.of(i, localDate.getMonth(), !IsoChronology.INSTANCE.isLeapYear((long) i) && localDate.getMonthValue() == 2 && localDate.getDayOfMonth() == 29 ? localDate.getDayOfMonth() - 1 : localDate.getDayOfMonth());
    }

    private long diffDateMonth(LocalDate localDate, LocalDate localDate2) {
        int dayOfMonth = localDate.getDayOfMonth();
        int iLengthOfMonth = localDate2.lengthOfMonth();
        int dayOfMonth2 = localDate2.getDayOfMonth();
        int i = iLengthOfMonth - dayOfMonth2;
        int iLengthOfMonth2 = localDate2.plusMonths(1L).lengthOfMonth();
        if (dayOfMonth >= dayOfMonth2) {
            return dayOfMonth > iLengthOfMonth ? i : dayOfMonth - dayOfMonth2;
        }
        return dayOfMonth > iLengthOfMonth2 ? i + iLengthOfMonth2 : i + dayOfMonth;
    }

    private long diffDateYear(LocalDate localDate, LocalDate localDate2) {
        if (localDate.getMonthValue() <= localDate2.getMonthValue() && (localDate.getMonthValue() != localDate2.getMonthValue() || localDate.getDayOfMonth() < localDate2.getDayOfMonth())) {
            return ChronoUnit.DAYS.between(localDate2, changeYear(localDate, localDate2.getYear() + 1));
        }
        LocalDate localDateChangeYear = changeYear(localDate, localDate2.getYear());
        return (!localDate2.isLeapYear() && localDateChangeYear.getMonth() == Month.FEBRUARY && localDateChangeYear.getDayOfMonth() == 29) ? (localDateChangeYear.getDayOfYear() - 1) - localDate2.getDayOfYear() : localDateChangeYear.getDayOfYear() - localDate2.getDayOfYear();
    }

    public static void load() {
        Expression.FunctionExpression.registerFunction("diffDate", new DateFunctions(3));
    }

    @RequiresApi
    public long diffDate(long j, long j2, int i) {
        return diffDate(long2LocalDate(j), long2LocalDate(j2), i);
    }

    @RequiresApi
    public long diffDate(LocalDate localDate, LocalDate localDate2, int i) {
        if (1 == i) {
            return diffDateYear(localDate, localDate2);
        }
        if (2 == i) {
            return diffDateMonth(localDate, localDate2);
        }
        if (3 != i) {
            return localDate.toEpochDay() - localDate2.toEpochDay();
        }
        int value = localDate.getDayOfWeek().getValue();
        int value2 = localDate2.getDayOfWeek().getValue();
        return value >= value2 ? value - value2 : (value + 7) - value2;
    }

    @Override // com.miui.maml.data.Expression.FunctionImpl
    public double evaluate(Expression[] expressionArr, Variables variables) {
        return diffDate((long) expressionArr[0].evaluate(), (long) expressionArr[1].evaluate(), (int) expressionArr[2].evaluate());
    }

    @Override // com.miui.maml.data.Expression.FunctionImpl
    public String evaluateStr(Expression[] expressionArr, Variables variables) {
        return Utils.doubleToString(evaluate(expressionArr, variables));
    }

    @RequiresApi
    public long localDate2Long(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @RequiresApi
    public LocalDate long2LocalDate(long j) {
        return Instant.ofEpochMilli(j).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
