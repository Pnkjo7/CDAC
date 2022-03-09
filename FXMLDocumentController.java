package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML private TextField filterField;
    @FXML private TableView<Lab> tableview;
    @FXML private TableColumn<Lab, String> testCode;
    @FXML private TableColumn<Lab, String> testName;
    @FXML private TableColumn<Lab, String> lab;
    @FXML private TableView<Lab> tableviewangio;
   
                  
    //Observable list to store data
    private final ObservableList<Lab> dataList = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
                               
    	testCode.setCellValueFactory(new PropertyValueFactory<>("testCode"));       
    	testName.setCellValueFactory(new PropertyValueFactory<>("testName"));        
    	lab.setCellValueFactory(new PropertyValueFactory<>("lab"));        
        
    
        String LabTest[][]=new String[500][6];
        File masterfile = new File("Lab_Master.txt");
        int loc=0;
    	try (Scanner myReader = new Scanner(masterfile)) {
    		while(myReader.hasNextLine()) {
    			String data = myReader.nextLine();
    		
    			 StringTokenizer st = new StringTokenizer( data, "@");  
    		     while (st.hasMoreTokens()) {  
    		    	 LabTest[loc][0]=""+st.nextToken(); 
    		    	 LabTest[loc][1]=""+st.nextToken();
    		    	 LabTest[loc][2]=""+st.nextToken();
    		    	 LabTest[loc][3]=""+st.nextToken();
    		       
    		         dataList.addAll(new Lab(LabTest[loc][0]+LabTest[loc][2], LabTest[loc][3], LabTest[loc][1]));
    		         loc++;
    	}		
    		}
    	}catch(Exception aa) {
    		
    	}
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Lab> filteredData = new FilteredList<>(dataList, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(lab -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (lab.getTestName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (lab.getLab().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Lab> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tableview.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tableview.setItems(sortedData);

     
    }

    
}
