package club;

import club.room.Room;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Club {
    private final ArrayList<Member> memberList = new ArrayList<>();
    private final ArrayList<Equipment> equipmentList = new ArrayList<>();
    private final ArrayList<Room> roomList = new ArrayList<>();
    private final Map<String, Equipment> equipmentDict = new HashMap<>();
    private final Map<String, Member> memberDict = new HashMap<>();
    private final Map<String, Room> roomDict = new HashMap<>();
    public static final LocalTime openTime = LocalTime.of(8,0);
    public static final LocalTime closeTime = LocalTime.of(21,0);

    public Club(List<String[]> memberData, List<String[]> equipmentData, List<String[]> roomData) {
        int num;

        for (String[] equipmentString : equipmentData) {
            Equipment e = new Equipment(equipmentString[0], Integer.parseInt(equipmentString[1]));
            equipmentList.add(e);
            equipmentDict.put(equipmentString[0], e);
        }

        for (String[] roomDataString : roomData) {
            Room r = new Room(roomDataString[0], Integer.parseInt(roomDataString[1]));
            roomList.add(r);
            roomDict.put(roomDataString[1], r);
        }
        for (String[] memberString : memberData) {
            Member m = new Member(memberString[0], memberString[1]);
            String eqName = memberString[2];
            memberList.add(m);
            if (!Objects.equals(eqName, "")) {
                num = Integer.parseInt(memberString[3]);
                m.setRentEquipment(getEquipment(eqName));
                m.setRentNum(num);
            }
            String usingRoom = memberString[4];
            String bookRoom =memberString[5];
            if (!Objects.equals(usingRoom, "")){
                m.useRoom(getRoom(usingRoom));
            }
            if (!Objects.equals(bookRoom, "")){
                String bookRoomTime= memberString[6];
                getRoom(bookRoom).addBookedTime(Room.strToBookedTime(bookRoomTime));
            }
            memberDict.put(memberString[0] + memberString[1], m);
        }
    }

    public void addMember(Member m) {
        if (memberList.contains(m)) {
            System.out.println(m.getName() + " is already in this club!");
            return;
        }
        memberList.add(m);
        String k = m.getName() + m.getBirthday().format(DateTimeFormatter.ISO_LOCAL_DATE);
        memberDict.put(k, m);
    }

    public void addMember(String name, String birthday) {
        String k = name + birthday;
        if (memberDict.containsKey(k)) {
            System.out.println(name + " is already in this club!");
            return;
        }
        Member m = new Member(name, birthday);
        memberList.add(m);
        memberDict.put(k, m);
    }

    public void searchMember(String name) {
        Map<String, Member> map = memberDict.entrySet().stream().filter(r -> r.getKey().contains(name)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (map.isEmpty()) return;
        System.out.println("Maybe you want to find:");
        for (Member member : map.values()) {
            System.out.println(member);
        }
    }

    public void addEquipment(Equipment e) {
        if (equipmentList.contains(e)) {
            System.out.println("Already have " + e.getName());
            return;
        }
        equipmentList.add(e);
        equipmentDict.put(e.getName(), e);
    }

    public void addRoom(Room r){
        if (roomList.contains(r)) {
            System.out.println("Already have this room\n"+r);
            return;
        }
        roomList.add(r);
        roomDict.put(String.valueOf(r.getId()),r);
    }

    public void quitClub(Member m) {
        memberList.remove(m);
        memberDict.values().removeIf(value -> value == m);
    }

    public void deleteEquipment(Equipment e) {
        equipmentList.remove(e);
        equipmentDict.values().removeIf(value -> value == e);
    }

    public void deleteRoom(Room r) {
        roomList.remove(r);
        roomDict.values().removeIf(value -> value==r);
    }

    public Member getMember(String name, String birthday) {
        return memberDict.get(name + birthday);
    }

    public Equipment getEquipment(String name) {
        return equipmentDict.get(name);
    }

    public Room getRoom(String number) {
        return roomDict.get(number);
    }

    public List<String[]> toMemberData() {
        List<String[]> data = new ArrayList<>();
        for (Member member : memberList) {
            data.add(member.toData());
        }
        return data;
    }

    public List<String[]> toEquipmentData() {
        List<String[]> data = new ArrayList<>();
        for (Equipment equipment : equipmentList) {
            data.add(equipment.toData());
        }
        return data;
    }

    public List<String[]> toRoomData() {
        List<String[]> data = new ArrayList<>();
        for (Room room : roomList) {
            data.add(room.toData());
        }
        return data;
    }

    public void showMembers() {
        for (Member member : memberList) {
            System.out.println(member.toString());
        }
    }

    public void showEquipments() {
        for (Equipment e : equipmentList) {
            System.out.println(e);
        }
    }

    public void showRooms(){
        for (Room room : roomList) {
            System.out.println(room.toString());
        }
    }
}
