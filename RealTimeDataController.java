package com.intellimission.services;

import com.intellimission.model.Unit;
import java.util.List;
import java.util.Objects;

/**
 * Simple controller for runtime unit list.
 */
public class RealTimeDataController {

    private final List<Unit> units;

    public RealTimeDataController(List<Unit> units) {
        this.units = Objects.requireNonNull(units, "units list required");
    }

    public List<Unit> listUnits() {
        return units;
    }

    public void addUnit(Unit unit) {
        if (unit != null) units.add(unit);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }
}
