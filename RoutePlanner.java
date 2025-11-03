package com.intellimission.services;

import com.intellimission.model.TerrainMap;

import java.util.*;

/**
 * A* pathfinding on the TerrainMap grid. Returns a list of [x,y] integer pairs.
 */
public class RoutePlanner {
    private final TerrainMap terrain;

    public RoutePlanner(TerrainMap terrain) {
        this.terrain = terrain;
    }

    private static class Node implements Comparable<Node> {
        final int x, y;
        double g; // cost from start
        double h; // heuristic to goal
        Node parent;

        Node(int x, int y, double g, double h, Node parent) {
            this.x = x; this.y = y; this.g = g; this.h = h; this.parent = parent;
        }

        double f() { return g + h; }

        @Override
        public int compareTo(Node o) { return Double.compare(this.f(), o.f()); }

        @Override
        public boolean equals(Object o) { return (o instanceof Node) && ((Node)o).x == x && ((Node)o).y == y; }

        @Override
        public int hashCode() { return Objects.hash(x, y); }
    }

    private double heuristic(int x1, int y1, int x2, int y2) {
        return Math.hypot(x2 - x1, y2 - y1);
    }

    /**
     * Find a path from (sx,sy) to (gx,gy). If no path, returns empty list.
     * Movement allows 8 directions (orthogonal + diagonal).
     */
    public List<int[]> findPath(int sx, int sy, int gx, int gy) {
        if (terrain == null) return Collections.emptyList();
        if (sx == gx && sy == gy) return Collections.singletonList(new int[]{sx, sy});
        int w = terrain.getWidth(), h = terrain.getHeight();
        if (sx < 0 || sy < 0 || gx < 0 || gy < 0 || sx >= w || sy >= h || gx >= w || gy >= h) {
            return Collections.emptyList();
        }
        // If start or goal impassable, no path.
        if (Double.isInfinite(terrain.getMovementCost(sx, sy)) || Double.isInfinite(terrain.getMovementCost(gx, gy))) {
            return Collections.emptyList();
        }

        boolean[][] closed = new boolean[w][h];
        Map<String, Double> gScore = new HashMap<>();
        PriorityQueue<Node> open = new PriorityQueue<>();

        Node start = new Node(sx, sy, 0.0, heuristic(sx, sy, gx, gy), null);
        open.add(start);
        gScore.put(key(sx, sy), 0.0);

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};

        while (!open.isEmpty()) {
            Node cur = open.poll();
            if (cur.x == gx && cur.y == gy) {
                List<int[]> path = new ArrayList<>();
                for (Node n = cur; n != null; n = n.parent) path.add(new int[]{n.x, n.y});
                Collections.reverse(path);
                return path;
            }

            if (cur.x < 0 || cur.y < 0 || cur.x >= w || cur.y >= h) continue;
            if (closed[cur.x][cur.y]) continue;
            closed[cur.x][cur.y] = true;

            for (int[] d : dirs) {
                int nx = cur.x + d[0], ny = cur.y + d[1];
                if (nx < 0 || ny < 0 || nx >= w || ny >= h) continue;
                if (closed[nx][ny]) continue;
                double baseCost = terrain.getMovementCost(nx, ny);
                if (Double.isInfinite(baseCost)) continue; // impassable
                double stepCost = (d[0] != 0 && d[1] != 0) ? 1.4142 * baseCost : baseCost;
                double tentativeG = cur.g + stepCost;
                String k = key(nx, ny);
                if (!gScore.containsKey(k) || tentativeG < gScore.get(k)) {
                    gScore.put(k, tentativeG);
                    Node neighbor = new Node(nx, ny, tentativeG, heuristic(nx, ny, gx, gy), cur);
                    open.add(neighbor);
                }
            }
        }
        return Collections.emptyList(); // no path
    }

    private String key(int x, int y) { return x + "," + y; }
}
