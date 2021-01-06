package club.room;

import club.Club;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalTime;
import java.util.ArrayList;

public class RoomFree implements RoomState{
    private final Room room;

    public RoomFree(Room room) {
        this.room = room;
    }

    public void handle(){
        System.out.println("You can use room "+room.getId()+ " now.");
        room.setState(room.getRoomUsing());
    }
    public Pair<LocalTime,LocalTime> bookRoom(LocalTime bookStartTime, int hours, int minutes) {
        LocalTime currentTime =  LocalTime.now();
        if (bookStartTime.isBefore(currentTime)){
            System.out.println("You must enter time after now.");
            return null;
        }
        if (bookStartTime.isBefore(Club.openTime)){
            System.out.println("Club don't open before "+Club.openTime);
            return null;
        }
        LocalTime bookEndTime = bookStartTime.plusHours(hours);
        bookEndTime= bookEndTime.plusMinutes(minutes);
        if (bookEndTime.isAfter(Club.closeTime)){
            System.out.println("Club closed at "+Club.closeTime);
            return null;
        }
        ArrayList<Pair<LocalTime,LocalTime>> bookedTimeList= room.getBookedTimeList();
        for (Pair<LocalTime, LocalTime> bookedTime : bookedTimeList){
            LocalTime start=bookedTime.getKey();
            LocalTime end = bookedTime.getValue();
            if (bookStartTime.isBefore(end) && bookStartTime.isAfter(start)){
                room.bookFailed();
                return null;
            }
            if (bookEndTime.isBefore(end) && bookEndTime.isAfter(start)){
                room.bookFailed();
                return null;
            }
        }
        Pair<LocalTime,LocalTime> t = Pair.of(Pair.of(bookStartTime,bookEndTime));
        room.addBookedTime(t);
        System.out.println("You booked Room "+room.getId()+" success.");
        return t;
    }

    @Override
    public String toString() {
        return " is Free.";
    }
}
