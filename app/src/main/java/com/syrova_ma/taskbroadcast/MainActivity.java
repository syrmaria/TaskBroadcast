package com.syrova_ma.taskbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MyMainActivity";
    public static final String STATE_FILTER = "com.syrova_ma.taskbroadcast.state";
    Button changeStateButton;
    StateReceiver stateReceiver;
    TextView stateTextView;
    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateTextView = findViewById(R.id.state_text_view);
        if (savedInstanceState != null) {
            state = savedInstanceState.getString(StateService.STATE);
            updateUI();
        }
        changeStateButton = findViewById(R.id.change_state_button);
        changeStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStartService();
            }
        });
        stateReceiver = new StateReceiver(new Handler());
        IntentFilter intentFilter = new IntentFilter(STATE_FILTER);
        LocalBroadcastManager.getInstance(this).registerReceiver(stateReceiver, intentFilter);
    }

    private void doStartService() {
        StateService.enqueueWork(this, StateService.newIntent(this));
        Log.d(TAG, "Started service");
    }

    private void updateUI() {
        stateTextView.setText(String.format(getString(R.string.state_changed), state));
        Log.d(TAG, "UI updated");
    }

    @Override
    protected void onDestroy() {
        stopService(StateService.newIntent(this));
        LocalBroadcastManager.getInstance(this).unregisterReceiver(stateReceiver);
        Log.d(TAG, "OnDestroy - stopped service and receiver");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(StateService.STATE, state);
    }

    public class StateReceiver extends BroadcastReceiver {
        private final Handler handler;

        public StateReceiver(Handler handler) {
            this.handler = handler;
            Log.d(TAG, "Receiver: created");
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            state = intent.getStringExtra(StateService.STATE);
            Log.d(TAG, "Receiver: got state, post to main activity");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    updateUI();
                }
            });
        }
    }

}
