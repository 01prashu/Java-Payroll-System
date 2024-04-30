
import java.sql.PreparedStatement;
import java.util.ArrayList;
import  java.sql.DriverManager;
import java.sql.Connection;

abstract class Employee{
    private String Emp_Name;
    private int Emp_Id;

    public Employee(String name , int id)
    {
        this.Emp_Id = id;
        this.Emp_Name = name;
    }
    // Encapsulation 
    public String get_emp_name()
    {
        return this.Emp_Name;
    }
    public int get_id(){
        return this.Emp_Id;
    }

    public void set_emp_name(String name)
    {
        this.Emp_Name = name;
    }
    public void set_empid(int id)
    {
        this.Emp_Id = id;
    }
    public abstract double calculatesalary();

    @Override
    public String toString()
    {
        return "Employee[name="+this.Emp_Name+",id="+this.Emp_Id+",salary="+calculatesalary()+"]";
    }
}

class FullTimeEmployee extends Employee {
    private int monthlysalary;
    FullTimeEmployee(String name , int id , int salary)
    {
        super(name, id);
        this.monthlysalary = salary;
    }
    @Override
    public double calculatesalary()
    {
        return monthlysalary;
    }
    int get_salary(){return this.monthlysalary;}
}

class PartTimeEmployee extends Employee{
    private double hoursRate;
    private int hoursWorked;
    PartTimeEmployee(String name , int id ,double hoursRate ,int hoursWorked )
    {
        super(name, id);
        this.hoursRate = hoursRate;
        this.hoursWorked = hoursWorked;
    }
    @Override
    public double calculatesalary()
    {
        return (this.hoursRate*this.hoursWorked);
    }
    public  int get_hours_work(){return  (this.hoursWorked);}
}


class PayrollSystem{
    private ArrayList<Employee> employeeList;
    public PayrollSystem()
    {
        employeeList = new ArrayList<>();
    }
    public void AddEmployee(Employee emp)
    {
        employeeList.add(emp);
    }
    public void RemoveEmployee(int id)
    {
        Employee employee_to_remove= null;
        for(Employee emp:employeeList)
        {
           
            if(emp.get_id() == id)
            {
                employee_to_remove = emp;
                break;
            }
        }
        if(employee_to_remove != null)
        {
            employeeList.remove(employee_to_remove);
        }
    }
    public void Display_Employee()
    {
        for(Employee emp:employeeList)
        {
            System.out.println(emp);
        }
    }
}

public class Main {
    static final String name="root";
    static final String password="prashant@123";
    static final String url="jdbc:mysql://127.0.0.1:3306/employee";

    public static void main(String[]arg)
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url,name,password);

            PayrollSystem payrollsystem = new PayrollSystem();
            PartTimeEmployee emp1 = new PartTimeEmployee("Karan", 1, 100, 34);
            FullTimeEmployee emp2 = new FullTimeEmployee("Rohit",1,13000);
            //            PartTimeEmployee emp2 = new PartTimeEmployee("Akash", 2, 150, 44);
//            FullTimeEmployee emp3 = new FullTimeEmployee("Vikas", 3, 42000);
//            FullTimeEmployee emp4 = new FullTimeEmployee("Vinit", 3, 18000);
            String Query ="INSERT INTO fulltimeemployee VALUES(?,?,?)";
            PreparedStatement preparedstatement = connection.prepareStatement(Query);
            preparedstatement.setString(2,emp2.get_emp_name());
            preparedstatement.setInt(1,emp2.get_id());
            preparedstatement.setInt(3,emp2.get_salary());


            int row_change = preparedstatement.executeUpdate();
            if(row_change>0)
            {
                System.out.println("Data updated");
            }
            payrollsystem.AddEmployee(emp1);
//            payrollsystem.AddEmployee(emp2);
//            payrollsystem.AddEmployee(emp3);
//            payrollsystem.AddEmployee(emp4);

//
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}