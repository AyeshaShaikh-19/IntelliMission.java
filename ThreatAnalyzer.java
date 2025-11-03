package com.intellimission.services;

import com.intellimission.model.Unit;
import java.util.List;

/**
 * ThreatAnalyzer class
 * Performs basic analysis between friendly and enemy units.
 */
public class ThreatAnalyzer {

    public ThreatAnalyzer() {
        // constructor
    }

    /**
     * Analyze threats between friendly and enemy units.
     * @param friendlyUnits list of friendly units
     * @param enemyUnits list of enemy units
     */
    public void analyze(List<Unit> friendlyUnits, List<Unit> enemyUnits) {
        System.out.println("Analyzing threats...");

        if (friendlyUnits == null || enemyUnits == null) {
            System.out.println("No units to analyze.");
            return;
        }

        for (Unit f : friendlyUnits) {
            for (Unit e : enemyUnits) {
                double distance = Math.hypot(f.getX() - e.getX(), f.getY() - e.getY());
                if (distance < 5.0) {
                    System.out.printf("⚠️  Threat detected near %s (enemy close at distance %.2f)%n", f.getName(), distance);
                }
            }
        }
        System.out.println("Threat analysis complete.\n");
    }
}
