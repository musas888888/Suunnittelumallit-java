// Demonstrates the Organization Structure using the Composite pattern
public class Main {
    public static void main(String[] args) {
        // Root of the organization
        Department company = new Department("MyCompany");

        // Top-level departments
        Department engineering = new Department("Engineering");
        Department sales = new Department("Sales");

        // Employees
        Employee alice = new Employee("Alice", 4000);
        Employee bob   = new Employee("Bob",   3500);
        Employee eva   = new Employee("Eva",   3000);

        // Build structure
        engineering.add(alice);
        engineering.add(bob);
        sales.add(eva);

        company.add(engineering);
        company.add(sales);

        // Print total salary
        System.out.println("Total salary = " + company.getTotalSalary());

        // Remove employee Bob
        engineering.remove(bob);
        System.out.println("Total salary after removing Bob = " + company.getTotalSalary());

        // Print XML of full organization
        System.out.println("\n=== Organization XML ===");
        System.out.println(company.toXml(0));
    }
}
