package com.leynmaster.advent.aoc2023.day20;

import java.util.List;

public class BroadcastModule extends Module {

    public BroadcastModule(String name) {
        super(name);
    }

    @Override
    public List<PulseMessage> nextPulses(PulseMessage pulseMessage) {
        return sendToOutputs(pulseMessage.pulse());
    }
}
