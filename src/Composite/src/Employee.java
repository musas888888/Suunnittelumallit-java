
package Composite.src;
// Leaf class – represents a single employee

class Employee extends OrganizationComponent {
    private double salary;

    public Employee(String name, double salary) {
        super(name);
        this.salary = salary;
    }

    @Override
    public double getTotalSalary() {
        return salary; // Just return own salary
    }

    @Override
    public String toXml(int indent) {
        return indent(indent) +
                "<employee name=\"" + name + "\" salary=\"" + salary + "\"/>\n";
    }
}
