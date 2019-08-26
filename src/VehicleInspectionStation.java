import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class VehicleInspectionStation {
    private static Scanner scanner = new Scanner(System.in);
    private static File file = new File("pojazdy.csv");
    public static void main(String[] args) {
        int option;
        System.out.println("Wybierz opcję: \n 0 - zamknij program \n 1 - wprowadź nowy pojazd \n " +
                "2 - pobierz pojazd do kontroli");
        try {
            option = scanner.nextInt();
            scanner.nextLine();
            Queue<Vehicle> vehicleQueue = new LinkedList<>();

            if (file.exists()) {
                readFile(vehicleQueue);
            }
            runChosenOption(option, vehicleQueue);

        } catch (InputMismatchException | IOException e) {
            System.out.println("Wybierz odpowiednią opcję");
        }
    }

    private static void runChosenOption(int option, Queue<Vehicle> vehicleQueue) throws IOException {
        while (option != 0) {
            if (option == 1) {
                vehicleQueue.offer(typeNewVehicle());
            } else if (option == 2) {
                if (!vehicleQueue.isEmpty()) {
                    vehicleQueue.poll();
                } else {
                    System.out.println("W kolejce nie ma żadnych pojazdów");
                }
            } else {
                System.out.println("Nie ma takiej opcji");
            }
            System.out.println("\n Wybierz opcję: \n 0 - zamknij program \n 1 - wprowadź nowy pojazd \n " +
                    "2 - pobierz pojazd do kontroli");
            option = scanner.nextInt();
            scanner.nextLine();
        }

        if (option == 0) {
                saveQueue(vehicleQueue);
        }
    }

    private static File saveQueue(Queue<Vehicle> queue) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        Vehicle vehicle;

        while(!queue.isEmpty()) {
            vehicle = queue.poll();
            bw.write(vehicle.getType() + ";" + vehicle.getBrand() + ";" + vehicle.getModel() + ";"
                    + vehicle.getYear() + ";" + vehicle.getMileage() + ";" + vehicle.getVIN());
            bw.newLine();
            bw.flush();
        }
        bw.close();

        return file;
    }

    private static Vehicle typeNewVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setType(chooseVehicleType());
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

    private static String chooseVehicleType() {
        int type;
        System.out.println("Wybierz typ pojazdu: \n 1. Samochód osobowy \n 2. Motocykl \n 3. Samochód ciężarowy");
        type = scanner.nextInt();
        scanner.nextLine();
        switch (type) {
            case 1:
                return "car";
            case 2:
                return "motor";
            case 3:
                return "truck";
            default:
                return "Nie znam takiego typu";
        }
    }

    private static void readFile(Queue<Vehicle> vehicleQueue) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        String[] data;

            while ((line = br.readLine()) != null) {
                data = line.split(";");
                vehicleQueue.offer(new Vehicle(data[0], data[1], data[2], Integer.valueOf(data[3]),
                        Integer.valueOf(data[4]), data[5]));
            }

        br.close();
    }
}
