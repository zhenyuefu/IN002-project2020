package menu;

import club.Club;
import club.Equipment;
import club.Member;
import club.room.Room;
import io.CSV;
import io.InputData;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    private static Menu menuInstance;

    static {
        try {
            menuInstance = new Menu();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't open data! Program exit!");
            System.exit(-1);
        }
    }

    private final Club c = readData();

    private Menu() throws IOException {
    }

    public static Menu getInstance() {
        return menuInstance;
    }

    private Club readData() throws IOException {
        List<String[]> memberData = CSV.read("member.csv");
        List<String[]> equipmentData = CSV.read("equipment.csv");
        List<String[]> roomData = CSV.read("room.csv");
        return new Club(memberData, equipmentData, roomData);
    }

    public void mainMenu() throws IOException {
        String i = null;
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("Main menu\n1.Member\n2.Equipment\n3.Room\n4.exit\n");
            if (scan.hasNextLine()) {
                i = scan.nextLine();
            }
            if (Objects.equals(i, "4"))
                exit();
            switch (Objects.requireNonNull(i)) {
                case "1" -> memberMenu();
                case "2" -> equipmentMenu();
                case "3" -> roomMenu();
                default -> System.out.print("Menu not found\n\n");
            }
        }
    }

    public void memberMenu() throws IOException {
        String i = null;
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("Member Menu\n1.Add Member\n2.Search Member\n3.back\n");
            if (scan.hasNextLine()) {
                i = scan.nextLine();
            }
            if (Objects.equals(i, "3"))
                break;
            switch (Objects.requireNonNull(i)) {
                case "1" -> addMember();
                case "2" -> {
                    c.showMembers();
                    Member m = searchMember();
                    if (m != null)
                        actionMemberMenu(m);
                }
                default -> System.out.print("Menu not found\n\n");
            }
        }
    }

    public void addMember() {
        String name = InputData.scanString("Enter name:");
        String birthday = InputData.scanBirthday();
        c.addMember(name, birthday);
    }

    public Member searchMember() {
        System.out.println("Search by name and birthday:");
        String name = InputData.scanString("Enter name:");
        String birthday = InputData.scanBirthday();
        Member m = c.getMember(name, birthday);
        if (m == null) {
            System.out.println("Can't find " + name);
            c.searchMember(name);
        }
        return m;
    }

    public void actionMemberMenu(Member m) throws IOException {
        String i = null;
        Scanner scan = new Scanner(System.in);
        System.out.println(m.toString());
        while (true) {
            System.out.print("1.Rent Equipment\n2.Return Equipment\n3.Quit club\n4.Using Room\n5.Book Room\n6.Back\n0.Main menu\n");
            if (scan.hasNextLine()) {
                i = scan.nextLine();
            }
            if (Objects.equals(i, "6"))
                break;
            switch (Objects.requireNonNull(i)) {
                case "1" -> rentEquipmentMenu(m);
                case "2" -> returnEquipmentMenu(m);
                case "3" -> {
                    c.quitClub(m);
                    break;
                }
                case "4" -> useRoomMenu(m);
                case "5" -> bookRoomMenu(m);
                case "0" -> mainMenu();
                default -> System.out.print("Menu not found\n\n");
            }
        }
    }

    public Room searchRoom() {
        c.showRooms();
        String num = InputData.scanString("Enter room ID:");
        Room r = c.getRoom(num);
        if (r == null) {
            System.out.println("Can't find room " + num);
        }
        return r;
    }

    public void useRoomMenu(Member m) {
        if (m.alreadyUsingRoom()) {
            System.out.println(m.getName() + " already using room " + m.getUseRoom().getId());
            String choice = InputData.scanYN("Do you want to leave?(y/n)");
            if (Objects.equals(choice, "y") || Objects.equals(choice, "Y")) {
                m.useRoom(m.getUseRoom());
            } else return;
        }
        Room r = searchRoom();
        if (r != null) {
            if (r.getState() == r.getRoomUsing()) {
                System.out.println(r);
                return;
            }
            m.useRoom(r);
        }
    }


    public void bookRoomMenu(Member m) {
        Room r = searchRoom();
        if (r != null) {
            String time = InputData.scanTime("Enter time you want to book.");
            int hours = InputData.scanHour("How many hours do you want to book:");
            int minutes = InputData.scanMinute("Minutes:");
            m.bookRoom(r, time, hours, minutes);
        }
    }

    public void rentEquipmentMenu(Member m) {
        if (m.alreadyRent()) {
            System.out.println("Please return " + m.getRentEquipment().getName() + " first!");
            return;
        }
        c.showEquipments();
        System.out.println("What equipment do you want to rent?");
        String name = InputData.scanString("Enter equipment:");
        Equipment e = c.getEquipment(name);
        if (e == null) {
            System.out.println("Find no equipment " + name);
            return;
        }
        System.out.println(e);
        int num = InputData.scanInt("How many " + name + " do you want to rent:");
        m.rent(e, num);
    }

    public void returnEquipmentMenu(Member m) {
        if (!m.alreadyRent()) {
            System.out.println(m.getName() + " didn't rent anything.");
            return;
        }
        m.returnRent();
    }


    public void equipmentMenu() {
        String i = null;
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("1.List equipment\n2.Add equipment\n3.Delete equipment\n4.Back\n");
            if (scan.hasNextLine()) {
                i = scan.nextLine();
            }
            if (Objects.equals(i, "4"))
                break;
            switch (Objects.requireNonNull(i)) {
                case "1" -> c.showEquipments();
                case "2" -> addEquipment();
                case "3" -> deleteEquipment();
                default -> System.out.print("Menu not found\n\n");
            }
        }
    }

    public void addEquipment() {
        String name = InputData.scanString("Enter equipment:");
        int num = InputData.scanInt("Enter quantity");
        Equipment e = new Equipment(name, num);
        c.addEquipment(e);
    }

    public void deleteEquipment() {
        String name = InputData.scanString("What equipment do you want to delete:");
        Equipment e = c.getEquipment(name);
        if (e == null) {
            System.out.println("Can't find " + name);
            return;
        }
        c.deleteEquipment(e);
        System.out.println(name + " has removed");
    }

    public void roomMenu() {
        String i = null;
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("Room Menu\n1.List Room\n2.Add Room\n3.Delete Room\n4.back\n");
            if (scan.hasNextLine()) {
                i = scan.nextLine();
            }
            if (Objects.equals(i, "4"))
                break;
            switch (Objects.requireNonNull(i)) {
                case "1" -> c.showRooms();
                case "2" -> addRoom();
                case "3" -> deleteRoom();
                default -> System.out.print("Menu not found\n\n");
            }
        }
    }

    public void addRoom() {
        String name = InputData.scanString("Enter room type:");
        int num = InputData.scanInt("Enter ID");
        Room r = new Room(name, num);
        c.addRoom(r);
    }

    public void deleteRoom() {
        String id = InputData.scanString("Enter room ID you want to delete:");
        Room r = c.getRoom(id);
        if (r == null) {
            System.out.println("Can't find room " + id);
            return;
        }
        c.deleteRoom(r);
        System.out.println("Room " + id + " has removed");
    }

    public void exit() throws IOException {
        saveData();
        System.exit(0);
    }

    public void saveData() throws IOException {
        List<String[]> memberData;
        List<String[]> equipmentData;
        List<String[]> roomData;
        memberData = c.toMemberData();
        equipmentData = c.toEquipmentData();
        roomData = c.toRoomData();
        CSV.write("member.csv", memberData);
        CSV.write("equipment.csv", equipmentData);
        CSV.write("room.csv", roomData);
    }
}