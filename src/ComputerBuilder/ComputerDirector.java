package ComputerBuilder;

public class ComputerDirector {
    public Computer constructComputer(ComputerBuilder builder) {
        builder.buildProcessor();
        builder.buildRAM();
        builder.buildHardDrive();
        builder.buildGraphicsCard();
        builder.buildOperatingSystem();
        return builder.getResult();
    }
}

