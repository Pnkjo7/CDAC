package application;
import javafx.beans.property.SimpleStringProperty;

public class Lab {    
    private  final SimpleStringProperty testCode;
    private  final SimpleStringProperty testName;
    private  final SimpleStringProperty lab;
    
    
    
    Lab(String ID, String TESTNAME, String LAB)
    {      
       this.testCode = new SimpleStringProperty(ID);
       this.testName = new SimpleStringProperty(TESTNAME);
       this.lab =  new SimpleStringProperty(LAB);
    }
    
     
    public String getTestCode() {
        return testCode.get();
    }

    public void setTestCode(String ID) {
        this.testCode.set(ID);
    }
    
   
  
    public String getTestName() {
        return testName.get();
    }

    public void setTestName(String TESTNAME) {
        testName.set(TESTNAME);
    }
    
    

    public String getLab() {
        return lab.get();
    }

    public void setLab(String LAB) {
        lab.set(LAB);
    }

    }