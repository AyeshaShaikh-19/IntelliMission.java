package com.intellimission.model;

import java.util.ArrayList;
import java.util.List;

/**
 * UnitManager manages friendly and enemy units on the map.
 */
public class UnitManager {

    private final List<Unit> friendlyUnits;
    private final List<Unit> enemyUnits;

    public UnitManager() {
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
    }

    // Add a friendly unit
    public void addFriendlyUnit(Unit unit) {
        if (unit != null) friendlyUnits.add(unit);
    }

    // Add an enemy unit
    public void addEnemyUnit(Unit unit) {
        if (unit != null) enemyUnits.add(unit);
    }

    // Get list of friendly units
    public List<Unit> getFriendlyUnits() {
        return friendlyUnits;
    }

    // Get list of enemy units
    public List<Unit> getEnemyUnits() {
        return enemyUnits;
    }

    // Remove all units
    public void clearAll() {
        friendlyUnits.clear();
        enemyUnits.clear();
    }
}
