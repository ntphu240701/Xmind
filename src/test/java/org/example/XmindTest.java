package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XmindTest {

    private static Result getData() {
        var centralTopic = new CentralTopic("Central Topic");
        var mainTopic1 = new Topic("Main Topic 1");
        var mainTopic2 = new Topic("Main Topic 2");
        var mainTopic3 = new Topic("Main Topic 3");
        var mainTopic4 = new Topic("Main Topic 4");

        centralTopic.addChildren(mainTopic1, mainTopic2, mainTopic3, mainTopic4);

        Result data = new Result(centralTopic, mainTopic1, mainTopic2, mainTopic3, mainTopic4);
        return data;
    }

    private record Result(CentralTopic centralTopic, Topic mainTopic1, Topic mainTopic2, Topic mainTopic3,
                          Topic mainTopic4) {
    }

    @Test
    public void testDefault() {
        Result data = getData();

        assertEquals(4, data.centralTopic.getChildren().size());
    }

    @Test
    public void testAddSubtopicToMainTopic() {
        var mainTopic = new Topic("Main Topic");
        var subTopic1 = new Topic("Subtopic 1");
        var subTopic2 = new Topic("Subtopic 2");
        var subTopic3 = new Topic("Subtopic 3");

        mainTopic.addChildren(subTopic1, subTopic2, subTopic3);

        assertEquals(3, mainTopic.getChildren().size());
    }

    @Test
    public void testAddChildrenToSubtopic() {
        var mainTopic = new Topic("Main Topic");
        var subTopic1 = new Topic("Subtopic 1");
        var subTopic2 = new Topic("Subtopic 2");
        var child1 = new Topic("Subtopic 1 of Subtopic 1");
        var child2 = new Topic("Subtopic 2 of Subtopic 1");

        mainTopic.addChildren(subTopic1, subTopic2);
        subTopic1.addChildren(child1, child2);

        assertEquals(2, subTopic1.getChildren().size());
    }

    @Test
    public void testAddFloatingTopicToCentralTopic() {
        var centralTopic = new CentralTopic("Central Topic");
        var floatingTopic1 = new FloatingTopic("Floating Topic 1");
        var floatingTopic2 = new FloatingTopic("Floating Topic 2");

        centralTopic.addFloatingTopic(floatingTopic1, floatingTopic2);

        assertEquals(2, centralTopic.getFloatingChildren().size());
    }

    @Test
    public void testAddSubtopicToFloatingTopic() {
        var centralTopic = new CentralTopic("Central Topic");
        var floatingTopic1 = new FloatingTopic("Floating Topic 1");
        var floatingTopic2 = new FloatingTopic("Floating Topic 2");
        var subTopic1 = new Topic("Subtopic 1");
        var subTopic2 = new Topic("Subtopic 2");
        var subTopic3 = new Topic("Subtopic 3");

        centralTopic.addFloatingTopic(floatingTopic1, floatingTopic2);
        floatingTopic1.addChildren(subTopic1, subTopic2, subTopic3);

        assertEquals(3, floatingTopic1.getChildren().size());
    }

    @Test
    public void testAddChildrenToFloatingSubtopic() {
        var centralTopic = new CentralTopic("Central Topic");
        var floatingTopic1 = new FloatingTopic("Floating Topic 1");
        var floatingTopic2 = new FloatingTopic("Floating Topic 2");
        var subTopic1 = new Topic("Subtopic 1");
        var subTopic2 = new Topic("Subtopic 2");
        var subTopic3 = new Topic("Subtopic 3");
        var child1 = new Topic("Subtopic 1");
        var child2 = new Topic("Subtopic 2");

        centralTopic.addFloatingTopic(floatingTopic1, floatingTopic2);
        floatingTopic1.addChildren(subTopic1, subTopic2, subTopic3);
        subTopic2.addChildren(child1, child2);

        assertEquals(2, subTopic2.getChildren().size());
    }

    @Test
    public void testOrderTopic() {
        Result data = getData();

        //Before swap
        assertEquals(0, data.centralTopic.getChildren().indexOf(data.mainTopic1));
        assertEquals(3, data.centralTopic.getChildren().indexOf(data.mainTopic4));

        //Swap
        data.centralTopic.orderTopic(data.mainTopic1, data.mainTopic4);

        //After swap
        assertEquals(3, data.centralTopic.getChildren().indexOf(data.mainTopic1));
        assertEquals(0, data.centralTopic.getChildren().indexOf(data.mainTopic4));
    }

    @Test
    public void testMoveSubtopicToMainTopic() {
        Result data = getData();
        var subTopic1 = new Topic("Subtopic 1");
        var subTopic2 = new Topic("Subtopic 2");

        data.mainTopic1.addChildren(subTopic1, subTopic2);

        //Before move Topic to Topic
        assertEquals(2, data.mainTopic1.getChildren().size());

        //Move Topic to Topic
        data.mainTopic1.moveTopicToTopic(subTopic1, data.mainTopic4);

        //After move Topic to Topic
        assertEquals(1, data.mainTopic4.getChildren().size());
        assertEquals(1, data.mainTopic1.getChildren().size());
    }

    @Test
    public void testMoveSubtopicToCentralTopic() {
        Result data = getData();
        var subTopic1 = new Topic("Subtopic 1");
        var subTopic2 = new Topic("Subtopic 2");

        data.mainTopic1.addChildren(subTopic1, subTopic2);

        //Before move Topic to CentralTopic
        assertEquals(4, data.centralTopic.getChildren().size());
        assertEquals(2, data.mainTopic1.getChildren().size());

        //Move Topic to CentralTopic
        data.mainTopic1.moveTopicToTopic(subTopic1, data.centralTopic);

        //After move Topic to CentralTopic
        assertEquals(5, data.centralTopic.getChildren().size());
        assertEquals(1, data.mainTopic1.getChildren().size());
    }

    @Test
    public void testMoveSubtopicToBeFloatingTopic() {
        Result data = getData();
        var subTopic1 = new Topic("Subtopic 1");
        var subTopic2 = new Topic("Subtopic 2");

        data.mainTopic1.addChildren(subTopic1, subTopic2);

        //Before move Subtopic to be FloatingTopic
        assertEquals(0, data.centralTopic.getFloatingChildren().size());
        assertEquals(2, data.mainTopic1.getChildren().size());

        data.mainTopic1.moveTopicToBeFloatingTopic(subTopic2, data.centralTopic);

        assertEquals(1, data.centralTopic.getFloatingChildren().size());
        assertEquals(1, data.mainTopic1.getChildren().size());
    }

    @Test
    public void testMoveFloatingTopicToSubtopic() {
        Result data = getData();
        var floatingTopic1 = new Topic("Floating Topic 1");
        var floatingTopic2 = new Topic("Floating Topic 2");
        var subTopic1 = new Topic("Subtopic 1");
        var subTopic2 = new Topic("Subtopic 2");

        data.centralTopic.addFloatingTopic(floatingTopic1, floatingTopic2);
        data.mainTopic1.addChildren(subTopic1, subTopic2);

        //Before
        assertEquals(2, data.centralTopic.getFloatingChildren().size());
        assertEquals(2, data.mainTopic1.getChildren().size());

        //Move FloatingTopic to Subtopic
        data.centralTopic.moveFloatingTopicToSubtopic(floatingTopic2, subTopic1);

        //After
        assertEquals(1, data.centralTopic.getFloatingChildren().size());
        assertEquals(1, subTopic1.getChildren().size());
    }

    @Test
    public void testMoveFloatingTopicToMainTopic() {
        Result data = getData();
        var floatingTopic1 = new Topic("Floating Topic 1");
        var floatingTopic2 = new Topic("Floating Topic 2");
        var subTopic1 = new Topic("Subtopic 1");
        var subTopic2 = new Topic("Subtopic 2");

        data.centralTopic.addFloatingTopic(floatingTopic1, floatingTopic2);
        data.mainTopic1.addChildren(subTopic1, subTopic2);

        //Before
        assertEquals(2, data.centralTopic.getFloatingChildren().size());
        assertEquals(2, data.mainTopic1.getChildren().size());

        //Move FloatingTopic to MainTopic
        data.centralTopic.moveFloatingTopicToSubtopic(floatingTopic2, data.mainTopic1);

        //After
        assertEquals(1, data.centralTopic.getFloatingChildren().size());
        assertEquals(3, data.mainTopic1.getChildren().size());
    }

    @Test
    public void testMoveFloatingTopicToCentralTopic() {
        Result data = getData();
        var floatingTopic1 = new Topic("Floating Topic 1");
        var floatingTopic2 = new Topic("Floating Topic 1");

        data.centralTopic.addFloatingTopic(floatingTopic1, floatingTopic2);

        //Before
        assertEquals(2, data.centralTopic.getFloatingChildren().size());
        assertEquals(4, data.centralTopic.getChildren().size());

        //Move FloatingTopic to CentralTopic
        data.centralTopic.moveFloatingTopicToCentralTopic(floatingTopic2, data.centralTopic);

        //After
        assertEquals(1, data.centralTopic.getFloatingChildren().size());
        assertEquals(5, data.centralTopic.getChildren().size());
    }

    @Test
    public void testDeleteSubTopic() {
        var mainTopic1 = new Topic("Main Topic 1");
        var subTopic1 = new Topic("Subtopic 1");
        var subTopic2 = new Topic("Subtopic 2");
        var children1 = new Topic("Child 1");

        mainTopic1.addChildren(subTopic1, subTopic2);
        subTopic1.addChildren(children1);

        //Before
        assertEquals(2, mainTopic1.getChildren().size());
        assertEquals(1, subTopic1.getChildren().size());

        //Delete SubTopic
        mainTopic1.deleteChildrenById(subTopic1.getId());

        //After
        assertEquals(1, mainTopic1.getChildren().size());
    }

    @Test
    public void testDeleteMainTopic() {
        Result data = getData();
        var subTopic1 = new Topic("Subtopic 1");
        var children1 = new Topic("Children 1");

        data.mainTopic4.addChildren(subTopic1);
        subTopic1.addChildren(children1);

        //Before
        assertEquals(4, data.centralTopic.getChildren().size());

        //Delete MainTopic
        data.centralTopic.deleteChildrenById(data.mainTopic4.getId(), data.mainTopic1.getId());

        //After
        assertEquals(2, data.centralTopic.getChildren().size());
    }

    @Test
    public void testDeleteFloatingTopic() {
        var centralTopic = new CentralTopic("Central Topic");
        var floatingTopic1 = new Topic("Floating Topic 1");
        var floatingTopic2 = new Topic("Floating Topic 2");

        centralTopic.addFloatingTopic(floatingTopic1, floatingTopic2);

        //Before
        assertEquals(2, centralTopic.getFloatingChildren().size());

        //Delete FloatingTopic
        centralTopic.deleteFloatingTopicById(floatingTopic1.getId());

        //After
        assertEquals(1, centralTopic.getFloatingChildren().size());
    }

    @Test
    public void testDeleteMultipleTopics() {
        Result data = getData();
        var subTopic31 = new Topic("Subtopic 1");
        var subsubTopic31 = new Topic("Children 1");
        var subsubsubTopic31 = new Topic("Children 1");
        var floatingTopic = new Topic("Floating Topic");

        data.centralTopic.addFloatingTopic(floatingTopic);
        data.mainTopic3.addChildren(subTopic31);
        subTopic31.addChildren(subsubTopic31);
        subsubTopic31.addChildren(subsubsubTopic31);

        //Before
        assertEquals(4, data.centralTopic.getChildren().size());
        assertEquals(1, data.centralTopic.getFloatingChildren().size());
        assertEquals(1, subTopic31.getChildren().size());

        //Delete multiple Topics
        data.centralTopic.deleteSelectedTopics(floatingTopic.getId(), data.mainTopic4.getId(), subsubTopic31.getId());

        //After
        assertEquals(0, data.centralTopic.getFloatingChildren().size());
        assertEquals(3, data.centralTopic.getChildren().size());
        assertEquals(0, subTopic31.getChildren().size());
    }

    @Test
    public void testAddRelationship() {
        Result data = getData();
        var subtopic11 = new Topic("Subtopic 1");
        var subtopic12 = new Topic("Subtopic 2");
        var subsubtopic12 = new Topic("SubSubtopic 2");

        data.mainTopic1.addChildren(subtopic11, subtopic12);
        subtopic12.addChildren(subsubtopic12);

        //Before
        assertEquals(0, data.centralTopic.getRelationship().size());

        //Add Relationship
        data.centralTopic.addRelationship(subsubtopic12.getId(), data.mainTopic2.getId());
        data.centralTopic.addRelationship(data.mainTopic3.getId(), subtopic11.getId());

        //After
        assertEquals(2, data.centralTopic.getRelationship().size());
    }

    @Test
    public void testAddRelationshipWithoutHead() {
        Result data = getData();
        var subtopic11 = new Topic("Subtopic 1");
        var subtopic12 = new Topic("Subtopic 2");

        data.mainTopic1.addChildren(subtopic11, subtopic12);

        //Before
        assertEquals(0, data.centralTopic.getRelationship().size());
        assertEquals(0, data.centralTopic.getFloatingChildren().size());

        //Add Relationship without head
        data.centralTopic.addRelationshipWithoutHead(subtopic12.getId());

        //After
        assertEquals(1, data.centralTopic.getRelationship().size());
        assertEquals(1, data.centralTopic.getFloatingChildren().size());
    }

    @Test
    public void testAddRelationshipWithoutTail() {
        Result data = getData();

        var subTopic1 = new Topic("Subtopic 1");

        data.mainTopic1.addChildren(subTopic1);

        //Before
        assertEquals(0, data.centralTopic.getRelationship().size());
        assertEquals(0, data.centralTopic.getFloatingChildren().size());

        //Add Relationship without head
        data.centralTopic.addRelationshipWithoutTail(subTopic1.getId());

        //After
        assertEquals(1, data.centralTopic.getRelationship().size());
        assertEquals(1, data.centralTopic.getFloatingChildren().size());
    }

    @Test
    public void testAddRelationshipWithoutHeadAndTail() {
        Result data = getData();

        //Before
        assertEquals(0, data.centralTopic.getRelationship().size());
        assertEquals(0, data.centralTopic.getFloatingChildren().size());

        //Add Relationship without head
        data.centralTopic.addRelationshipWithoutHeadAndTail();

        //After
        assertEquals(1, data.centralTopic.getRelationship().size());
        assertEquals(2, data.centralTopic.getFloatingChildren().size());
    }

    @Test
    public void testMoveRelationshipHead() {
        Result data = getData();
        var subTopic1 = new Topic("Subtopic 1");

        data.mainTopic1.addChildren(subTopic1);

        data.centralTopic.addRelationship(subTopic1.getId(), data.mainTopic2.getId());

        //Before
        assertEquals(data.mainTopic2.getId(), data.centralTopic.getRelationshipsOfTopic(subTopic1).get(0).getHeadId());

        //Move Relationship Head
        data.centralTopic.moveRelationshipHead(data.centralTopic.getRelationshipsOfTopic(subTopic1).get(0), data.mainTopic4);

        //After
        assertEquals(1, data.centralTopic.getRelationshipsOfTopic(subTopic1).size());
    }

    @Test
    public void testMoveRelationshipTail() {
        Result data = getData();
        var subTopic1 = new Topic("Subtopic 1");
        var subTopic2 = new Topic("Subtopic 2");

        data.mainTopic1.addChildren(subTopic1, subTopic2);

        data.centralTopic.addRelationship(subTopic1.getId(), data.mainTopic2.getId());

        //Before
        assertEquals(subTopic1.getId(), data.centralTopic.getRelationshipsOfTopic(subTopic1).get(0).getTailId());

        //Move Relationship Head
        data.centralTopic.moveRelationshipTail(data.centralTopic.getRelationshipsOfTopic(subTopic1).get(0), subTopic2);

        //After
        assertEquals(1, data.centralTopic.getRelationshipsOfTopic(subTopic2).size());
    }

    @Test
    public void testDeleteRelationship() {
        Result data = getData();
        var subTopic1 = new Topic("Subtopic 1");
        var subTopic2 = new Topic("Subtopic 2");

        data.mainTopic1.addChildren(subTopic1, subTopic2);

        data.centralTopic.addRelationship(subTopic1.getId(), data.mainTopic2.getId());
        data.centralTopic.addRelationship(data.mainTopic3.getId(), subTopic2.getId());

        //Before
        assertEquals(2, data.centralTopic.getRelationship().size());
        assertEquals(subTopic1.getId(), data.centralTopic.getRelationshipsOfTopic(subTopic1).get(0).getTailId());

        //Delete Relationship
        data.centralTopic.deleteRelationship(data.centralTopic.getRelationshipsOfTopic(subTopic1).get(0));

        //After
        assertEquals(1, data.centralTopic.getRelationship().size());
    }

    @Test
    public void testDeleteMultipleRelationships() {
        Result data = getData();
        var subTopic1 = new Topic("Subtopic 1");
        var subTopic2 = new Topic("Subtopic 2");

        data.mainTopic1.addChildren(subTopic1, subTopic2);

        data.centralTopic.addRelationship(subTopic1.getId(), data.mainTopic2.getId());
        data.centralTopic.addRelationship(data.mainTopic3.getId(), subTopic2.getId());
        data.centralTopic.addRelationship(data.mainTopic4.getId(), subTopic2.getId());

        //Before
        assertEquals(3, data.centralTopic.getRelationship().size());
        assertEquals(subTopic1.getId(), data.centralTopic.getRelationshipsOfTopic(subTopic1).get(0).getTailId());
        assertEquals(subTopic2.getId(), data.centralTopic.getRelationshipsOfTopic(data.mainTopic3).get(0).getHeadId());
        assertEquals(subTopic2.getId(), data.centralTopic.getRelationshipsOfTopic(data.mainTopic4).get(0).getHeadId());

        //Delete Multiple Relationships
        data.centralTopic.deleteRelationship(data.centralTopic.getRelationshipsOfTopic(subTopic1).get(0), data.centralTopic.getRelationshipsOfTopic(data.mainTopic3).get(0));

        //After
        assertEquals(1, data.centralTopic.getRelationship().size());
    }
}
