package src.db.repository;

import src.db.client.DBClient;
import src.db.entities.ScheduleEntity;
import src.schedule.Schedule;

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

    public Schedule toSchedule(ScheduleEntity scheduleEntity) {
        return new Schedule(
                scheduleEntity.getDoctorId(),
                scheduleEntity.getClinicId(),
                scheduleEntity.getDay(),
                scheduleEntity.getStartHour(),
                scheduleEntity.getEndHour()
        );
    }

    public ArrayList<Schedule> toScheduleList(ArrayList<ScheduleEntity> scheduleEntities) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        for (ScheduleEntity schedule : scheduleEntities) {
            schedules.add(toSchedule(schedule));
        }
        return schedules;
    }

    public ArrayList<ScheduleEntity> getAllSchedules() {
        String query = "SELECT * FROM schedule";
        ArrayList<ScheduleEntity> schedules = new ArrayList<>();
        try (Statement stmt = client.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                schedules.add(new ScheduleEntity(
                        rs.getInt("doctor_id"),
                        rs.getInt("clinic_id"),
                        DayOfWeek.valueOf(rs.getString("day").toUpperCase(Locale.ROOT)),
                        rs.getTime("start_hour").toLocalTime(),
                        rs.getTime("end_hour").toLocalTime()
                ));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedules;
    }

    public ArrayList<ScheduleEntity> getSchedules(int doctorId, int clinicId) {
        String query = "SELECT * FROM schedule WHERE doctor_id = ? and clinic_id = ?";
        ArrayList<ScheduleEntity> schedules = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.setInt(2, clinicId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                schedules.add(new ScheduleEntity(
                        rs.getInt("doctor_id"),
                        rs.getInt("clinic_id"),
                        DayOfWeek.valueOf(rs.getString("day").toUpperCase(Locale.ROOT)),
                        rs.getTime("start_hour").toLocalTime(),
                        rs.getTime("end_hour").toLocalTime()
                ));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedules;
    }

    public ArrayList<ScheduleEntity> getSchedules(int doctorId) {
        String query = "SELECT * FROM schedule WHERE doctor_id = ?";
        ArrayList<ScheduleEntity> schedules = new ArrayList<>();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                schedules.add(new ScheduleEntity(
                        rs.getInt("doctor_id"),
                        rs.getInt("clinic_id"),
                        DayOfWeek.valueOf(rs.getString("day").toUpperCase(Locale.ROOT)),
                        rs.getTime("start_hour").toLocalTime(),
                        rs.getTime("end_hour").toLocalTime()
                ));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedules;
    }

    public ScheduleEntity getSchedule(int doctorId, int clinicId, DayOfWeek day) {
        String query = "SELECT * FROM schedule WHERE doctor_id = ? and clinic_id = ? and day = ?";
        ScheduleEntity schedule = new ScheduleEntity();
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.setInt(2, clinicId);
            stmt.setString(3, day.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                schedule.setDoctorId(rs.getInt("doctor_id"));
                schedule.setClinicId(rs.getInt("clinic_id"));
                schedule.setDay(DayOfWeek.valueOf(rs.getString("day").toUpperCase(Locale.ROOT)));
                schedule.setStartHour(rs.getTime("start_hour").toLocalTime());
                schedule.setEndHour(rs.getTime("end_hour").toLocalTime());
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedule;
    }

    public void insertSchedule(ScheduleEntity schedule) {
        String query = "INSERT INTO schedule(doctor_id,clinic_id,day,start_hour,end_hour) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, schedule.getDoctorId());
            stmt.setInt(2, schedule.getClinicId());
            stmt.setString(3, schedule.getDay().toString().toLowerCase(Locale.ROOT));
            stmt.setTime(4, java.sql.Time.valueOf(schedule.getStartHour()));
            stmt.setTime(5, java.sql.Time.valueOf(schedule.getEndHour()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSchedule(ScheduleEntity schedule) {
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

    public void deleteSchedule(int doctorId, int clinicId, DayOfWeek day) {
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

    public void deleteSchedule(int doctorId) {
        String query = "DELETE FROM schedule where doctor_id = ?";
        try (PreparedStatement stmt = client.getConnection().prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
