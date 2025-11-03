package com.intellimission.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple manager for mission objectives.
 */
public class MissionObjectiveManager {
    private final Map<String, MissionObjective> objectives = new LinkedHashMap<>();

    public synchronized void addObjective(MissionObjective o) {
        if (o == null) throw new IllegalArgumentException("Objective is null");
        objectives.put(o.getId(), o);
    }

    public synchronized MissionObjective getObjective(String id) {
        return objectives.get(id);
    }

    public synchronized void removeObjective(String id) {
        objectives.remove(id);
    }

    public synchronized List<MissionObjective> listObjectives() {
        return new ArrayList<>(objectives.values());
    }

    /** Return objectives sorted by priority (ascending). */
    public synchronized List<MissionObjective> listByPriority() {
        return objectives.values().stream()
                .sorted(Comparator.comparingInt(MissionObjective::getPriority))
                .collect(Collectors.toList());
    }

    public synchronized void clear() {
        objectives.clear();
    }
}
