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

    private String topicPosition = "";

    public String getTopicPosition() {
        return topicPosition;
    }

    public void setTopicPosition(String topicPosition) {
        this.topicPosition = topicPosition;
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
            item.topicPosition = this.topicPosition;
            arrangeTopic(item);
        }
    }

    public void arrangeTopic(Topic topic) {
        if (this.children.size() > 4) {
            topic.topicPosition = "left";
            this.children.get(this.children.size() / 2).setTopicPosition("right");
        } else if (this.children.size() < 3)
            topic.setTopicPosition("right");
        else
            topic.setTopicPosition("left");
    }

    public void deleteChildrenById(String... childrenId) {
        for (var element : childrenId) {
            List<Topic> filteredTopics = children.stream()
                    .filter(item -> item.getId() != element)
                    .collect(Collectors.toList());
            this.children = filteredTopics;
        }
    }

    public void orderTopic(Topic topicToMove, Topic targetTopic) {
        var targetIndex = this.children.indexOf(targetTopic);
        this.deleteChildrenById(topicToMove.getId());
        this.children.add(targetIndex, topicToMove);
    }

    public void moveTopicToTopic(Topic topic, Topic parentTopic) {
        parentTopic.addChildren(topic);
        this.deleteChildrenById(topic.getId());
    }

    public void moveTopicToBeFloatingTopic(Topic topic, CentralTopic centralTopic) {
        centralTopic.addFloatingTopic(topic);
        this.deleteChildrenById(topic.getId());
    }

    public void deleteChildrenNodes(String... nodeIds) {
        for (var item : nodeIds) {
            this.deleteChildrenById(item);
        }
    }

    public void deleteChildrenByIdSet(String... idSet) {
        this.deleteChildrenNodes(idSet);
        for (var item : this.children) {
            item.deleteChildrenByIdSet(idSet);
        }
    }
}
