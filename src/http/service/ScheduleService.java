package src.http.service;

import src.schedule.Schedule;
import src.db.client.DBClient;
import src.db.entities.ScheduleEntity;
import src.db.repository.ScheduleRepository;
import src.http.constants.HttpStatus;
import src.http.util.HttpException;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class ScheduleService {
    private static final Logger LOGGER = Logger.getLogger(ScheduleService.class.getName());
    private static final DBClient dbClient;

    static {
        dbClient = new DBClient(true);
    }

    private static final ScheduleRepository scheduleRepository = new ScheduleRepository(dbClient);

    public ArrayList<Schedule> getAllSchedules() {
        return scheduleRepository.toScheduleList(scheduleRepository.getAllSchedules());
    }

    public ArrayList<Schedule> getSchedules(int doctorId) {
        return scheduleRepository.toScheduleList(scheduleRepository.getSchedules(doctorId));
    }

    public ArrayList<Schedule> getSchedules(int doctorId, int clinicId) {
        return scheduleRepository.toScheduleList(scheduleRepository.getSchedules(doctorId, clinicId));
    }

    public Schedule getSchedule(int doctorId, int clinicId, DayOfWeek day) {
        ScheduleEntity schedule = scheduleRepository.getSchedule(doctorId, clinicId, day);
        if (schedule.equals(new ScheduleEntity())) {
            return null;
        }
        return scheduleRepository.toSchedule(schedule);
    }

    public Schedule addSchedule(Map<String, String> scheduleData) {
        ScheduleEntity schedule = toScheduleEntity(scheduleData);
        scheduleRepository.insertSchedule(schedule);
        return scheduleRepository.toSchedule(schedule);
    }

    public Schedule updateSchedule(Map<String, String> scheduleData) {
        //TODO validate data
        System.out.println(scheduleData);
        ScheduleEntity scheduleEntity = toScheduleEntity(scheduleData);
        scheduleRepository.updateSchedule(scheduleEntity);
        return scheduleRepository.toSchedule(scheduleEntity);
    }

    public void deleteSchedule(int doctorId) {
        scheduleRepository.deleteSchedule(doctorId);
    }

    public void deleteSchedule(int doctorId, int clinicId, DayOfWeek day) {
        scheduleRepository.deleteSchedule(doctorId, clinicId, day);
    }

    private ScheduleEntity toScheduleEntity(Map<String, String> scheduleData) {
        try {
            return new ScheduleEntity(
                    Integer.parseInt(scheduleData.get("doctorId")),
                    Integer.parseInt(scheduleData.get("clinicId")),
                    DayOfWeek.valueOf(scheduleData.get("day").toUpperCase(Locale.ROOT)),
                    LocalTime.parse(scheduleData.get("startTime")),
                    LocalTime.parse(scheduleData.get("endTime"))
            );
        } catch (NumberFormatException e) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Invalid payload");
        }
    }
}
