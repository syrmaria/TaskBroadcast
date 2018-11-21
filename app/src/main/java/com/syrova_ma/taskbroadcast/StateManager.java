package com.syrova_ma.taskbroadcast;

import java.util.ArrayList;

/**
 * Created by SBT-Syrova-MA on 20.11.2018.
 */

enum StateManager {
    INSTANCE;

    enum State {A, B, C, D, E};
    private State currentState;

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}
