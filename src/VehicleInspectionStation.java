import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class VehicleInspectionStation {
    public static void main(String[] args) {

        int option;
        System.out.println("wybierz opcję: \n 0 - zamknij program \n 1 - wprowadź nowy pojazd \n " +
                "2 - pobierz pojazd do kontroli");
        try (Scanner scanner = new Scanner(System.in)) {
            option = scanner.nextInt();
            scanner.nextLine();
            Queue<Vehicle> vehicleQueue = new LinkedList<>();

            while (option != 0) {
                if (option == 1) {
                    vehicleQueue.offer(addNewVehicle(scanner));
                } else if (option == 2) {
                    vehicleQueue.poll();
                } else {
                    System.out.println("Nie ma takiej opcji");
                }
                System.out.println("wybierz opcję: \n 0 - zamknij program \n 1 - wprowadź nowy pojazd \n " +
                        "2 - pobierz pojazd do kontroli");
                option = scanner.nextInt();
                scanner.nextLine();
            }

            if (option == 0 && !checkIfQueueEmpty(vehicleQueue)) {
                try {
                    saveQueue(vehicleQueue);
                } catch (IOException e) {
                    System.out.println("Błąd pliku");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Wybierz odpowiednią opcję");
        }
    }

    private static File saveQueue(Queue<Vehicle> queue) throws IOException {
        File file = new File("pojazdy.csv");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        Vehicle vehicle;

        for (int i = 0; i < queue.size(); i++) {
            vehicle = queue.poll();
            bw.write(vehicle.getType() + ";" + vehicle.getBrand() + ";" + vehicle.getModel() + ";"
                    + vehicle.getYear() + ";" + vehicle.getMileage() + ";" + vehicle.getVIN());
            bw.flush();
        }

        bw.close();

        return file;
    }

    private static boolean checkIfQueueEmpty(Queue<Vehicle> queue) {
        return queue.isEmpty();

    }

    private static Vehicle addNewVehicle(Scanner scanner) {
        Vehicle vehicle = new Vehicle();
        int type;
        System.out.println("Wybierz typ pojazdu: \n 1. Samochód osobowy \n 2. Motocykl \n 3. Samochód ciężarowy");
        type = scanner.nextInt();
        scanner.nextLine();
        switch (type) {
            case 1:
                vehicle.setType("car");
                break;
            case 2:
                vehicle.setType("motor");
                break;
            case 3:
                vehicle.setType("truck");
                break;
            default:
                System.out.println("Nie znam takiego typu");
                break;
        }
        System.out.println("Podaj markę pojazdu");
        vehicle.setBrand(scanner.nextLine());
        System.out.println("Podaj model pojazdu");
        vehicle.setModel(scanner.nextLine());
        System.out.println("Podaj rocznik pojazd");
        vehicle.setYear(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Podaj przebieg pojazdu");
        vehicle.setMileage(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Podaj numer VIN pojazdu");
        vehicle.setVIN(scanner.nextLine());

        return vehicle;
    }
}
