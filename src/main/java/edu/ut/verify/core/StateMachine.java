package edu.ut.verify.core;

import edu.ut.verify.core.exception.NoInitialStateException;
import edu.ut.verify.core.state.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jerry Wang on 2018/11/5.
 */
public class StateMachine {

    private StateChart stateChart;

    private List<Sequence> sequences;

    private static final int circleCount = 3;

    public StateMachine(StateChart stateChart){
        this.stateChart = stateChart;

        this.sequences = new ArrayList<Sequence>();

        currentState = stateChart.getStartState();
    }

    private State currentState;


    public StateChart getStateChart() {
        return stateChart;
    }

    /**
     * start process of the state chart sequence
     */
    public void process() throws NoInitialStateException{

        Sequence sequence = new Sequence();
        
        if(currentState == null)
            throw new NoInitialStateException("No Start State Found!");
        if(stateChart.getEndState() == null)
            throw new NoInitialStateException("No End State Found!");

        HashMap<State, Integer> countMap = new HashMap<State, Integer>();
        dfs(sequence, countMap);
    }

    /**
     * dfs + backtrack
     * @param sequence
     * @param countMap
     */
    private void dfs(Sequence sequence, HashMap<State, Integer> countMap){
        if(currentState.equals(stateChart.getEndState())){
            sequences.add(Sequence.clone(sequence));
            return;
        }

        List<Transition> transitionList = stateChart.getStateTransitionMap().get(currentState);

        for( Transition transition : transitionList ){
            sequence.addTransition(transition);
            State nextState = transition.toState;

            if(countMap.getOrDefault(nextState,0) == circleCount)
                continue;

            countMap.put(nextState, countMap.getOrDefault(nextState, 0 ) + 1);
            //dfs
            this.currentState = nextState;
            dfs(sequence, countMap);

            countMap.put(nextState, countMap.get(nextState) - 1);

            this.currentState = transition.fromState;
            sequence.removeTransition(transition);
        }
    }

    public void printSequence(){
        for(Sequence s: sequences){
            s.printSequence();
        }
    }


}
