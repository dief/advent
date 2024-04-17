package com.leynmaster.advent.aoc2023.day20;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConjunctionModule extends Module {
    private final Map<String, Pulse> lastPulseMap = new HashMap<>();

    public ConjunctionModule(String name) {
        super(name);
    }

    @Override
    public void addInput(Module module) {
        lastPulseMap.put(module.getName(), Pulse.LOW);
        super.addInput(module);
    }

    @Override
    public void reset() {
        lastPulseMap.keySet().forEach(name -> lastPulseMap.put(name, Pulse.LOW));
    }

    @Override
    public List<PulseMessage> nextPulses(PulseMessage message) {
        lastPulseMap.put(message.source().getName(), message.pulse());
        return sendToOutputs(lastPulseMap.values().stream().anyMatch(Pulse.LOW::equals) ? Pulse.HIGH : Pulse.LOW);
    }
}
