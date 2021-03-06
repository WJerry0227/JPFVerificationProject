package edu.ut.verify.core.event;

import edu.ut.verify.core.statechart.Predicate;

/**
 * Created by Jerry Wang on 2018/11/5.
 */
public abstract class Event {

    private String name;

    private String Id;

    private Predicate predicate;

    public Event(){
        this.predicate = new Predicate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) { this.Id = Id; }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public String toString(){
        if(predicate != null)
            return ""+name+": "+predicate.toString();
        else
            return "";
    }

}
