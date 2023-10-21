package io.dborrego.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class SourceChange {
    @JsonAlias("ts_ms")
    Long ts;
    @JsonAlias("db")
    String database;
    String table;

    public SourceChange() {
    }

    public Long getTs() {
        return this.ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

}
