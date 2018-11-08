package edu.ut.verify.statechart2java;

/**
 * Created by Jerry Wang on 2018/11/5.
 */

import edu.ut.verify.core.Transition;
import edu.ut.verify.core.state.NState;
import edu.ut.verify.core.state.State;
import edu.ut.verify.core.StateChart;
import edu.ut.verify.core.event.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class XMIPaser {


    private static NodeList parseRegion(Document doc){

        return doc.getElementsByTagName("region");

    }

    private static Event parseEvent(Element element){

        Event event = new DoEvent();
        Element ownedMember = (Element) element.getElementsByTagName("ownedMember").item(0);
        if (ownedMember.getAttribute("name").length() != 0) {
            event.setName(ownedMember.getAttribute("name"));
        }
        event.setId(element.getAttribute("xmi:id"));
        return event;
    }

    private static void parseTransition(Element eElement, StateChart stateChart){
        //all events under current region
        NodeList vertexList = eElement.getElementsByTagName("transition");
        for (int i = 0; i < vertexList.getLength(); i++) {

            Element elem = (Element) vertexList.item(i);

            Event event = parseEvent(elem);

            Map<State, List<Transition>> stateTransitionMap = stateChart.getStateTransitionMap();

            // find fromState and toState
            Set <State> states = stateTransitionMap.keySet();
            State fromState = null;
            State toState = null;
            String from = elem.getAttribute("source");
            String to = elem.getAttribute("target");
            for (State s : states){
                if (from.equals(s.getId())){
                    fromState = s;
                } else if (to.equals(s.getId())) {
                    toState = s;
                }
            }

            List<Transition> transitionList = stateTransitionMap.get(fromState);

            Transition transition = new Transition(fromState, toState, event);

            transitionList.add(transition);

        }
    }

    private static void parseState(NodeList regions, StateChart stateChart){

        Map<State, List<Transition>> stateTransitionMap = stateChart.getStateTransitionMap();

        Node nNode = regions.item(0);
        Element eElement = (Element) nNode;

        //all states under current region
        Element elem;
        NodeList vertexList = eElement.getElementsByTagName("subvertex");
        for (int i = 0; i < vertexList.getLength(); i++) {
            elem = (Element) vertexList.item(i);
            State state = new NState();
            // If the state has no name
            if (elem.getAttribute("name").length() != 0) {
                state.setName(elem.getAttribute("name"));
            }
            state.setId(elem.getAttribute("xmi:id"));
            List<Transition> transitionList = new ArrayList<Transition>();
            stateTransitionMap.put(state, transitionList);
        }
        parseTransition(eElement, stateChart);
    }


    public static StateChart parser(File f) {

        // return object
        StateChart stateChart = new StateChart();
        Map<State, List<Transition>> stateTransitionMap = new HashMap<State, List<Transition>>();
        stateChart.setStateTransitionMap(stateTransitionMap);


        //TODO Your parser code goes here
        // you should implement the code below
        //stateChart.setStartState(startState);
        //stateChart.setStartState(endState);
        // add all the states and events inside the list
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);
            doc.getDocumentElement().normalize();

            //get the initial region
            NodeList regions = parseRegion(doc);
            parseState(regions, stateChart);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return stateChart;
    }

    public static void main(String[] args) {
        String fileName = "/Users/irenezhang/Documents/school/Verification/SampleXMI/sample.xmi";
        File inputFile = new File(fileName);
        parser(inputFile);
    }

}
