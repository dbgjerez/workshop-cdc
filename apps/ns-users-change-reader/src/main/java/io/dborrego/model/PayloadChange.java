package io.dborrego.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class PayloadChange {
    private UserChange after;
    private UserChange before;
    private SourceChange source;
    @JsonAlias("op")
    private String operation;

    public PayloadChange() {
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public SourceChange getSource() {
        return this.source;
    }

    public void setSource(SourceChange source) {
        this.source = source;
    }

    public UserChange getAfter() {
        return this.after;
    }

    public void setAfter(UserChange after) {
        this.after = after;
    }

    public UserChange getBefore() {
        return this.before;
    }

    public void setBefore(UserChange before) {
        this.before = before;
    }

    @Override
    public String toString() {
        return "{" +
                " after='" + getAfter() + "'" +
                ", before='" + getBefore() + "'" +
                ", source='" + getSource() + "'" +
                ", operation='" + getOperation() + "'" +
                "}";
    }

}
