package src.mail;

import src.config.Config;
import src.db.client.DBClient;
import src.db.repository.VisitRepository;
import src.visit.Visit;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class PeriodicMail implements Callable<Void> {
    private final Config config = Config.getConfig();
    private final Mail mail = new Mail(config.getEmail(), config.getSender(), config.getPassword());
    private final DBClient dbClient;
    private final int PERIOD;
    private final int TIME_BEFORE = 1440; // 1 day

    {
        dbClient = new DBClient(true);
    }

    public PeriodicMail(int period) {
        PERIOD = period;
    }

    @Override
    public Void call() {
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
        return null;
    }
}
