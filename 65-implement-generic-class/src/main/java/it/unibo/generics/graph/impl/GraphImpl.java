package it.unibo.generics.graph.impl;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import it.unibo.generics.graph.api.Graph;

public class GraphImpl<N> implements Graph<N>{

    HashSet<N> nodes;
    HashMap<N, HashSet<N>> edges;

    public GraphImpl(){
        nodes = new HashSet<>();
        edges = new HashMap<>();
    }

    @Override
    public void addNode(N node) {
        if(node == null) return;

        nodes.add(node);
        edges.putIfAbsent(node, new HashSet<>());
    }

    @Override
    public void addEdge(N source, N target) {
        if (source == null || target == null) return;
        if (!nodes.contains(source) || !nodes.contains(target)) return;
    
        edges.putIfAbsent(source, new HashSet<>());
        edges.get(source).add(target);
    }

    @Override
    public Set<N> nodeSet() {
        return Collections.unmodifiableSet(nodes);
    }

    @Override
    public Set<N> linkedNodes(N node) {
        if (node == null) return Set.of();
        return Collections.unmodifiableSet(edges.getOrDefault(node, new HashSet<>()));
    }

    @Override
    public List<N> getPath(N source, N target) {
        if (source == null || target == null || !nodes.contains(source) || !nodes.contains(target)) {
            return List.of();
        }

        // Mappa per ricostruire il cammino
        Map<N, N> predecessor = new HashMap<>();
        Queue<N> queue = new ArrayDeque<>();
        Set<N> visited = new HashSet<>();

        visited.add(source);
        queue.add(source);

        while (!queue.isEmpty()) {
            N current = queue.poll();

            if (current.equals(target)) {
                // trovato: ricostruisci il cammino
                return reconstructPath(source, target, predecessor);
            }

            for (N neighbor : edges.getOrDefault(current, new HashSet<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    predecessor.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        return List.of();
    }

    /**
     * Ricostruisce il cammino da source a target usando la mappa dei predecessori.
     */
    private List<N> reconstructPath(N source, N target, Map<N, N> predecessor) {
        LinkedList<N> path = new LinkedList<>();
        N step = target;

        while (step != null) {
            path.addFirst(step);
            step = predecessor.get(step);
        }

        // Se il primo nodo non è la sorgente, non esiste un cammino valido
        if (!path.isEmpty() && !path.getFirst().equals(source)) {
            return List.of();
        }
        return path;
    }
    
}
