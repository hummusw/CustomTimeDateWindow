import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Controller {
    // JavaJX controls
    public Label label_date;
    public Label label_time;
    public Button button_settings;

    // Local constants
    private static final int SYNC_STEP = 50; // Unit of time in milliseconds to sync with the second
    private static final int SECOND_MILLIS = 1000;

    // Local variables
    private Timeline timeline;
    private int lastSecond;
    private DateFormat dateFormat;
    private DateFormat timeFormat;

    /**
     * Initializes local variables, starts synchronization on startup
     *
     * Thanks to https://stackoverflow.com/a/35306011/
     */
    public void initialize() {
        dateFormat = SimpleDateFormat.getDateInstance();
        timeFormat = SimpleDateFormat.getTimeInstance();

        startSync();
    }

    /**
     * Synchronizes updates to be accurate within {@code SYNC_STEP} milliseconds
     * After synchronization, updates are done every millisecond
     *
     * @see #scheduleUpdates()
     *
     * Thanks to https://stackoverflow.com/q/41246545/
     */
    private void startSync() {
        lastSecond = Calendar.getInstance().get(Calendar.SECOND);

        timeline = new Timeline(
                new KeyFrame(Duration.millis(SYNC_STEP),
                        actionEvent -> {
                            updateFields(); // Might be overkill, but keeps UI from freezing in worst case
                            int thisSecond = Calendar.getInstance().get(Calendar.SECOND);
                            if (lastSecond != thisSecond) {
                                timeline.stop();
                                scheduleUpdates();
                            }
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setDelay(Duration.millis(System.currentTimeMillis() % 1000));
        timeline.play();
    }

    /**
     * Schedules the fields to be updated every second after synchronization
     *
     * Thanks to https://stackoverflow.com/q/41246545/
     */
    private void scheduleUpdates() {
        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(SECOND_MILLIS),
                        actionEvent -> updateFields()
                        )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Updates the date and time fields graphically
     */
    private void updateFields() {
        Date now = new Date();
        label_date.setText(dateFormat.format(now));
        label_time.setText(timeFormat.format(now));
    }

    /**
     * Shows settings on button click
     *
     * @param actionEvent action event that triggered this call
     */
    public void action_settings(ActionEvent actionEvent) {
        //todo implement
        System.out.println("Button pressed!");
    }
}
