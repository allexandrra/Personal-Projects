import hlt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Collection;

public class MyBot {

    public static void main(final String[] args) {
        final Networking networking = new Networking();
        final GameMap gameMap = networking.initialize("Tamagocchi");

        // We now have 1 full minute to analyse the initial map.
        final String initialMapIntelligence =
                "width: " + gameMap.getWidth() +
                "; height: " + gameMap.getHeight() +
                "; players: " + gameMap.getAllPlayers().size() +
                "; planets: " + gameMap.getAllPlanets().size();
        Log.log(initialMapIntelligence);

        final ArrayList<Planet> dockedPlanets = new ArrayList<>();
        final ArrayList<Move> moveList = new ArrayList<>();
        Map<Double, Planet> closeRangePlanets = new TreeMap<>();
        Map<Double, Ship> enemyShips = new TreeMap<>();
        final ArrayList<Ship> attackedShips = new ArrayList<>();
        for (;;) {
            moveList.clear();

            networking.updateMap(gameMap);

            Ship s = gameMap.getClosestShipToCenter(gameMap.getWidth(), gameMap.getHeight());
            Log.log("closest ship is " + s.getId());

            for (final Ship ship : gameMap.getMyPlayer().getShips().values()) {
                if (ship.getDockingStatus() != Ship.DockingStatus.Undocked) {
                    continue;
                }

                enemyShips.clear();
                enemyShips = gameMap.nearbyEnemyShipsByDistance(ship);

                Log.log("closeRangePlanets = " + enemyShips.size());
                for (Map.Entry<Double, Ship> entry : enemyShips.entrySet()) {
                    Ship ships = entry.getValue();
                    Log.log("enemyShip Id = " + ships.getId());
                }

                //prioritizeaza planetele cu algoritm greedy pt cea mai apropiata planeta ca distanta
                //sort planets by distance to the current ship (GameMap.java)
                closeRangePlanets.clear();
                closeRangePlanets = gameMap.nearbyPlanetsByDistance(ship);

                /*Log.log("closeRangePlanets = " + closeRangePlanets.size());
                for (Map.Entry<Double, Planet> entry : closeRangePlanets.entrySet()) {
                    Planet planet = entry.getValue();
                    Log.log("planet Id = " + planet.getId() + " docked Slots + planet.getDockedShips().size());
                }*/

                for (Map.Entry<Double, Planet> entry : closeRangePlanets.entrySet()) {
                    Planet planet = entry.getValue();
                    // daca nu suntem noi ownerul planetei
                    if (planet.getOwner() != gameMap.getMyPlayerId()) {

                        for(Map.Entry<Double, Ship> entryShip : enemyShips.entrySet()) {
                            Ship enemyShip = entryShip.getValue();

                            if(attackedShips.contains(enemyShip)) {
                                continue;
                            }

                            if (ship.canAttack(enemyShip)) {
                                moveList.add(new AttackMove(ship, enemyShip));
                                attackedShips.add(enemyShip);
                                break;
                            }

                            final ThrustMove newThrustMove = Navigation.navigateShipToAttack(gameMap, ship, enemyShip, Constants.MAX_SPEED);
                            if (newThrustMove != null) {
                                moveList.add(newThrustMove);
                            }

                            break;
                        }
                        break;
                    }

                    if(dockedPlanets.contains(planet)) {
                        if (dockedPlanets.get(dockedPlanets.indexOf(planet)).getDockedShips().size() == 2) {
                            continue;
                        } else {
                            if (ship.canDock(planet)) {
                                moveList.add(new DockMove(ship, planet));
                                dockedPlanets.add(planet);
                                break;
                            }

                            final ThrustMove newThrustMove = Navigation.navigateShipToDock(gameMap, ship, planet, Constants.MAX_SPEED);
                            if (newThrustMove != null) {
                                moveList.add(newThrustMove);
                            }

                            break;
                        }
                    }

                    if (ship.canDock(planet)) {
                        moveList.add(new DockMove(ship, planet));
                        dockedPlanets.add(planet);
                        break;
                    }

                    final ThrustMove newThrustMove = Navigation.navigateShipToDock(gameMap, ship, planet, Constants.MAX_SPEED);
                    if (newThrustMove != null) {
                        moveList.add(newThrustMove);
                    }

                    break;
                }
            }
            Networking.sendMoves(moveList);
        }
    }
}
