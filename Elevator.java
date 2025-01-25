
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
class Elevator implements Runnable {
    public int number;
    public int currentFloor;
    private final Queue<Request> tasks;
    private boolean active;
    private final StringBuilder log = new StringBuilder();

    public Elevator(int number) {
        this.number = number;
        this.currentFloor = 1;
        this.tasks = new ConcurrentLinkedQueue<>();
        this.active = true;
    }

    public synchronized void addTask(Request request) {
        tasks.add(request);
        notify(); // Уведомляем поток, если он ожидал задачи
    }

    public synchronized void stop() {
        active = false;
        notify();
    }
    //Запуск выполнения запросов лифта
    @Override
    public void run() {
        try {
            while (active) {
                synchronized (this) {
                    while (tasks.isEmpty()) {
                        wait(); // Ждем, пока не появится задача
                    }
                }
                Request request = tasks.poll();
                if (request != null) {
                    /*if (currentFloor != request.getFloorTo()) {
                        checkForIntermediateStops(); // Не получилось реализовать проверку попутных вызовов
                    }*/
                    moveTo(request.getFloorFrom(), request.getFloorTo()); // Перемещаемся на целевой этаж
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    //Вывод перемещений лифта
    private void moveTo(int currentFloor, int targetFloor) {
        log("Лифт " + number +" движется с " + this.currentFloor + " на " + currentFloor);
        log("Лифт " + number + " прибыл на " + currentFloor);
        log("Лифт " + number + " движется с " + currentFloor + " на " + targetFloor);
        this.currentFloor = targetFloor;
        log("Лифт " + number + " прибыл на " + this.currentFloor);
    }

    // Метод для записи в лог
    private synchronized void log(String message) {
        log.append(message).append("\n");
    }

    // Метод для получения лога в виде строки
    public synchronized String getLog() {
        return log.toString();
    }
}
