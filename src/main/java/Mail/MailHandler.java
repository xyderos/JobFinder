package Mail;

import Integration.DBConnection;
import Integration.Metadata;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MailHandler {

    private static Timer timer = new Timer();

    private static final int PERIOD = 1000 * 60 * 60 * 24;

    private static final long DELAY = 0L;

    private static List<String> consultants = new ArrayList<>();

    private String from;

    private String pass;

    private ResultSet res;

    private DBConnection dbc;

    public MailHandler(String from, String pass, String dbname, String dbpass, String eWorkname, String eWorkPass) {
        this.from = from;
        this.pass = pass;

        TimerTask t = new TimerTask() {
            @Override
            public void run() {

                try {
                    dbc = new DBConnection(dbname, dbpass, eWorkname, eWorkPass);
                    res = dbc.getRes();
                    sendJobsToEveryConsultant();
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        };

        timer.schedule(t, DELAY, PERIOD);
    }

    private void sendJobsToEveryConsultant() throws Exception {
        for (String e : consultants)
            for (Metadata m: Metadata.setToList(res))
                new MailCreator(this.from, this.pass, e, m.getFileName(), m.getFilePath());

    }
}
