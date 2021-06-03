package ro.pub.cs.systems.eim.practicaltest02;

public class TimerInformation {
    private String hour;
    private String minute;

    public TimerInformation() {
        this.hour = null;
        this.minute = null;
    }

    public TimerInformation(String hour, String minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }
}
