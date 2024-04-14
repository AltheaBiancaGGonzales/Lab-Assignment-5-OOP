import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class DepartmentDA {

    private HashMap<String, Employee> employeeMap;

    public DepartmentDA() throws FileNotFoundException {
        Scanner sc = new Scanner(new FileReader("dep.csv"));

        employeeMap = new HashMap<>();

        while(sc.hasNext()) {
            String row = sc.nextLine();
            String[] rowSpecific = new String[2];

            rowSpecific = row.split(",");

            Department dept = new Department();
            dept.setDepCode(rowSpecific[0]);
            dept.setDepName(rowSpecific[1].trim());

            readDepEmp(dept);
            dept.setEmployeeMap(employeeMap);

            Double salary = 0.00;

            for(Map.Entry<String, Employee> entryMap: dept.getEmployeeMap().entrySet()) {
                salary += entryMap.getValue().getSalary();
            }
            dept.setDepTotalSalary(salary);

            printDept(dept);
        }
        sc.close();
    }
    private void printDept(Department dept) {
        DecimalFormat df = new DecimalFormat("###,###.00");

        System.out.println("Department Code: " + dept.getDepCode());
        System.out.println("Department Name: " + dept.getDepName());
        System.out.println("Department Total Salary: " + df.format(dept.getDepTotalSalary()));

        System.out.println("---------------------Details -------------------------");
        System.out.println("EmpNo\t\tEmployee Name\t\tSalary");

        for (Map.Entry<String, Employee> entryMap : dept.getEmployeeMap().entrySet()) {
        System.out.printf("%-15s %-15s %16s%n",entryMap.getValue().getEmpNo()
                                                     ,entryMap.getValue().getLastName() + "," + entryMap.getValue().getFirstName()
                                                     ,df.format(entryMap.getValue().getSalary()));
        }

        System.out.println();
    }

    private void readDepEmp(Department dept) throws FileNotFoundException {
        Scanner sc2 = new Scanner(new FileReader("deptemp.csv"));

        employeeMap.clear();


        Integer key = 0;

        while(sc2.hasNext()) {
            String row2 = sc2.nextLine();
            String[] rowSpecific2 = new String[3];

            rowSpecific2 = row2.split(",");

            if(dept.getDepCode().equals(rowSpecific2[0])) {
                EmployeeDA employeeDA = new EmployeeDA(rowSpecific2[1].trim(), Double.parseDouble(rowSpecific2[2].trim()));

                employeeMap.put(rowSpecific2[1].trim()+key, employeeDA.getEmployee());
                key++;
            }
        }
        sc2.close();
    }
}
