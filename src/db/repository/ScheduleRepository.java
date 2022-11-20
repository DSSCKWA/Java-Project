package src.db.repository;

import src.db.client.DBClient;
import src.db.tables.ScheduleTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Locale;

public class ScheduleRepository extends Repository {
    public ScheduleRepository(DBClient client) {
        super(client);
    }

    public ArrayList<ScheduleTable> getAllSchedules() {
        String query = "SELECT * FROM schedule";
        ArrayList<ScheduleTable> schedules = new ArrayList<>();
        try (Statement stmt = client.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                schedules.add(new ScheduleTable(
                        rs.getInt("doctor_id"),
                        rs.getInt("clinic_id"),
                        DayOfWeek.valueOf(rs.getString("day").toUpperCase(Locale.ROOT)),
                        rs.getTime("start_hour").toLocalTime(),
                        rs.getTime("end_hour").toLocalTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedules;
    }

    public ArrayList<ScheduleTable> getSchedulesByClinicId(int clinicId) {
        String query = "SELECT * FROM schedule WHERE clinic_id = ?";
        ArrayList<ScheduleTable> schedules = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, clinicId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                schedules.add(new ScheduleTable(
                        rs.getInt("doctor_id"),
                        rs.getInt("clinic_id"),
                        DayOfWeek.valueOf(rs.getString("day").toUpperCase(Locale.ROOT)),
                        rs.getTime("start_hour").toLocalTime(),
                        rs.getTime("end_hour").toLocalTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedules;
    }

    public ArrayList<ScheduleTable> getSchedulesByDoctorId(int doctorId) {
        String query = "SELECT * FROM schedule WHERE doctor_id = ?";
        ArrayList<ScheduleTable> schedules = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                schedules.add(new ScheduleTable(
                        rs.getInt("doctor_id"),
                        rs.getInt("clinic_id"),
                        DayOfWeek.valueOf(rs.getString("day").toUpperCase(Locale.ROOT)),
                        rs.getTime("start_hour").toLocalTime(),
                        rs.getTime("end_hour").toLocalTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedules;
    }

    public void insertSchedule(ScheduleTable schedule) {
        String query = "INSERT INTO schedule(doctor_id,clinic_id,day,start_hour,end_hour) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1,schedule.getDoctorId());
            stmt.setInt(2,schedule.getClinicId());
            stmt.setString(3,schedule.getDay().toString().toLowerCase(Locale.ROOT));
            stmt.setTime(4, java.sql.Time.valueOf(schedule.getStartHour()));
            stmt.setTime(5, java.sql.Time.valueOf(schedule.getEndHour()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSchedule(ScheduleTable schedule) {
        String query = "UPDATE schedule SET start_hour = ?, end_hour = ? WHERE clinic_id = ? and doctor_id = ? and day = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setTime(1, java.sql.Time.valueOf(schedule.getStartHour()));
            stmt.setTime(2, java.sql.Time.valueOf(schedule.getEndHour()));
            stmt.setInt(3, schedule.getClinicId());
            stmt.setInt(4, schedule.getDoctorId());
            stmt.setString(5, schedule.getDay().toString().toLowerCase(Locale.ROOT));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteSchedule(int doctorId,int clinicId, DayOfWeek day) {
        String query = "DELETE FROM schedule where doctor_id = ? and clinic_id = ? and day = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.setInt(2, clinicId);
            stmt.setString(3, day.toString().toLowerCase(Locale.ROOT));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
