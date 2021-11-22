package com.gentree.common;

import java.util.Map;

public interface IVertex<V>
{
    /**
     * Returns the vertex payload
     * @return vertex payload
     */
    V getPayload();

    /**
     * @return list of incoming adjacent vertices
     * @throws IllegalArgumentException if not directed
     */
    public Map<IVertex<V>, IEdge> getIncoming()
        throws IllegalArgumentException;

    /**
     * @return list of outgoing adjacent vertices
     */
    public Map<IVertex<V>, IEdge> getOutgoing();

    /**
     * Returns the minimum distance to the source vertex
     * @return minimum distance to the source vertex
     */
    double getMinDistance();

    /**
     * Sets the minimum distance to the source vertex
     * @param minDistance minimum distance to the source vertex
     */
    void setMinDistance(double minDistance);

    /**
     * Returns the previous vertex in the shortest path
     * @return previous vertex in the shortest path
     */
    IVertex<V> getPrevious();

    /**
     * Sets the previous vertex in the shortest path
     * @param previous previous vertex in the shortest path
     */
    void setPrevious(IVertex<V> previous);

    /**
     * Returns the vertex state
     * @return vertex state
     */
    Graph.VisitState getVisitState();

    /**
     * Sets the vertex state
     * @param state vertex state
     */
    void setVisitState(Graph.VisitState state);
}
