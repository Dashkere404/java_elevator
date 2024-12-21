import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ElevatorController controller = new ElevatorController(3);  // Создаем контроллер с 3 лифтами
        Random random = new Random();

        // Генерация 10 случайных запросов
        for (int i = 0; i < 10; i++) {
            int floorFrom = random.nextInt(10) + 1;
            int floorTo = random.nextInt(10) + 1;
            while (floorTo == floorFrom) {
                floorTo = random.nextInt(10) + 1;  // Этаж назначения не должен быть равен этажу отправления
            }
            Request request = new Request(floorFrom, floorTo);
            controller.dispatchRequest(request);
            Thread.sleep(500);  // Пауза между запросами
        }
        Thread.sleep(5000);

        // Печать логов всех лифтов
        controller.printElevatorLogs();

        // Остановка всех лифтов
        controller.stopAll();
    }
}
