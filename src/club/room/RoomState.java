package club.room;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalTime;

public interface RoomState {
    void handle();
    Pair<LocalTime,LocalTime> bookRoom(LocalTime bookStartTime, int hours, int minutes);
}
