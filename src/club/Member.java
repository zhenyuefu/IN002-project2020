package club;

import club.room.Room;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Member {
    private final String name;
    private final LocalDate birthday;
    private Equipment rentEquipment;
    private int rentNum;
    private Room usingRoom;
    private Room bookRoom;
    private Pair<LocalTime,LocalTime> bookTime;

    public Member(String name, String birthday) {
        this.name = name;
        this.birthday = LocalDate.parse(birthday);
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public Equipment getRentEquipment() {return rentEquipment;}
    public void setRentEquipment(Equipment e) {
        rentEquipment = e;
    }

    public void setRentNum(int n) {
        rentNum = n;
    }

    public Room getUseRoom() {
        return usingRoom;
    }
    public boolean alreadyRent(){
        return rentEquipment != null;
    }

    public boolean alreadyUsingRoom() {
        return usingRoom!=null;
    }
    public void rent(Equipment e, int number) {
        if (alreadyRent()){
            System.out.println("Please return "+rentEquipment.getName()+" first!");
            return;
        }
        if (e.loan(number)) {
            rentEquipment = e;
            rentNum = number;
            System.out.println(name + " rent " + number + ' ' + e.getName());
        }
    }

    public void returnRent(){
        if (alreadyRent()){
            rentEquipment.returnRent(rentNum);
        }
        System.out.print("Return "+rentNum+" "+rentEquipment.getName());
        rentEquipment =null;
        rentNum=0;
        System.out.println(" Success!");
    }

    public void bookRoom(Room r, String time,int hours,int minutes) {
        LocalTime timeBook = LocalTime.parse(time);
        bookTime = r.getState().bookRoom(timeBook,hours,minutes);
        bookRoom=r;

    }

    public void useRoom(Room r){
        r.getState().handle();
        if (r.getState() == r.getRoomUsing()){
            usingRoom = r;
        }else usingRoom = null;
    }

    public String toString() {
        return "Name:" + name + " Birthday:" + birthday.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String[] toData() {
        String r = "";
        String num = "";
        String uRoom = "";
        String bRoom = "";
        String bTime = "";
        if (rentEquipment != null) {
            r = rentEquipment.getName();
            num = String.valueOf(rentNum);
        }
        if (usingRoom != null){
            uRoom = String.valueOf(usingRoom.getId());
        }
        if (bookRoom != null){
            bRoom = String.valueOf(bookRoom.getId());
            bTime = Room.bookTimeToString(bookTime);
        }
        return new String[]{name, birthday.format(DateTimeFormatter.ISO_LOCAL_DATE), r, num,uRoom,bRoom,bTime};
    }
}
