package Game.UI;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClockPanel extends JPanel{

    private JLabel timeLabel;
    private ClockThread clockThread;

    // Longs
    private long initTime;

    ClockPanel(long initTime){
        setLayout(new BorderLayout());
        this.initTime = initTime;

        JLabel gameTimeLabel = new JLabel("Elapsed Time");
        timeLabel = new JLabel();

        gameTimeLabel.setHorizontalAlignment(JLabel.CENTER);
        gameTimeLabel.setBorder(new EmptyBorder(0,0,5,0));
        timeLabel.setHorizontalAlignment(JLabel.CENTER);

        add(gameTimeLabel,BorderLayout.NORTH);
        add(timeLabel,BorderLayout.CENTER);
        setBorder(new EmptyBorder(10,10,10,10));

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        clockThread = new ClockThread(initTime);
        exec.scheduleAtFixedRate(clockThread, 0, 1, TimeUnit.SECONDS);

    }

    /**
     *  Used for fetching the total passed time in the game
     * @return total game time
     */
    public long getGameTime(){
        return clockThread.getPassedTime();
    }


    /**
     *  Thread increments the clock each second. Formatted HH:MM:SS
     */
    class ClockThread extends Thread implements Runnable {

        private final int SECONDS_IN_HOUR = 3600;
        private final int SECONDS_IN_MIN = 60;

        private long passedTime;
        ClockThread(long passedTime){
            this.passedTime = initTime;
        }

        public void run() {

            passedTime += 1;

            long totalSeconds = passedTime;

            long hours = totalSeconds / SECONDS_IN_HOUR;

            long remainingTime = totalSeconds - (hours * SECONDS_IN_HOUR);

            long minutes = TimeUnit.SECONDS.toMinutes(remainingTime);

            long seconds = remainingTime - (minutes * SECONDS_IN_MIN);


            String timeStamp = ((hours < 10) ? ("0"+hours):(hours))+":"+
                    ((minutes < 10) ? ("0"+minutes) : (minutes))+":"+
                    ((seconds < 10) ? ("0"+seconds) : (seconds));

            timeLabel.setText(timeStamp);
        }

        private long getPassedTime(){
            return passedTime;
        }
    }
}
