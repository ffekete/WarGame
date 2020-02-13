package com.mygdx.wargame.battle.map;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class NodeGraphTest {

    @Test
    public void testConnection() {
        NodeGraph nodeGraph = new NodeGraph(5, 5);

        Node n1 = new Node(1, 1);
        nodeGraph.addNode(n1);

        Node n2 = new Node(2, 1);
        nodeGraph.addNode(n2);

        Node n3 = new Node(1, 2);
        nodeGraph.addNode(n3);

        Node n4 = new Node(2, 2);
        nodeGraph.addNode(n4);

        nodeGraph.connectCities(n1, n2);
        nodeGraph.connectCities(n1, n3);
        nodeGraph.connectCities(n1, n4);

        assertThat(nodeGraph.getEdges().size, is(3));
        assertThat(nodeGraph.getNodes().size, is(4));
        assertThat(nodeGraph.streetsMap.get(n1).size, is(3));
        assertThat(nodeGraph.getNodeEdges().get(n1).size, is(3));


        nodeGraph.disconnectCities(n1);

        assertThat(nodeGraph.getEdges().size, is(0));
        assertThat(nodeGraph.getNodes().size, is(4));
        assertThat(nodeGraph.streetsMap.get(n1).size, is(0));
        assertThat(nodeGraph.getNodeEdges().get(n1).size, is(0));

        nodeGraph.connectCities(n1, n2);
        nodeGraph.connectCities(n1, n3);
        nodeGraph.connectCities(n1, n4);

        assertThat(nodeGraph.getEdges().size, is(3));
        assertThat(nodeGraph.getNodes().size, is(4));
        assertThat(nodeGraph.streetsMap.get(n1).size, is(3));
        assertThat(nodeGraph.getNodeEdges().get(n1).size, is(3));
    }

    @Test
    public void reconnect() {
        NodeGraph nodeGraph = new NodeGraph(5, 5);

        Node n1 = new Node(1, 1);
        nodeGraph.addNode(n1);

        Node n2 = new Node(0, 1);
        nodeGraph.addNode(n2);

        Node n3 = new Node(1, 0);
        nodeGraph.addNode(n3);

        Node n4 = new Node(0, 0);
        nodeGraph.addNode(n4);

        Node n5 = new Node(2, 0);
        nodeGraph.addNode(n5);

        Node n6 = new Node(0, 2);
        nodeGraph.addNode(n6);

        Node n7 = new Node(2, 2);
        nodeGraph.addNode(n7);

        Node n8 = new Node(2, 1);
        nodeGraph.addNode(n8);

        Node n9 = new Node(1, 2);
        nodeGraph.addNode(n9);

        nodeGraph.connectCities(n1, n2);
        nodeGraph.connectCities(n1, n3);
        nodeGraph.connectCities(n1, n4);
        nodeGraph.connectCities(n1, n5);
        nodeGraph.connectCities(n1, n6);
        nodeGraph.connectCities(n1, n7);
        nodeGraph.connectCities(n1, n8);
        nodeGraph.connectCities(n1, n9);

        assertThat(nodeGraph.getEdges().size, is(8));
        assertThat(nodeGraph.getNodes().size, is(9));
        assertThat(nodeGraph.streetsMap.get(n1).size, is(8));
        assertThat(nodeGraph.getNodeEdges().get(n1).size, is(8));

        nodeGraph.disconnectCities(n1);

        assertThat(nodeGraph.getEdges().size, is(0));
        assertThat(nodeGraph.getNodes().size, is(9));
        assertThat(nodeGraph.streetsMap.get(n1).size, is(0));
        assertThat(nodeGraph.getNodeEdges().get(n1).size, is(0));

        nodeGraph.reconnectCities(n1);

        assertThat(nodeGraph.getEdges().size, is(8));
        assertThat(nodeGraph.getNodes().size, is(9));
        assertThat(nodeGraph.streetsMap.get(n1).size, is(8));
        assertThat(nodeGraph.getNodeEdges().get(n1).size, is(8));
    }
}