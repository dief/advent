package com.leynmaster.advent.aoc2023.day20;

import java.util.Collections;
import java.util.List;

public class FlipFlopModule extends Module {
    private boolean on;

    public FlipFlopModule(String name) {
        super(name);
    }

    @Override
    public void reset() {
        on = false;
    }

    @Override
    public List<PulseMessage> nextPulses(PulseMessage message) {
        if (message.pulse() == Pulse.HIGH) {
            return Collections.emptyList();
        }
        on = !on;
        return sendToOutputs(on ? Pulse.HIGH : Pulse.LOW);
    }
}
