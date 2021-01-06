package club.room;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 */
public class Room {
    private final String type;
    private final int id;
    /* all state */
    private final RoomState roomFree;
    private final RoomState roomUsing;
    private RoomState state;
    private final ArrayList<Pair<LocalTime, LocalTime>> bookedTimeList = new ArrayList<>();

    /**
     * @param type type of the room
     * @param id   room number
     */
    public Room(String type, int id) {
        roomFree = new RoomFree(this);
        roomUsing = new RoomUsing(this);
        this.type = type;
        /* default state free */
        state = getRoomFree();
        this.id = id;

    }

    public RoomState getRoomFree() {
        return roomFree;
    }

    public RoomState getRoomUsing() {
        return roomUsing;
    }

    public int getId() {
        return id;
    }

    public void setState(RoomState state) {
        this.state = state;
    }

    public RoomState getState() {
        return state;
    }

    public ArrayList<Pair<LocalTime, LocalTime>> getBookedTimeList() {
        return bookedTimeList;
    }

    public void addBookedTime(Pair<LocalTime, LocalTime> bookTime) {
        bookedTimeList.add(bookTime);
    }

    public static Pair<LocalTime, LocalTime> strToBookedTime(String s) {
        String[] time = s.split("-");
        LocalTime startTime = LocalTime.parse(time[0]);
        LocalTime endTime = LocalTime.parse(time[1]);
        return Pair.of(startTime, endTime);
    }

    public void bookFailed() {
        System.out.println("Booked Failed. " + type + " " + id + " is already booked in this time.");
    }


    @Override
    public String toString() {
        return "Room " + id + " " + type + state;
    }

    public static String bookTimeToString(Pair<LocalTime, LocalTime> bookTime) {
        return bookTime.getKey().toString() + "-" + bookTime.getValue().toString();
    }

    public String[] toData() {
        String strState = "F";
        if (state == roomUsing) {
            strState = "U";
        }
        ArrayList<String> data = new ArrayList<>();
        data.add(type);
        data.add(String.valueOf(id));
        data.add(strState);
        for (Pair<LocalTime, LocalTime> bookTime : bookedTimeList) {
            String bookTimeStr = bookTimeToString(bookTime);
            data.add(bookTimeStr);
        }
        int size = data.size();
        return data.toArray(new String[size]);
    }
}
