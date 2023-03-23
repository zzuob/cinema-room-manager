package cinema;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Cinema {

    public static void printLine(String[] array) {
        // print each nested array, followed by a newline
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                System.out.println(array[i]);
            }
            else System.out.print(array[i]+" ");
        }
    }

    public static void printRows(String[][] rows) {
        // print a group of rows
        for (int i = 0; i < rows.length; i++) {
            if (i > 0) rows[i][0] = Integer.toString(i);
            printLine(rows[i]);
        }
    }

    static String[] getHeader(int seats) {
        // format table header
        String[] header = new String[seats];
        for (int i = 0; i < seats; i++) {
            header[i] = Integer.toString(i);
        }
        header[0] = " ";
        return header;
    }

    public static int getIncome(int rows, int seats) {
        // calculate income at max capacity
        int totalSeats = rows * seats;
        int[] price = {10, 8}; // prices for small cinema + front half vs back half
        int total = 0;
        if (totalSeats <= 60) total = totalSeats * price[0];
        else {
            int front = rows / 2;
            int back = rows - front;
            total = (front*seats*price[0]) + (back*seats*price[1]);
        }
        return total;
    }

    public static int getPrice(int totalRows, int rowWidth, int row) {
        // get price of one ticket
        int totalSeats = totalRows * rowWidth;
        int price = 10;
        if (totalSeats > 60) {
            int front = totalRows / 2;
            price = row <= front ? 10 : 8;
        }
        return price;
    }
    public static void selectSeat(String[] row, int seatNum) {
        row[seatNum] = "B";
    }

    public static void printAll(String[][] rows) {
        System.out.println("Cinema:");
        printRows(rows);
    }

    public static boolean inBounds(int number, int max){
        return number > 0 && number <= max;
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int totalRows = scan.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int totalSeats = scan.nextInt();
        int width = totalSeats + 1; // +1 for row number in rows[y][0]
        int height = totalRows + 1; // +1 for seat number header
        String[] header = getHeader(width);
        String[][] rows = new String[height][width];
        for (String[] row : rows) {
            Arrays.fill(row, "S");
        }
        rows[0] = header; // set header to row[0]
        int choice = 3;
        int income = 0;
        int ticketsSold = 0;
        int totalIncome = getIncome(totalRows, totalSeats);
        while (choice != 0) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            if (scan.hasNextInt()) {
                choice = scan.nextInt();
            }
            switch (choice) {
                case 1 -> printAll(rows);
                case 2 -> {
                    boolean invalid = true;
                    while (invalid) {
                        System.out.println("Enter a row number:");
                        int rowNumber = scan.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        int seatNumber = scan.nextInt();
                        System.out.println();
                        if (inBounds(rowNumber, totalRows) && inBounds(seatNumber, totalSeats)) {
                            if (Objects.equals(rows[rowNumber][seatNumber], "B")) {
                                System.out.println("That ticket has already been purchased!");
                            } else {
                                System.out.print("Ticket price: $");
                                int ticketPrice = getPrice(totalRows, totalSeats, rowNumber);
                                System.out.println(ticketPrice);
                                selectSeat(rows[rowNumber], seatNumber);
                                income += ticketPrice;
                                ticketsSold++;
                                invalid = false;
                            }
                        } else {
                            System.out.print("Wrong input!");
                        }
                    }
                }
                case 3 -> {
                    System.out.format("Number of purchased tickets: %d\n", ticketsSold);
                    double percent = (((double) ticketsSold) / (totalSeats * totalRows)) * 100;
                    System.out.format("Percentage: %.2f", percent);
                    System.out.println("%");
                    System.out.format("Current income: $%d\n", income);
                    System.out.format("Total income: $%d\n", totalIncome);
                }
                default -> System.out.println("Enter a number from 0-3");
            }
            System.out.println();
        }
    }
}
