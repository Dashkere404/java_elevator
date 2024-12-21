import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorController {
    private List<Elevator> elevators=new ArrayList<Elevator>();
    private ExecutorService executor;
    //Поиск лифта, который ближе всего в целевому этажу
    private Elevator findBestElevator(Request request){
        return elevators.stream()
                .min(Comparator.comparingInt(elevator -> Math.abs(elevator.currentFloor - request.getFloorFrom())))
                .orElseThrow(() -> new RuntimeException("Нет доступных лифтов"));
    }
    //Создание очереди из лифтов
    public ElevatorController(int elevatorCount) {
        executor = Executors.newFixedThreadPool(elevatorCount);
        for (int i = 0; i < elevatorCount; i++) {
            Elevator elev = new Elevator(i + 1);
            elevators.add(elev);
            new Thread(elev).start();
        }
    }
    /*public void start(){
        for (Elevator elev : elevators) {
            new Thread(elev).start();
        }
    }*/
    //Добавляем запрос этажа в очередь близжайшего лифта
    public void dispatchRequest(Request request) {
        Elevator bestElevator = findBestElevator(request);
        bestElevator.addTask(request);
        System.out.println("Запрос с этажа " + request.getFloorFrom() + " на этаж " +  request.getFloorTo() + " отправлен лифту " + bestElevator.number);
    }
    //Остановка всех лифтов
    public void stopAll() {
        for (Elevator elevator : elevators) {
            elevator.stop();
        }
        executor.shutdown();
    }
    //Вывод лога программы
    public void printElevatorLogs() {
        System.out.println(String.format("%-10s %-60s", "Лифт", "Лог"));
        System.out.println("-----------------------------------------");

        for (Elevator elevator : elevators) {
            String log = elevator.getLog();
            String[] logLines = log.split("\n");

            for (String logLine : logLines) {
                System.out.println(String.format("%-10d %-60s", elevator.number, logLine));
            }
        }
    }
}