public class AttendanceRule implements EligibilityRule{
    private final int minAttendance;

    public AttendanceRule(int minAttendance){
        this.minAttendance = minAttendance;
    }

    @Override
    public String check(StudentProfile student){
        if(student.attendancePct < minAttendance){
            return "attendance below " + minAttendance;
        }

        return null;
    }
}
