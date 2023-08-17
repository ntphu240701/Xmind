package org.example;

import java.util.ArrayList;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CentralTopic extends Topic {
    public CentralTopic(String title) {
        super(title);
        floatingChildren = new ArrayList<>();
        relationship = new ArrayList<>();
    }

    private List<Topic> floatingChildren;

    public List<Topic> getFloatingChildren() {
        return floatingChildren;
    }

    private List<Relationship> relationship;

    public List<Relationship> getRelationship() {
        return relationship;
    }

    public void addRelationship(String tailId, String headId) {
        this.relationship.add(new Relationship(tailId, headId));
    }

    public void addRelationshipWithoutHead(String tailId) {
        var newFloatingTopic = new Topic("Floating Topic");
        this.addFloatingTopic(newFloatingTopic);
        addRelationship(tailId, newFloatingTopic.getId());
    }

    public void addRelationshipWithoutTail(String headId) {
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

    public void addFloatingTopic(Topic... subTopic) {
        for (var item : subTopic) {
            floatingChildren.add(item);
        }
    }

    public void deleteFloatingTopicById(String... floatingTopicId) {
        for (var element : floatingTopicId) {
            List<Topic> filteredTopics = floatingChildren.stream()
                    .filter(item -> item.getId() != element)
                    .collect(Collectors.toList());
            this.floatingChildren = filteredTopics;
        }
    }

    public void moveFloatingTopicToSubtopic(Topic floatingTopic, Topic parentTopic) {
        parentTopic.addChildren(floatingTopic);
        this.deleteFloatingTopicById(floatingTopic.getId());
    }

    public void moveFloatingTopicToCentralTopic(Topic floatingTopic, CentralTopic centralTopic) {
        centralTopic.addChildren(floatingTopic);
        this.deleteFloatingTopicById(floatingTopic.getId());
    }

    void removeFloatingTopics(String... topicsId) {
        List<String> topicsIdNeedToRemove = new ArrayList<>();
        for (var topicId : topicsId) {
            topicsIdNeedToRemove.add(topicId);
        }
        this.traversalFloatingTopics(topicsIdNeedToRemove);
    }

    void traversalFloatingTopics(List<String> topicsIdNeedToRemove) {
        for (var item : this.getFloatingChildren()) {
            if (topicsIdNeedToRemove.contains(item.getId())) {
                this.deleteFloatingTopicById(item.getId());
                topicsIdNeedToRemove.remove(item.getId());
            }
            item.traversal(topicsIdNeedToRemove);
        }
    }

    public void deleteSelectedTopics(String... selectedTopicsId) {
        //Delete Children
        removeTopics(selectedTopicsId);

        //Delete Floating Topic
        removeFloatingTopics(selectedTopicsId);
    }
}
