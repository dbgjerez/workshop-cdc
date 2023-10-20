package io.dborrego.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class SourceChange {
    @JsonAlias("ts_ms")
    Long ts;

    public SourceChange() {
    }

    public Long getTs() {
        return this.ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

}
