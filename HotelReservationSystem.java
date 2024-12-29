import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class HotelReservationSystem {
    private static List<Room> rooms = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initializing some rooms
        rooms.add(new Room(101, "Single", true, 100));
        rooms.add(new Room(102, "Double", true, 150));
        rooms.add(new Room(103, "Suite", true, 300));

        boolean exit = false;

        while (!exit) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    searchRooms();
                    break;
                case 2:
                    makeReservation();
                    break;
                case 3:
                    viewBookings();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Thank you for using the Hotel Reservation System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\nHotel Reservation System");
        System.out.println("1. Search for Available Rooms");
        System.out.println("2. Make a Reservation");
        System.out.println("3. View Booking Details");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void searchRooms() {
        System.out.print("Enter room type (Single/Double/Suite): ");
        String roomType = scanner.nextLine();
        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(roomType) && room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    private static void makeReservation() {
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Room room = findRoomByNumber(roomNumber);

        if (room != null && room.isAvailable()) {
            System.out.print("Enter customer name: ");
            String customerName = scanner.nextLine();
            System.out.print("Enter check-in date (yyyy-mm-dd): ");
            Date checkInDate = new Date(scanner.nextLine());
            System.out.print("Enter check-out date (yyyy-mm-dd): ");
            Date checkOutDate = new Date(scanner.nextLine());
            System.out.print("Enter card number for payment: ");
            String cardNumber = scanner.nextLine();

            if (Payment.processPayment(cardNumber, room.getPrice())) {
                Reservation reservation = new Reservation(room, customerName, checkInDate, checkOutDate);
                reservations.add(reservation);
                room.setAvailable(false);
                System.out.println("Reservation successful!");
            } else {
                System.out.println("Payment failed. Reservation not completed.");
            }
        } else {
            System.out.println("Room not available or invalid room number.");
        }
    }

    private static Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    private static void viewBookings() {
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomerName().equalsIgnoreCase(customerName)) {
                System.out.println(reservation);
            }
        }
    }
}
 class Payment {
    public static boolean processPayment(String cardNumber, double amount) {
        // Simulate payment processing
        return cardNumber != null && !cardNumber.isEmpty() && amount > 0;
    }
}

 class Reservation {
    private Room room;
    private String customerName;
    private Date checkInDate;
    private Date checkOutDate;

      public Reservation(Room room, String customerName, Date checkInDate, Date checkOutDate) {
        this.room = room;
        this.customerName = customerName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Room getRoom() {
        return room;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "room=" + room +
                ", customerName='" + customerName + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                '}';
    }
}
 class Room {
    private int roomNumber;
    private String roomType;
    private boolean isAvailable;
    private double price;

    public Room(int roomNumber, String roomType, boolean isAvailable, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isAvailable = isAvailable;
        this.price = price;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", roomType='" + roomType + '\'' +
                ", isAvailable=" + isAvailable +
                ", price=" + price +
                '}';
    }
}
