package com.leynmaster.advent.aoc2023.day20;

import java.util.*;

public class ModuleConfiguration {
    private static final String BUTTON_MODULE = "button";
    private static final String BROADCASTER_MODULE = "broadcaster";
    private final Map<String, Module> moduleMap = new HashMap<>();
    private final Map<String, Long> highMap = new HashMap<>();

    public void reset() {
        moduleMap.values().forEach(Module::reset);
        highMap.clear();
    }

    public ButtonResult pushButton(long iteration) {
        long highPulses = 0L;
        long lowPulses = 0L;
        LinkedList<PulseMessage> queue = new LinkedList<>();
        queue.addLast(new PulseMessage(new BroadcastModule(BUTTON_MODULE),
                moduleMap.get(BROADCASTER_MODULE), Pulse.LOW));
        while (!queue.isEmpty()) {
            PulseMessage message = queue.removeFirst();
            Pulse pulse = message.pulse();
            if (pulse == Pulse.HIGH) {
                highPulses++;
            } else {
                lowPulses++;
            }
            String targetName = message.target().getName();
            processPulse(message.source().getName(), pulse, iteration);
            Module target = moduleMap.get(targetName);
            if (target != null) {
                queue.addAll(target.nextPulses(message));
            }
        }
        return new ButtonResult(highPulses, lowPulses);
    }

    public Map<String, Long> highStatuses(String... sources) {
        Map<String, Long> status = new HashMap<>();
        for (String source : sources) {
            Long value = highMap.get(source);
            if (value != null) {
                status.put(source, value);
            }
        }
        return status.size() == sources.length ? status : Collections.emptyMap();
    }

    public void setup(List<String[]> lines) {
        Map<String, String[]> connectionMap = new LinkedHashMap<>();
        for (String[] line : lines) {
            connectionMap.put(createModule(line[0]), line[1].split("\\s*,\\s*"));
        }
        for (Map.Entry<String, String[]> entry : connectionMap.entrySet()) {
            String sourceName = entry.getKey();
            Module source = moduleMap.get(sourceName);
            for (String targetStr : entry.getValue()) {
                Module target = moduleMap.getOrDefault(targetStr, new BroadcastModule(targetStr));
                source.addOutput(target);
                target.addInput(source);
            }
        }
    }

    private String createModule(String label) {
        String name = label;
        char type = label.charAt(0);
        if (type == '%') {
            name = label.substring(1);
            moduleMap.put(name, new FlipFlopModule(name));
        } else if (type == '&') {
            name = label.substring(1);
            moduleMap.put(name, new ConjunctionModule(name));
        } else if (BROADCASTER_MODULE.equals(label)) {
            moduleMap.put(label, new BroadcastModule(label));
        }
        return name;
    }

    private void processPulse(String source, Pulse pulse, long iteration) {
        if (!highMap.containsKey(source) && pulse == Pulse.HIGH) {
            highMap.put(source, iteration + 1);
        }
    }
}
