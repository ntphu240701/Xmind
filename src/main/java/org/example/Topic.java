package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Topic {
    public Topic(String title) {
        id = UUID.randomUUID();
        this.title = title;
        children = new ArrayList<Topic>();
    }

    private UUID id;

    public UUID getId() {
        return id;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private int height;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int width;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private List<Topic> children;

    public List<Topic> getChildren() {
        return children;
    }

    public void setChildren(List<Topic> children) {
        this.children = children;
    }

    public void addChildren(Topic... subTopics) {
        for (var item : subTopics) {
            children.add(item);
        }
    }

    public void deleteChildren(Topic... subTopics) {
        for (var item : subTopics) {
            this.children = removeTopic(item, this.children);
        }
    }

    public static List<Topic> removeTopic(Topic topic, List<Topic> list) {
        return list.stream()
                .filter(item -> item != topic)
                .collect(Collectors.toList());
    }

    public void orderTopic(Topic topicA, Topic topicB) {
        int indexA = this.children.indexOf(topicA);
        int indexB = this.children.indexOf(topicB);
        this.children.set(indexA, topicB);
        this.children.set(indexB, topicA);
    }

    public void moveTopicToTopic(Topic topic, Topic parentTopic) {
        parentTopic.addChildren(topic);
        this.deleteChildren(topic);
    }

    public void moveTopicToBeFloatingTopic(Topic topic, CentralTopic centralTopic) {
        centralTopic.addFloatingTopic(topic);
        this.deleteChildren(topic);
    }

    void removeTopics(Topic... topics) {
        List<Topic> topicsNeedToRemove = new ArrayList<>();
        for (var topic : topics) {
            topicsNeedToRemove.add(topic);
        }
        this.traversal(topicsNeedToRemove);
    }

    void traversal(List<Topic> topicsNeedToRemove) {
        for (var item : this.getChildren()) {
            if (topicsNeedToRemove.contains(item)) {
                this.deleteChildren(item);
                topicsNeedToRemove.remove(item);
            }
            item.traversal(topicsNeedToRemove);
        }
    }
}
