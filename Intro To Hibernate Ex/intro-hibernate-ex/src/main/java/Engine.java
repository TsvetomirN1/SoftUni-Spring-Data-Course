import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.ToDoubleBiFunction;

public class Engine implements Runnable {
    private final EntityManager entityManager;
    private final BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {

        //2.	Remove Objects
        //this.removeObjectEx();

        //3.	Contains Employee
//        try {
//            this.containsEmployeeEx();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //4.	Employees with Salary Over 50 000
        //this.employeeWithSalaryOver50000();

        //5.	Employees from Department
        //this.employeesFromDepartments();

        //6.	Adding a New Address and Updating Employee
//        try {
//            this.addingNewAddressAndAddItToEmp();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //7.	Addresses with Employee Count
        //this.addressesWithEmployeeCount();

        //8.	Get Employee with Project
//        try {
//            this.getEmployeeWithProject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //9.	Find Latest 10 Projects
        //this.findLatest10Projects();

        //10.	Increase Salaries
        //this.increaseSalaries();

        //11.	Remove Towns
//        try {
//            this.removeTowns();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //12.	Find Employees by First Name
//        try {
//            this.findEmployeesByFirstName();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //13.	Employees Maximum Salaries
        //this.employeesMaximumSalaries();


    }

    private void employeesMaximumSalaries() {
        this.entityManager
                .createQuery("SELECT e FROM  Employee AS e WHERE e.salary NOT BETWEEN 30000 AND 70000 " +
                        "GROUP BY e.department " +
                        "ORDER BY e.salary DESC", Employee.class)
                .getResultList()
                .stream().sorted(Comparator.comparing(e -> e.getDepartment().getId()))
                .forEach(employee -> System.out.printf("%s - %.2f%n",
                        employee.getDepartment().getName(), employee.getSalary()));
    }

    private void findEmployeesByFirstName() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter pattern: ");
        String pattern = reader.readLine();
        this.entityManager.createQuery("SELECT e FROM Employee AS e WHERE e.firstName LIKE :pattern", Employee.class)
                .setParameter("pattern", pattern + "%")
                .getResultList()
                .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));
    }

    private void removeTowns() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter town name: ");
        String townName = reader.readLine();
        Town town = entityManager.createQuery("SELECT t FROM Town AS t WHERE t.name = :townName", Town.class)
                .setParameter("townName", townName)
                .getSingleResult();

        List<Address> addresses = entityManager.createQuery("SELECT a FROM Address AS a WHERE a.town.name = :townName", Address.class)
                .setParameter("townName", townName)
                .getResultList();

        String output = String.format("%d address%s in %s deleted",
                addresses.size(), (addresses.size() != 1) ? "es" : "", town.getName());

        entityManager.getTransaction().begin();

        addresses.forEach(address -> {
            for (Employee employee : address.getEmployees()) {
                employee.setAddress(null);
            }
            address.setTown(null);
            entityManager.remove(address);
        });
        entityManager.remove(town);
        entityManager.getTransaction().commit();
        System.out.println(output);
    }

    private void increaseSalaries() {
        entityManager.getTransaction().begin();
        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee AS e WHERE e.department.name " +
                "IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class).getResultList();
        employees.forEach(e -> {
            e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12)));
            System.out.printf("%s %s ($%.2f)%n", e.getFirstName(),
                    e.getLastName(), e.getSalary());
        });
        entityManager.getTransaction().commit();
    }

    private void findLatest10Projects() {
        this.entityManager
                .createQuery("SELECT p FROM Project AS p ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> System.out.printf("Project name: %s%n   Project Description: %s%n" +
                        "   Project Start Date: %s%n   Project End Date: %s%n", project.getName(),
                        project.getDescription(), project.getStartDate(), project.getEndDate()));
    }

    private void getEmployeeWithProject() throws IOException {
        System.out.println("Enter employee id: ");
        int id = Integer.parseInt(reader.readLine());
        Employee employee = this.entityManager.createQuery("SELECT e FROM Employee  AS e " +
                "WHERE e.id = :id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
        System.out.printf("%s %s - %s%n",employee.getFirstName(), employee.getLastName(), employee.getJobTitle());
        employee.getProjects().stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> System.out.printf("    %s%n", project.getName()));
    }

    private void addressesWithEmployeeCount() {
         this.entityManager
                .createQuery("SELECT a FROM Address AS a ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList()
                 .forEach(a -> System.out.printf("%s, %s - %d employees%n",
                a.getText(), a.getTown().getName(), a.getEmployees().size()));
    }

    private void addingNewAddressAndAddItToEmp() throws IOException {
        System.out.println("Enter employee last name: ");
        String lastName = this.reader.readLine();
        Employee employee = this.entityManager
                .createQuery("SELECT e FROM Employee AS e WHERE e.lastName = :name", Employee.class)
                .setParameter("name", lastName)
                .getSingleResult();
        Address address = this.createNewAddress("Vitoshka 15");
        this.entityManager.getTransaction().begin();
        this.entityManager.detach(employee);
        employee.setAddress(address);
        this.entityManager.merge(employee);
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

    private Address createNewAddress(String textContent) {
        Address address = new Address();
        address.setText(textContent);

        this.entityManager.getTransaction().begin();
        this.entityManager.persist(address);
        this.entityManager.getTransaction().commit();

        return address;
    }

    private void employeesFromDepartments() {
        List<Employee> employees = this.entityManager
                .createQuery("SELECT e FROM Employee AS e WHERE e.department.name = 'Research and Development' " +
                        "ORDER BY e.salary, e.id ", Employee.class)
                .getResultList();
        employees.forEach(e -> System.out.printf("%s %s from Research and Development - $%.2f%n", e.getFirstName(),
                e.getLastName(), e.getSalary()));
    }

    private void employeeWithSalaryOver50000() {
        this.entityManager
                .createQuery("SELECT e FROM Employee AS e WHERE e.salary > 50000", Employee.class)
                .getResultStream()
                .forEach(e -> System.out.printf("%s%n", e.getFirstName()));
    }

    private void containsEmployeeEx() throws IOException {
        System.out.println("Enter employee full name: ");
        String fullName = this.reader.readLine();
        try {
            Employee employee = this.entityManager.createQuery("SELECT e FROM Employee  AS e " +
                    "WHERE concat(e.firstName, ' ', e.lastName) = :name", Employee.class)
                    .setParameter("name", fullName)
                    .getSingleResult();
            System.out.println("Yes");
        } catch (NoResultException nre){
            System.out.println("No");
        }
    }

    private void removeObjectEx() {
        this.entityManager.getTransaction().begin();
        this.entityManager.createQuery("UPDATE Town SET name = LOWER(name) WHERE LENGTH(name) < 5").executeUpdate();
        this.entityManager.getTransaction().commit();

//        Solution from exercise with Chavdar Mitov
//        List<Town> towns = this.entityManager
//                .createQuery("SELECT t FROM Town AS t WHERE length(t.name) > 5", Town.class)
//                .getResultList();
//        this.entityManager.getTransaction().begin();
//        towns.forEach(this.entityManager::detach);
//        for (Town town : towns) {
//            town.setName(town.getName().toLowerCase());
//        }
//        towns.forEach(this.entityManager::merge);
//        this.entityManager.flush();
//        this.entityManager.getTransaction().commit();
    }
}
