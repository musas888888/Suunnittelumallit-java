package ComputerBuilder;

public class GamingComputerBuilder implements ComputerBuilder {
    private final Computer computer = new Computer();

    @Override
    public void buildProcessor() {
        computer.setProcessor("AMD Ryzen 7 7800X3D");
    }

    @Override
    public void buildRAM() {
        computer.setRamGB(32);
    }

    @Override
    public void buildHardDrive() {
        computer.setHardDrive("2 TB NVMe SSD");
    }

    @Override
    public void buildGraphicsCard() {
        computer.setGraphicsCard("NVIDIA GeForce RTX 4080");
    }

    @Override
    public void buildOperatingSystem() {
        computer.setOperatingSystem("Windows 11 Pro");
    }

    @Override
    public Computer getResult() {
        return computer;
    }
}

