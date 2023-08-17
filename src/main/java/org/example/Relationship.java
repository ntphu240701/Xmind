package org.example;

import java.util.List;
import java.util.UUID;

public class Relationship {
    private UUID id;
    private UUID tailId;
    private UUID headId;


    public Relationship(UUID tailId, UUID headId) {
        id = UUID.randomUUID();
        this.tailId = tailId;
        this.headId = headId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTailId() {
        return tailId;
    }

    public void setTailId(UUID tailId) {
        this.tailId = tailId;
    }

    public UUID getHeadId() {
        return headId;
    }

    public void setHeadId(UUID headId) {
        this.headId = headId;
    }

    public void traversalRelationships(List<Relationship> relationshipsNeedToRemove) {
    }
}
