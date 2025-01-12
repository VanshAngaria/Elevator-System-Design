import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NavigableSet;

public class Elevator implements Runnable {

    private boolean operating;
    private int id;
    private ElevatorState elevatorState;
    private int currentFloor;

    private NavigableSet<Integer> floorStops;

    public Map<ElevatorState, NavigableSet<Integer>> floorStopsMap;

    public Elevator(int id) {
        this.id = id;
        setOperating(true);
    }

    public int getId() {
        return id;
    }

    public ElevatorState getElevatorState() {
        return ElevatorState;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setElevatorState(ElevatorState elevatorState) {
        this.elevatorState = elevatorState;
    }

    public boolean isOperating() {
        return this.operating;
    }

    public void setOperating(boolean state) {
        this.operating = state;

        if (!state) {
            setElevatorState(ElevatorState.MAINTAINANCE);
            this.floorStops.clear();
        } else {
            setElevatorState(ElevatorState.STATIONARY);
            this.floorStopsMap = new LinkedHashMap<(ElevatorState, NavigableSet < Integer >> ());
            this.floorStopsMap = new LinkedHashMap<ElevatorState, NavigableSet<Integer>>();


            ElevatorController.updateElevatorLists(this);
        }
        setCurrentFloor(0);
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void move() {
        synchronized (ElevatorController.getInstance()) {
            Iterator<ElevatorState> iter = floorStopsMap.keySet().iterator();
            while (iter.hasNext()) {
                elevatorState = iter.next();

                floorStops = floorStopsMap.get(elevatorState);
                iter.remove();
                Integer currFlr = null;
                Integer nextFlr = null;

                while (!floorStops.isEmpty()) {
                    if (elevatorState.equals(ElevatorState.UP)) {
                        currFlr = floorStops.pollLast();
                        nextFlr = floorStops.lower(currFlr);
                    } else if (elevatorState.equals(ElevatorState.DOWN)) {
                        currFlr = floorStops.pollLast();
                        nextFlr = floorStops.lower(currFlr);
                    } else {
                        return;
                    }

                    setCurrentFloor(currFlr);

                    if (nextFlr != null) {
                        generateIntermediateFloors(currFlr, nextFlr);
                    } else {
                        setElevatorState(ElevatorState.STATIONARY);
                        ElevatorController.updateElevatorLists(this);
                    }

                    System.out.println("Elevator ID" + this.id + " | current floor - " + getCurrentFloor() + " | next move - " + getElevatorState());
                    try {
                        Thread.sleep(1000); // get off time
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                ElevatorController.getInstance().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateIntermediateFloors(int initial, int target){
        if(initial == target){
            return;
        }
        if(Math.abs(initial - target) == 1){
            return;
        }
        int n = 1;
        if(target - initial <0){
            n = -1;
        }

        while(initial != target){
            initial += n;
            if(!floorStops.contains(initial)){
                floorStops.add(initial);
            }
        }
    }
    @Override
    public void run() {
        while(true){
            if(isOperating()){
                move();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }
    }
}
