package com.jukusoft.libgdx.rpg.engine.time;

import com.jukusoft.libgdx.rpg.engine.time.listener.CooldownTimerFinishedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 07.03.2017.
 */
public class CooldownTimer extends GameTimer {

    protected long interval = 0;
    protected long endTime = 0;

    //list with listeners
    protected List<CooldownTimerFinishedListener> timerFinishedListenerList = new ArrayList<>();

    public CooldownTimer (long interval) {
        this.interval = interval;
    }

    @Override
    protected void afterStart (GameTime time) {
        //calculate end time
        endTime = startTime + interval;
    }

    @Override
    protected void afterStop (GameTime time) {
        //
    }

    @Override
    protected void afterUpdate (GameTime time) {
        if (time.getTime() > this.endTime) {
            //stop timer
            this.stop(time);

            //call listeners
            onEndTimeReached(time);
        }
    }

    public long getEndTime () {
        return this.startTime + interval;
    }

    public void setInterval (long interval) {
        this.interval = interval;
    }

    protected void onEndTimeReached (GameTime time) {
        this.timerFinishedListenerList.stream().forEach(listener -> {
            //call listener
            listener.onFinished(this.startTime, this.interval, time.getTime());
        });
    }

    public void setFinishedListener (CooldownTimerFinishedListener listener) {
        //clear list
        this.timerFinishedListenerList.clear();

        if (listener != null) {
            this.timerFinishedListenerList.add(listener);
        }
    }

    public void removeAllFinishedListeners () {
        //clear listener list
        this.timerFinishedListenerList.clear();
    }

}
