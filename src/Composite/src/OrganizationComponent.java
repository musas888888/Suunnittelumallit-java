// Base abstract class for both Department (composite) and Employee (leaf)
// Defines the common interface so we can treat them uniformly.
abstract class OrganizationComponent {
    protected String name;

    public OrganizationComponent(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    // Default add/remove methods – only Departments override them.
    public void add(OrganizationComponent c) {
        throw new UnsupportedOperationException("add not supported for this component");
    }

    public void remove(OrganizationComponent c) {
        throw new UnsupportedOperationException("remove not supported for this component");
    }

    // Every component must be able to return its total salary
    public abstract double getTotalSalary();

    // Every component must be able to print itself in XML
    public abstract String toXml(int indent);

    // Helper method for indentation in XML
    protected String indent(int n) {
        return "  ".repeat(n); // n times 2 spaces
    }
}
