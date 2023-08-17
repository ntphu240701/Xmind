package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Topic {
    public Topic(String title) {
        id = UUID.randomUUID().toString();
        this.title = title;
        children = new ArrayList<>();
    }

    private String id;

    public String getId() {
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

    public void deleteChildrenById(String... childrenId) {
        for (var element : childrenId) {
            List<Topic> filteredTopics = children.stream()
                    .filter(item -> item.getId() != element)
                    .collect(Collectors.toList());
            this.children = filteredTopics;
        }
    }

    public void orderTopic(Topic topicA, Topic topicB) {
        int indexA = this.children.indexOf(topicA);
        int indexB = this.children.indexOf(topicB);
        this.children.set(indexA, topicB);
        this.children.set(indexB, topicA);
    }

    public void moveTopicToTopic(Topic topic, Topic parentTopic) {
        parentTopic.addChildren(topic);
        this.deleteChildrenById(topic.getId());
    }

    public void moveTopicToBeFloatingTopic(Topic topic, CentralTopic centralTopic) {
        centralTopic.addFloatingTopic(topic);
        this.deleteChildrenById(topic.getId());
    }

    public void removeTopics(String... topicsId) {
        List<String> topicsIdNeedToRemove = new ArrayList<>();
        for (var topicId : topicsId) {
            topicsIdNeedToRemove.add(topicId);
        }
        this.traversal(topicsIdNeedToRemove);
    }

    public void traversal(List<String> topicsIdNeedToRemove) {
        for (var item : this.getChildren()) {
            if (topicsIdNeedToRemove.contains(item.getId())) {
                this.deleteChildrenById(item.getId());
                topicsIdNeedToRemove.remove(item.getId());
            }
            item.traversal(topicsIdNeedToRemove);
        }
    }
}
