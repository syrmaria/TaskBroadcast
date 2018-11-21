package com.syrova_ma.taskbroadcast;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Random;

/**
 * Created by SBT-Syrova-MA on 20.11.2018.
 */

public class StateService extends JobIntentService {
    static final int JOB_ID = 1000;
    public static final String TAG = "MyStateService";
    public static final String STATE = "STATE";
    private static final Random RANDOM = new Random();
    private StateManager.State[] stateValuesArrayList = StateManager.State.values();

    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, StateService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        int nextStateNumber = RANDOM.nextInt(stateValuesArrayList.length);
        StateManager.State newState = stateValuesArrayList[nextStateNumber];
        StateManager.INSTANCE.setCurrentState(newState);
        Log.d(TAG, "StateManager called");

        Intent newStateIntent = new Intent(MainActivity.STATE_FILTER);
        newStateIntent.putExtra(STATE, newState.toString());
        LocalBroadcastManager.getInstance(this).sendBroadcast(newStateIntent);
        Log.d(TAG, "Broadcast sent");
    }

    public static Intent newIntent(Context c) {
        return new Intent(c, StateService.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
    }
}
