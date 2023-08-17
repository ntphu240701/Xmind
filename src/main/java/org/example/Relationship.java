package org.example;

import java.util.List;
import java.util.UUID;

public class Relationship {
    private String id;
    private String tailId;
    private String headId;


    public Relationship(String tailId, String headId) {
        id = UUID.randomUUID().toString();
        this.tailId = tailId;
        this.headId = headId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTailId() {
        return tailId;
    }

    public void setTailId(String tailId) {
        this.tailId = tailId;
    }

    public String getHeadId() {
        return headId;
    }

    public void setHeadId(String headId) {
        this.headId = headId;
    }
}
