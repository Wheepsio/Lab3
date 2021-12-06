import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;
    HashMap<Location, Waypoint> lock = new HashMap<>();//Словарь с элементами Location-Waypoint
    HashMap<Location, Waypoint> open = new HashMap<>();

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)//Инициализирует объект для использования алгоритма
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }//Возврат карты перемещения алгоритма

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        if (numOpenWaypoints() == 0) {//Если нет путевых точек , возвращает null
            return null;
        }

        Waypoint minWaypoint = null;//Сохранение для наилучшей путевой точки
        float min = Float.MAX_VALUE;//Значение для путевой точки

        for (Waypoint waypoint : open.values()) {//Сканирует все открытые путевые точки
            float cost = waypoint.getTotalCost();
            if (cost < min) {
                min = cost;
                minWaypoint = waypoint;
            }
        }
        return minWaypoint; //Возврат кратчайшего пути
    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one <em>only
     * if</em> the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)//Метод добавляет путевую точку в открытую
    // коллекцию.Если в местоположении новых маршрутных точек еще нетоткрытой маршрутной точки,
    // то новая маршрутная точкапросто добавляется в коллекцию.Однако, если в новом местоположении
    // путевых точек уже есть путевая точка, новая путевая точка заменяется старой в том случае,
    // если новое значение путевых точек меньше, чем текущее значение путевых точек.
    {
        Waypoint openWP = open.get(newWP.loc);
        if (openWP== null){
            open.put(newWP.loc,newWP);
            return true;
        }
        if (openWP.getPreviousCost() > newWP.getPreviousCost()){
            open.put(newWP.loc, newWP);
            return true;
        }
        return false;
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return open.size();
    }//Возвращает число открытых путевых точек


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    // Перемещает путевую точку из открытого списка в закрытый
    {
        open.remove(loc);
        lock.put(loc,open.get(loc));
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return lock.containsKey(loc);
    }//Возвращает true , если коллекция достигла указанноого местоположения.
}