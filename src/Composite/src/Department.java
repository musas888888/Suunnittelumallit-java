import java.util.ArrayList;
import java.util.List;

// Composite class – can contain Employees and other Departments
class Department extends OrganizationComponent {
    private List<OrganizationComponent> children = new ArrayList<>();

    public Department(String name) {
        super(name);
    }

    // Add child (Employee or Department)
    @Override
    public void add(OrganizationComponent c) {
        children.add(c);
    }

    // Remove child (Employee or Department)
    @Override
    public void remove(OrganizationComponent c) {
        children.remove(c);
    }

    // Sum up salaries of all children
    @Override
    public double getTotalSalary() {
        double sum = 0;
        for (OrganizationComponent c : children) {
            sum += c.getTotalSalary();
        }
        return sum;
    }

    // Print department and its children in XML format
    @Override
    public String toXml(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent(indent))
                .append("<department name=\"")
                .append(name)
                .append("\">\n");

        for (OrganizationComponent c : children) {
            sb.append(c.toXml(indent + 1)); // recursively print children
        }

        sb.append(indent(indent))
                .append("</department>\n");
        return sb.toString();
    }
}
