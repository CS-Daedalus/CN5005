package com.gentree.common;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Graph<V extends Comparable<V>>
{
    private final boolean isDirected;
    private final Set<Vertex<V>> vertices;
    private final Set<Edge> edges;
    private IVertex<V> root;

    /**
     * Default Constructor that creates a directed graph
     */
    public Graph()
    {
        this(true);
    }

    /**
     * Constructor that creates a directed or undirected graph
     *
     * @param isDirected true if directed, false if undirected
     */
    public Graph(boolean isDirected)
    {
        this.isDirected = isDirected;
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    // Graph manipulation methods:

    /**
     * Create a Vertex with the given payload and insert it into the graph.
     *
     * @param payload Value of type T
     * @return Vertex
     */
    public Vertex<V> insertVertex(V payload)
    {
        Vertex<V> v = new Vertex<>(payload, isDirected);
        if (null == root)
            root = v;
        vertices.add(v);
        return v;
    }

    /**
     * Removes a Vertex from the graph and all edges connected to it.
     *
     * @param vertex Vertex
     */
    public void removeVertex(IVertex<V> vertex)
    {
        Vertex<V> v = validate(vertex);
        for (IEdge edge : v.outgoing.values())
            removeEdge(edge);

        for (IEdge edge : v.incoming.values())
            removeEdge(edge);

        vertices.remove(v);
    }

    /**
     * Removes an Edge from the graph.
     *
     * @param edge Edge
     */
    public void removeEdge(IEdge edge)
    {
        Edge e = validate(edge);

        List<IVertex<V>> endpoints = e.getEndpoints();

        // Remove vertices at edge endpoints from their incoming/outgoing lists
        Vertex<V> source = validate(endpoints.get(0));
        Vertex<V> destination = validate(endpoints.get(1));
        destination.getIncoming().remove(source);

        // Remove edge from list of edges
        edges.remove(e);
    }

    /**
     * Create an Edge with the given weight between nodes source and destination.
     *
     * @param source Vertex
     * @param destination Vertex
     * @param weight Weight of edge
     * @return Edge
     * @throws IllegalArgumentException if edge already exists
     */
    public IEdge insertEdge(IVertex<V> source, IVertex<V> destination, double weight)
        throws IllegalArgumentException
    {
        if (null == getEdge(source, destination))
        {
            Vertex<V> from = validate(source);
            Vertex<V> to = validate(destination);
            Edge edge = new Edge(from, to, weight);
            edges.add(edge);
            // Skip position.
            from.outgoing.put(to, edge);
            if (isDirected)
                to.incoming.put(from, edge);
            else
                to.outgoing.put(from, edge);
            return edge;
        }
        else
            throw new IllegalArgumentException("Edge already exists");
    }

    /**
     * Return the number of vertices in graph.
     *
     * @return number of vertices in graph
     */
    public int numVertices() {
        return vertices.size();
    }

    /**
     * Return the number of edges in graph.
     *
     * @return number of edges in graph
     */
    public int numEdges() {
        return edges.size();
    }

    /**
     * Return all vertices in graph.
     *
     * @return all vertices in graph
     */
    public Iterable<Vertex<V>> vertices() {
        return new HashSet<>(vertices);
    }

    /**
     * Return all edges in graph.
     *
     * @return all edges in graph
     */
    public Iterable<Edge> edges() {
        return new HashSet<>(edges);
    }

    /**
     * Calls the Dijkstra method destination build the path tree for the given starting vertex,
     * then calls getShortestPath method destination return a list containing all the steps
     * in the shortest path destination the destination vertex.
     *
     * @param source value of type V for Vertex 'source'
     * @param destination   value of type V for vertex 'destination'
     * @return ArrayList of type String of the steps in the shortest path.
     */
    public List<String> getPath(V source, V destination)
    {
        boolean test = dijkstra(source);
        if (!test)
            return Collections.emptyList();
        return getShortestPath(Objects.requireNonNull(getVertex(destination)));
    }

    /**
     * Performs a recursive Depth First Search on the 'root' node (the first vertex created)
     *
     * @return true if connected, false if empty or not connected
     */
    public boolean depthFirstSearch()
    {
        if (vertices.isEmpty())
            return false;

        clearStates();

        if (root == null)
            return false;

        // call recursive function
        depthFirstSearch(root);
        return isConnected();
    }

    /**
     * Performs a Breadth-First Search starting at 'root' node (First created vertex)
     *
     * @return true is connected, false is not or if empty
     */
    public boolean breadthFirstSearch()
    {
        if (vertices.isEmpty())
            return false;
        clearStates();

        if (root == null)
            return false;

        Queue<IVertex<V>> queue = new LinkedList<>();
        queue.add(root);
        root.setVisitState(VisitState.VISITED);

        while (!queue.isEmpty())
        {
            IVertex<V> vSubRoot = queue.peek();
            for (IVertex<V> vertex : vSubRoot.getOutgoing().keySet())
            {

                if (vertex.getVisitState() == VisitState.UNVISITED)
                {
                    vertex.setVisitState(VisitState.VISITED);
                    queue.add(vertex);
                }
            }
            queue.remove();
        }
        return isConnected();
    }

    /**
     * Performs a Breadth-First Search on a given starting vertex
     *
     * @param startVertex Value of type V for starting vertex
     * @return true if connected, false if not or if empty
     */
    public boolean breadthFirstSearch(V startVertex)
    {
        if (vertices.isEmpty())
            return false;
        clearStates();

        IVertex<V> vRoot = getVertex(startVertex);
        if (vRoot == null)
            return false;

        Queue<IVertex<V>> queue = new LinkedList<>();
        queue.add(vRoot);
        vRoot.setVisitState(VisitState.VISITED);

        while (!queue.isEmpty())
        {
            vRoot = queue.peek();
            for (IVertex<V> vertex : vRoot.getOutgoing().keySet())
            {

                if (vertex.getVisitState() == VisitState.UNVISITED)
                {
                    vertex.setVisitState(VisitState.VISITED);
                    queue.add(vertex);
                }
            }
            queue.remove();
        }
        return isConnected();
    }

    /**
     * Test if DFS or BFS returned a connected graph
     * @return true if connected, false if not.
     */
    public boolean isConnected()
    {
        for (Vertex<V> vertex : vertices)
        {
            if (vertex.visitState != VisitState.VISITED)
                return false;
        }
        return true;
    }

    /**
     * list all the edges into a string
     *
     * @return string of edge data
     */
    public String edgesToString()
    {
        String retval = "";
        for (IEdge edge : edges)
        {
            retval += edge + "\n";
        }
        return retval;
    }

    /**
     * Get string of Vertex with all it's ingoing and outgoing adjacencies
     *
     * @return string
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (IVertex<V> v : vertices)
        {
            sb.append("Vertex ").append(v.getPayload()).append("\n");
            if (isDirected)
                sb.append("\t[outgoing]");
            sb.append("\t").append(outDegree(v)).append(" adjacent vertices:\n");
            for (IEdge e : outEdge(v))
            {
                sb.append("\t\t");
                sb.append(
                    String.format(
                        "(node %s to node %s with edge weight %s)",
                        v.getPayload(), opposite(v, e).getPayload(), e.getWeight()
                    )
                );
                sb.append("\n");
            }
            sb.append("\n");
            if (isDirected)
            {
                sb.append("\t[incoming]");
                sb.append("\t").append(inDegree(v)).append(" adjacent vertices:\n");
                for (IEdge e : inEdges(v))
                {
                    sb.append("\t\t");
                    sb.append(
                        String.format(
                            "(node %s to node %s with edge weight %s)",
                            v.getPayload(), opposite(v,e).getPayload(), e.getWeight()
                        )
                    );
                    sb.append("\n");
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    // Graph utility methods:

    /**
     * Safe casts IVertex interface into concrete Vertex
     * @param vertex IVertex
     * @return Vertex
     */
    @Contract(value = "null -> fail", pure = true)
    private @NotNull Vertex<V> validate(IVertex<V> vertex)
    {
        if (!(vertex instanceof Vertex)) {
            throw new IllegalArgumentException("Invalid vertex");
        }
        // Safe cast:
        return (Vertex<V>) vertex;
    }

    private @NotNull Edge validate(@NotNull IEdge edge)
    {
        if (edge.getClass() != Edge.class)
            throw new IllegalArgumentException("Invalid edge");

        return (Edge) edge;
    }

    /**
     * find Vertex in Graph from value
     *
     * @param vertex value of type T
     * @return Vertex, or <code>null</code> if not found.
     */
    @Contract(pure = true)
    private @Nullable Vertex<V> getVertex(V vertex)
    {
        for (Vertex<V> v : vertices)
        {
            if (v.payload.compareTo(vertex) == 0)
                return v;
        }
        return null;
    }

    /**
     * Find edge containing two vertices in direction source -> destination
     * @param source Vertex 'source'
     * @param destination Vertex 'destination'
     * @return Edge, or <code>null</code> if not found.
     */
    private IEdge getEdge(IVertex<V> source, IVertex<V> destination)
    {
        Vertex<V> from = validate(source);
        return from.getOutgoing().get(destination);
    }

    /**
     * Returns the vertex opposite to the given vertex and edge.
     * @param vertex Vertex
     * @param edge Edge
     * @return Vertex
     */
    private IVertex<V> opposite(IVertex<V> vertex, IEdge edge)
    {
        Edge e = validate(edge);
        List<IVertex<V>> endpoints = e.getEndpoints();

        if (endpoints.get(0).equals(vertex))
        {
            return endpoints.get(1);
        }
        else if (endpoints.get(1).equals(vertex))
        {
            return endpoints.get(0);
        }
        else
            throw new IllegalArgumentException("The passed vertex is not incident to the passed edge.");
    }

    /**
     * Returns the incoming edge degree of the vertex.
     * @param vertex Vertex
     * @return int
     */
    private int inDegree(IVertex<V> vertex)
    {
        return validate(vertex).getIncoming().size();
    }

    /**
     * Return the outgoing edge degree of the vertex.
     *
     * @param vertex Vertex
     * @return int
     */
    private int outDegree(IVertex<V> vertex)
    {
        return validate(vertex).getOutgoing().size();
    }

    /**
     * Return an iterable set of Edge objects incoming to the vertex.
     *
     * @param vertex Vertex
     * @return Iterable set of Edge objects
     */
    private @NotNull Iterable<IEdge> inEdges(IVertex<V> vertex)
    {
        return validate(vertex).getIncoming().values();
    }

    /**
     * Return an iterable set of Edge objects outgoing from the vertex
     *
     * @param vertex Vertex
     * @return Iterable set of Edge objects
     */
    private @NotNull Iterable<IEdge> outEdge(IVertex<V> vertex)
    {
        return validate(vertex).getOutgoing().values();
    }


    /**
     * Sets all states to UNVISITED
     */
    private void clearStates()
    {
        for (Vertex<V> vertex : vertices)
        {
            vertex.visitState = VisitState.UNVISITED;
        }
    }

    /**
     * Helper for Depth first search
     *
     * @param vertex vertex
     */
    private void depthFirstSearch(@NotNull IVertex<V> vertex)
    {
        vertex.setVisitState(VisitState.VISITING);

        // loop through neighbors
        for (IVertex<V> v : vertex.getOutgoing().keySet())
        {
            if (v.getVisitState() == VisitState.UNVISITED)
            {
                depthFirstSearch(v);
            }
        }
        vertex.setVisitState(VisitState.VISITED);
    }



    /**
     * Creates path information on the graph using the Dijkstra Algorithm,
     * Puts the information into the Vertices, based on given starting vertex.
     *
     * @param startVertex Value of type V for starting vertex
     * @return true if successful, false if empty or not found.
     */
    private boolean dijkstra(V startVertex)
    {
        if (vertices.isEmpty())
            return false;

        // reset all vertices minDistance and previous
        resetDistances();

        // make sure it is valid
        Vertex<V> source = getVertex(startVertex);
        if (source == null)
            return false;

        // set to 0 and add to heap
        source.minDistance = 0;
        PriorityQueue<IVertex<V>> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(source);

        while (!priorityQueue.isEmpty())
        {
            //pull off top of queue
            IVertex<V> u = priorityQueue.poll();

            // loop through adjacent vertices
            for (IVertex<V> v : u.getOutgoing().keySet())
            {
                // get edge
                Edge e = validate(getEdge(validate(u), validate(v)));

                // add weight to current
                double totalDistance = u.getMinDistance() + e.weight;
                if (totalDistance < v.getMinDistance())
                {
                    // new weight is smaller, set it and add to queue
                    priorityQueue.remove(v);
                    v.setMinDistance(totalDistance);
                    // link vertex
                    v.setPrevious(u);
                    priorityQueue.add(v);
                }
            }
        }
        return true;
    }

    /**
     * Goes through the result tree created by the Dijkstra method and steps backward
     *
     * @param target Vertex end of path
     * @return string List of vertices and weights
     */
    private @NotNull List<String> getShortestPath(@NotNull Vertex<V> target)
    {
        List<String> path = new ArrayList<>();

        // check for no path found
        if (Math.abs(target.minDistance - Integer.MAX_VALUE) < Float.MIN_VALUE)
        {
            path.add("No path found");
            return path;
        }

        // loop through the vertices from end target
        for (Vertex<V> v = target; v != null; v = v.previous)
        {
            path.add(v.payload + " : weight : " + v.minDistance);
        }

        // flip the list
        Collections.reverse(path);
        return path;
    }

    /**
     * for Dijkstra, resets all the path tree fields
     */
    private void resetDistances()
    {
        for (Vertex<V> vertex : vertices)
        {
            vertex.minDistance = Integer.MAX_VALUE;
            vertex.previous = null;
        }
    }

    // Inner helper classes

    public enum VisitState
    {
        UNVISITED,
        VISITING,
        VISITED
    }

    class Vertex<V>
        implements Comparable<Vertex<V>>,
                   IVertex<V>
    {
        private final V payload;

        // variables for Dijkstra Tree
        private Vertex<V> previous;
        private double minDistance = Integer.MAX_VALUE;
        private final Map<IVertex<V>, IEdge> outgoing;
        private Map<IVertex<V>, IEdge> incoming;
        private VisitState visitState;
        private final boolean isDirected;

        /**
         * Creates new Vertex with value T
         * @param payload T
         */
        public Vertex(V payload, boolean isDirected)
        {
            this.payload = payload;
            this.isDirected = isDirected;
            visitState = VisitState.UNVISITED;
            outgoing = new HashMap<>();
            if (isDirected)
                // if directed, add incoming edges
                incoming = new HashMap<>();
        }

        @Override
        public V getPayload()
        {
            return payload;
        }

        /**
         * @return list of incoming adjacent vertices
         * @throws IllegalArgumentException if not directed
         */
        public Map<IVertex<V>, IEdge> getIncoming()
            throws IllegalArgumentException
        {
            if (isDirected)
                return incoming;

            throw new IllegalArgumentException("Graph is not directed");
        }

        /**
         * @return list of outgoing adjacent vertices
         */
        public Map<IVertex<V>, IEdge> getOutgoing()
        {
            return outgoing;
        }

        /**
         * Returns the minimum distance to the source vertex
         * @return minimum distance to the source vertex
         */
        public double getMinDistance()
        {
            return minDistance;
        }

        /**
         * Sets the minimum distance to the source vertex
         * @param minDistance minimum distance to the source vertex
         */
        public void setMinDistance(double minDistance)
        {
            this.minDistance = minDistance;
        }

        /**
         * Returns the previous vertex in the shortest path
         * @return previous vertex in the shortest path
         */
        public IVertex<V> getPrevious()
        {
            return previous;
        }

        /**
         * Sets the previous vertex in the shortest path
         * @param previous previous vertex in the shortest path
         */
        public void setPrevious(IVertex<V> previous)
        {
            this.previous = (Vertex<V>) previous;
        }

        /**
         * Returns the vertex state
         * @return vertex state
         */
        public VisitState getVisitState()
        {
            return visitState;
        }

        /**
         * Sets the vertex state
         * @param state vertex state
         */
        public void setVisitState(VisitState state)
        {
            visitState = state;
        }

        /**
         * @param other Vertex to compare to
         */
        @Override
        public int compareTo(@NotNull Vertex<V> other)
        {
            return Double.compare(minDistance, other.getMinDistance());
        }

        /**
         * @param other Vertex to compare to
         * @return boolean if vertices are equal
         */
        @Override
        public boolean equals(Object other)
        {
            if (other == null)
                return false;
            if (other.getClass() != this.getClass())
                return false;
            Vertex<V> otherVertex = (Vertex<V>) other;
            return payload.equals(otherVertex.payload);
        }

        /**
         * @return hashcode of the payload
         */
        @Override
        public int hashCode()
        {
            return payload.hashCode();
        }

        /**
         * Get string of Vertex with all it's ingoing and outgoing adjacencies
         *
         * @return string
         */
        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Vertex: ").append(payload).append(" : In: ");
            for (IVertex<V> incomingVertex : getIncoming().keySet())
                sb.append(incomingVertex.getPayload()).append(" ");
            sb.append("Out: ");
            for (IVertex<V> outgoingVertex : getOutgoing().keySet())
                sb.append(outgoingVertex.getPayload()).append(" ");

            return sb.toString();
        }
    }

    class Edge implements IEdge
    {
        private final double weight;
        private final List<IVertex<V>> endpoints = new ArrayList<>();

        /**
         * @param source     value of type V for 'source' vertex
         * @param destination     value of type V for 'destination' vertex
         * @param weight integer value for weight of edge
         */
        public Edge(Vertex<V> source, Vertex<V> destination, double weight)
        {
            this.weight = weight;
            endpoints.add(source);
            endpoints.add(destination);
        }

        /**
         * @return Edge as string
         */
        @Override
        public String toString()
        {
            return "Edge From: " + getSource().getPayload() + " to: " + getSource().getPayload() + " weight: " + weight;
        }

        public List<IVertex<V>> getEndpoints()
        {
            return endpoints;
        }

        public IVertex<V> getSource()
        {
            return endpoints.get(0);
        }

        public IVertex<V> getDestination()
        {
            return endpoints.get(1);
        }

        @Override
        public double getWeight()
        {
            return weight;
        }
    }
}
