package src.mail;

import src.config.Config;
import src.db.client.DBClient;
import src.db.repository.VisitRepository;
import src.visit.Visit;

import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PeriodicMail implements Runnable {
    private final Config config = Config.getConfig();
    private final Mail mail = new Mail(config.getEmail(), config.getSender(), config.getPassword());
    private final DBClient dbClient;
    private final int PERIOD;
    private final int TIME_BEFORE = 1440; // 1 day

    {
        try {
            dbClient = new DBClient(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PeriodicMail(int period) {
        PERIOD = period;
    }

    @Override
    public void run() {
        VisitRepository visitRepository = new VisitRepository(dbClient);
        ArrayList<Visit> visits = visitRepository.toVisitList(visitRepository.getAllUncompletedVisits());
        LocalDateTime now = LocalDateTime.now();
        for (Visit visit : visits) {
            LocalDateTime visitDateTime = LocalDateTime.of(visit.getDate(), visit.getTime());
            long timeLeft = Duration.between(now, visitDateTime).toMinutes();
            if (timeLeft > 0 && // before visit happens
                    timeLeft < TIME_BEFORE && // remind 1 day before
                    TIME_BEFORE - timeLeft <= PERIOD // remind just once
            ) {
                mail.visitReminder(visit);
            }
        }
    }
}
