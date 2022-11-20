package dio.quebec.parking.sevice;

import ch.qos.logback.core.net.SyslogOutputStream;
import dio.quebec.parking.model.Parking;

import java.time.Duration;
import java.time.LocalDateTime;

public class ParkingCheckOut {

    public static final int ONE_HOUR = 60 * 60;
    public static final int TWENTY_FOUR_HOURS = 24 * ONE_HOUR;
    public static final double ONE_HOUR_VALUE = 5.00;
    public static final double ADDITIONAL_PER_HOUR_VALUE = 2.00;
    public static final double DAY_VALUE = 20.00;

    public static Double getBill(Parking parking) {
        return getBill(parking.getEntryDate(), parking.getExitDate());
    }

    private static Double getBill(LocalDateTime entryDate, LocalDateTime exitDate) {

        Duration lenghtOfStay = Duration.between(entryDate, exitDate);

        Long seconds = lenghtOfStay.toSeconds();
        Double bill = 0.0;
        if (seconds <= ONE_HOUR) {
            return ONE_HOUR_VALUE;
        }
        if (seconds <= TWENTY_FOUR_HOURS) {
            bill = ONE_HOUR_VALUE;
            int hours = (int) (seconds / ONE_HOUR);
            System.out.println(hours);
            for (int i = 0; i < hours; i++) {
                bill += ADDITIONAL_PER_HOUR_VALUE;
            }
            return bill;
        }
        int days = (int) (seconds / TWENTY_FOUR_HOURS);
        System.out.println(days);
        for (int i = 0; i < days; i++) {
            bill += DAY_VALUE;
        }
        return bill;
    }
}
