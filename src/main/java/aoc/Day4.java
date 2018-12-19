package aoc;

import com.google.common.collect.ComparisonChain;

import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;
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

        NavigableSet<Event> eventsInOrder = new TreeSet<>();

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
            eventsInOrder.add(event);
        }

        // TODO fill in sleepies
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
