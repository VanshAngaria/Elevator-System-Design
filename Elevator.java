import java.util.LinkedHashMap;
import java.util.NavigableSet;

public class Elevator implements Runnable {

    private boolean operating;
    private int id;
    private ElevatorState elevatorState;
    private int currentFloor;

    private NavigableSet<Integer> floorStops;

    public Map<ElevatorState, NavigableSet<Integer>> floorStopsMap;

    public Elevator(int id){
        this.id = id;
        setOperating(true);
    }

    public int getId(){
        return id;
    }

    public ElevatorState getElevatorState(){
        return ElevatorState;
    }

    public int getCurrentFloor(){
        return currentFloor;
    }

    public void setElevatorState(ElevatorState elevatorState){
        this.elevatorState = elevatorState;
    }

    public boolean isOperating(){
        return this.operating;
    }
    public void setOperating(boolean state){
        this.operating = state;

        if(!state){
            setElevatorState(ElevatorState.MAINTAINANCE);
            this.floorStops.clear();
        }
        else {
            setElevatorState(ElevatorState.STATIONARY);
            this.floorStopsMap = new LinkedHashMap<(ElevatorState, NavigableSet<Integer>>());

            ElevatorController.updateElevatorLists(this);
        }

        public void setCurrentFloor(int currentFloor){
            this.currentFloor = currentFloor;
        }

    }
}
