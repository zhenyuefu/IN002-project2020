package club.room;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalTime;

public class RoomUsing implements RoomState {
    private final Room room;

    public RoomUsing(Room room) {
        this.room = room;
    }

    public void handle() {
        System.out.println("Room" + room.getId() + " is free now!");
        room.setState(room.getRoomFree());
    }

    /*
     * wait to modify
     */
    public Pair<LocalTime,LocalTime> bookRoom(LocalTime bookStartTime, int hours, int minutes) {
       System.out.println("Booked Failed! This room is on Using.");
       return null;
    }
    @Override
    public String toString() {
        return " is on using.";
    }
}
