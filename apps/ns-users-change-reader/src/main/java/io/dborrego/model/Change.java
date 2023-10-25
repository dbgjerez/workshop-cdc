package io.dborrego.model;

public class Change {
    private PayloadChange payload;

    public Change() {
    }

    public PayloadChange getPayload() {
        return this.payload;
    }

    public void setPayload(PayloadChange payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "{" +
                " payload='" + getPayload() + "'" +
                "}";
    }

}
