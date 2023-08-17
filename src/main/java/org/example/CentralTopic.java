package org.example;

import java.util.ArrayList;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CentralTopic extends Topic {
    public CentralTopic(String title) {
        super(title);
        floatingChildren = new ArrayList<>();
        relationship = new ArrayList<Relationship>();
    }

    private List<Topic> floatingChildren;

    public List<Topic> getFloatingChildren() {
        return floatingChildren;
    }

    public void setFloatingChildren(List<Topic> floatingChildren) {
        this.floatingChildren = floatingChildren;
    }

    private List<Relationship> relationship;

    public List<Relationship> getRelationship() {
        return relationship;
    }

    public void addRelationship(UUID tailId, UUID headId) {
        this.relationship.add(new Relationship(tailId, headId));
    }

    public void addRelationshipWithoutHead(UUID tailId) {
        var newFloatingTopic = new Topic("Floating Topic");
        this.addFloatingTopic(newFloatingTopic);
        addRelationship(tailId, newFloatingTopic.getId());
    }

    public void addRelationshipWithoutTail(UUID headId) {
        var newFloatingTopic = new Topic("Floating Topic");
        this.addFloatingTopic(newFloatingTopic);
        addRelationship(newFloatingTopic.getId(), headId);
    }

    public void addRelationshipWithoutHeadAndTail() {
        var newFloatingTopic1 = new Topic("Floating Topic 1");
        var newFloatingTopic2 = new Topic("Floating Topic 2");
        this.addFloatingTopic(newFloatingTopic1, newFloatingTopic2);
        addRelationship(newFloatingTopic1.getId(), newFloatingTopic2.getId());
    }

    public List<Relationship> getRelationshipsOfTopic(Topic topic) {
        List<Relationship> relationshipList = new ArrayList<>();
        for (var item : this.relationship) {
            if (item.getTailId() == topic.getId()) {
                relationshipList.add(item);
            }
        }
        return relationshipList;
    }

    public void moveRelationshipHead(Relationship relationship, Topic topic) {
        relationship.setHeadId(topic.getId());
    }

    public void moveRelationshipTail(Relationship relationship, Topic topic) {
        relationship.setTailId(topic.getId());
    }

    public void deleteRelationship(Relationship... relationships) {
        for (var item : relationships) {
            this.relationship = deleteRelationshipItem(item, this.relationship);
        }
    }

    public static List<Relationship> deleteRelationshipItem(Relationship element, List<Relationship> list) {
        return list.stream()
                .filter(item -> item != element)
                .collect(Collectors.toList());
    }

    public void deleteSelectedRelationships(Relationship... selectedRelationships) {
        removeRelationships(selectedRelationships);
    }

    void removeRelationships(Relationship... relationships) {
        List<Relationship> relationshipsNeedToRemove = new ArrayList<>();
        for (var item : relationships) {
            relationshipsNeedToRemove.add(item);
        }
        this.traversalRelationships(relationshipsNeedToRemove);
    }

    void traversalRelationships(List<Relationship> relationshipsNeedToRemove) {
        for (var item : this.getRelationship()) {
            if (relationshipsNeedToRemove.contains(item)) {
                this.deleteRelationship(item);
                relationshipsNeedToRemove.remove(item);
            }
            item.traversalRelationships(relationshipsNeedToRemove);
        }
    }

    public void addFloatingTopic(Topic... subTopic) {
        for (var item : subTopic) {
            floatingChildren.add(item);
        }
    }

    public void deleteFloatingTopic(Topic... floatingTopic) {
        for (var item : floatingTopic) {
            this.floatingChildren = removeTopic(item, this.floatingChildren);
        }
    }

    public void moveFloatingTopicToSubtopic(Topic floatingTopic, Topic parentTopic) {
        parentTopic.addChildren(floatingTopic);
        this.deleteFloatingTopic(floatingTopic);
    }

    public void moveFloatingTopicToCentralTopic(Topic floatingTopic, CentralTopic centralTopic) {
        centralTopic.addChildren(floatingTopic);
        this.deleteFloatingTopic(floatingTopic);
    }

    void removeFloatingTopics(Topic... topics) {
        List<Topic> topicsNeedToRemove = new ArrayList<>();
        for (var topic : topics) {
            topicsNeedToRemove.add(topic);
        }
        this.traversalFloatingTopics(topicsNeedToRemove);
    }

    void traversalFloatingTopics(List<Topic> topicsNeedToRemove) {
        for (var item : this.getFloatingChildren()) {
            if (topicsNeedToRemove.contains(item)) {
                this.deleteFloatingTopic(item);
                topicsNeedToRemove.remove(item);
            }
            item.traversal(topicsNeedToRemove);
        }
    }

    public void deleteSelectedTopics(Topic... selectedTopics) {
        //Delete Children
        removeTopics(selectedTopics);

        //Delete Floating Topic
        removeFloatingTopics(selectedTopics);
    }
}
