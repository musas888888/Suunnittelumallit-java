package ComputerBuilder;

public class Main {
    public static void main(String[] args) {
        ComputerDirector director = new ComputerDirector();

        ComputerBuilder gamingBuilder = new GamingComputerBuilder();
        Computer gamingPC = director.constructComputer(gamingBuilder);
        System.out.println("=== Gaming PC ===");
        System.out.println(gamingPC);

        ComputerBuilder officeBuilder = new OfficeComputerBuilder();
        Computer officePC = director.constructComputer(officeBuilder);
        System.out.println("=== Office PC ===");
        System.out.println(officePC);
    }
}

