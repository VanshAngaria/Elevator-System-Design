import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ElevatorController {
    private boolean stopController;

    private static Map<Integer, Elevator> upMovingMap = new HashMap<Integer, Elevator>();

    private static Map<Integer, Elevator> downMovingMap = new HashMap<Integer, Elevator>();

    private static List<Elevator> elevatorList = new ArrayList<Elevator>(16);
    private static final ElevatorController instance = new ElevatorController();

    private ElevatorController(){
        if(instance != null){
            throw new IlligalStateException("Already instantiated");
        }

        setStopController(false);
        initializeElevator();
    }
    public static ElevatorController getInstance(){
        return instance;
    }

    public synchronized  Elevator selectElevator(ElevatorRequest elevatorRequest){
        Elevator elevator = null;

        ElevatorState elevatorState = getRequestedElevatorDirection(elevatorRequest);
        int requestedFloor = elevatorRequest.getRequestFloor();
        int targetFloor = elevatorRequest.getTargetFloor();

        elevator = findElevator(elevatorState, requestedFloor, targetFloor);
        notifyAll();
        return elevator;
    }

    private static ElevatorState getRequestedElevatorDirection(ElevatorRequest elevatorRequest){
        ElevatorState elevatorState = null;
        int requestedFloor = elevatorRequest.getRequestFloor();
        int targetFloor = elevatorRequest.getTargetFloor();

        if(targetFloor - requestedFloor > 0){
            elevatorState = ElevatorState.UP;
        }
        else{
            elevatorState = ElevatorState.DOWN;
        }
        return elevatorState;
    }

    private static Elevator findElevator(ElevatorState elevatorState, int requestedFloor, int targetFloor){
        Elevator elevator = null;

        TreeMap<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>();

        if(elevatorState.equals(ElevatorState.UP)){
            for (Map.Entry<Integer, Elevator> elvMap : upMovingMap.entrySet()){
                Elevator elv = elvMap.getValue();
                Integer distance = requestedFloor - elv.getCurrentFloor();
                if( distance < 0 && elv.getElevatorState().equals(ElevatorState.UP)) {
                    continue;
                }
            }
        }
    }










}
