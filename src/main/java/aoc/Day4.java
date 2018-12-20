package aoc;

import com.google.common.collect.ComparisonChain;

import java.io.BufferedWriter;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {

    public static void main (String args[]) {
        try {
            day4_1();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void day4_1() {
        Scanner sc = Utils.getScanner("day4input.txt");

        Pattern capturePattern = Pattern.compile("\\[([0-9]+)-([0-9]+)-([0-9]+) ([0-9]+):([0-9]+)\\] (.*)");

        List<Event> events = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Matcher m = capturePattern.matcher(line);
            m.matches();
            int year = Integer.parseInt(m.group(1));
            int month = Integer.parseInt(m.group(2));
            int day = Integer.parseInt(m.group(3));
            int hour = Integer.parseInt(m.group(4));
            int minute = Integer.parseInt(m.group(5));
            String eventString = m.group(6);
            Event event = new Event(year, month, day, hour, minute, eventString);
            events.add(event);
        }

        events.sort(Event::compareTo);

        Pattern guardNumberCapture = Pattern.compile("Guard #([0-9]+).*");

        Map<Integer, Guard> guardsById = new HashMap<>();
        Guard activeGuard = null;
        int fellAlseepOn = 0;
        for (Event event : events) {
            if (event.eventString.startsWith("Guard")) {
                Matcher guardNumberMatcher = guardNumberCapture.matcher(event.eventString);
                guardNumberMatcher.matches();
                int guardNum = Integer.parseInt(guardNumberMatcher.group(1));
                activeGuard = guardsById.computeIfAbsent(guardNum, num -> new Guard(num));
            } else if (event.eventString.startsWith("falls asleep")) {
                fellAlseepOn = event.minute;
            } else if (event.eventString.startsWith("wakes up")) {
                int wokeUpOn = event.minute;
                activeGuard.totalSleepingMinutes += (wokeUpOn - fellAlseepOn);
                for (int i=fellAlseepOn; i<wokeUpOn; i++) {
                    activeGuard.sleepByMinute[i]++;
                }
            }
        }

        Guard mostAsleepGuard = guardsById.values().stream()
                .reduce((g1, g2) -> g1.totalSleepingMinutes > g2.totalSleepingMinutes ? g1 : g2)
                .get();
        int mostAsleepMinute = -1;
        int minutesAsleepOnMinute = 0;
        for (int i=0; i<60; i++) {
            int currAsleep = mostAsleepGuard.sleepByMinute[i];
            if (currAsleep > minutesAsleepOnMinute) {
                minutesAsleepOnMinute = currAsleep;
                mostAsleepMinute = i;
            }
        }

        System.out.println("#1 Guard " + mostAsleepGuard.id + " slept most on minute " + mostAsleepMinute + " -> " + (mostAsleepGuard.id * mostAsleepMinute));

        mostAsleepMinute = -1;
        minutesAsleepOnMinute = 0;
        int mostAsleepGuardId = -1;

        for (Guard g : guardsById.values()) {
            for (int i=0; i<60; i++) {
                int currAsleep = g.sleepByMinute[i];
                if (currAsleep > minutesAsleepOnMinute) {
                    minutesAsleepOnMinute = currAsleep;
                    mostAsleepMinute = i;
                    mostAsleepGuardId = g.id;
                }
            }
        }

        System.out.println("#2 Guard " + mostAsleepGuardId + " slept most on minute " + mostAsleepMinute + " -> " + (mostAsleepGuardId * mostAsleepMinute));
    }


    private static class Guard {
        int id;
        int totalSleepingMinutes;
        int[] sleepByMinute;

        private Guard(int id) {
            this.id = id;
            this.totalSleepingMinutes = 0;
            this.sleepByMinute = new int[60];
        }
    }

    private static class Event implements Comparable<Event> {
        int year, month, day, hour, minute;
        String eventString;

        private Event(int year, int month, int day, int hour, int minute, String eventString) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.hour = hour;
            this.minute = minute;
            this.eventString = eventString;
        }

        @Override
        public int compareTo(Event e) {
            return ComparisonChain.start()
                    .compare(year, e.year)
                    .compare(month, e.month)
                    .compare(day, e.day)
                    .compare(hour, e.hour)
                    .compare(minute, e.minute)
                    .result();
        }
    }

}
