package com.leynmaster.advent.aoc2023.day20;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    protected final List<Module> inputs = new ArrayList<>();
    private final List<Module> outputs = new ArrayList<>();
    private final String name;

    public Module(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addInput(Module module) {
        inputs.add(module);
    }

    public void addOutput(Module module) {
        outputs.add(module);
    }

    public abstract List<PulseMessage> nextPulses(PulseMessage pulse);

    public void reset() { }

    List<PulseMessage> sendToOutputs(Pulse pulse) {
        return outputs.stream().map(output -> new PulseMessage(this, output, pulse)).toList();
    }
}
