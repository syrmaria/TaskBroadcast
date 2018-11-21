package com.syrovama.taskbroadcast;

enum StateManager {
    INSTANCE;

    enum State {A, B, C, D, E}
    private State currentState;

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}
