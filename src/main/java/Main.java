import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    static List<Security> securityList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        List<List<Event>> testList = readInputFile();
        for (List<Event> eventList : testList) {
            int countSecurityNow = 0;
            int noLowIntervalStartTime = 0;
            boolean flag = true;
            boolean noLowIntervalStart =false;
            List<Security> noLowIntervalList = new ArrayList<>();
            for (int i = 0; i < eventList.size() - 1; i++) {
                Event event = eventList.get(i);
                if (event.getType() == -1) {
                    countSecurityNow++;
                } else {
                    countSecurityNow--;
                }
                if (event.getTime() != eventList.get(i + 1).getTime()) {
                    if (countSecurityNow == 0) {
                        flag = false;
                    }
                    if ((countSecurityNow > 1) && !noLowIntervalStart) {
                        noLowIntervalStart = true;
                        noLowIntervalStartTime = event.getTime();
                    }
                    if ((countSecurityNow == 1) && noLowIntervalStart) {
                        noLowIntervalList.add(new Security(noLowIntervalStartTime, event.getTime()));
                        noLowIntervalStart = false;
                    }
                }
            }
            if ((eventList.get(eventList.size() - 1).getTime() < 10000) || (eventList.get(0).getTime() != 1)) {
                flag = false;
            }
            for (Security noLowInterval : noLowIntervalList) {
                for (Security security : securityList) {
                    if (noLowInterval.contains(security)) {
                        flag = false;
                    }
                }
            }
            if (flag) {
                System.out.println("Accepted");
            } else {
                System.out.println("Wrong Answer");
            }
        }
    }

    static List<List<Event>> readInputFile() throws IOException {
        List<List<Event>> testList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        int countTest = Integer.parseInt(reader.readLine());
        for (int i = 0; i < countTest; i++) {
            List<Event> eventList = new ArrayList<>();
            String[] str = reader.readLine().split(" ");
            int countSecurity = Integer.parseInt(str[0]);
            int index = -1;
            for (int j = 0; j < countSecurity; j++) {
                index = index + 2;
                eventList.add(new Event(Integer.parseInt(str[index]) + 1, -1, j));
                eventList.add(new Event(Integer.parseInt(str[index + 1]), 1, j));
                securityList.add(new Security(Integer.parseInt(str[index]) + 1, Integer.parseInt(str[index + 1])));
            }
            Collections.sort(eventList, Comparator.comparing(Event::getTime).thenComparing(Event::getType));
            testList.add(eventList);
        }
        return testList;
    }
}

class Security {
    private int timeStart;
    private int timeEnd;

    public Security(int timeStart, int timeEnd) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public int getTimeStart() {
        return timeStart;
    }

    public int getTimeEnd() {
        return timeEnd;
    }

    public boolean contains(Security security) {
        if ((timeStart <= security.getTimeStart()) && (timeEnd >= security.getTimeEnd())){
            return true;
        } else {
            return false;
        }
    }
}

class Event {
    private int time;
    private int type;
    private int index;

    public Event(int time, int type, int index) {
        this.time = time;
        this.type = type;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public int getTime() {
        return time;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Event{" +
                "time=" + time +
                ", type=" + type +
                '}';
    }
}