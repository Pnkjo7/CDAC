package application;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class FXMLDocumentController implements Initializable {
	
	@FXML VBox aaa;
	
    @FXML private TableColumn<Lab, String> cod;
   
    @FXML private Button btn;

	
	
    @FXML
    private Label label;
    @FXML private TextField filterField;
    @FXML private TableView<Lab> tableview;
    @FXML private TableColumn<Lab, String> testCode;
    @FXML private TableColumn<Lab, String> testName;
    @FXML private TableColumn<Lab, String> lab;
    @FXML private TableView<Lab> tableviewangio;
    @FXML private TableColumn<Lab, String> testCode1;
    @FXML private TableColumn<Lab, String> testName1;
    @FXML private TableColumn<Lab, String> lab1;
    @FXML private TableColumn<Lab, String> testCode2;
    @FXML private TableColumn<Lab, String> testName2;
    @FXML private TableColumn<Lab, String> lab2;
    
    @FXML private TableView<Lab> tableviewselectedlist;

    

    
    
    private final ObservableList<Lab> dat = FXCollections.observableArrayList();

    
    //Observable list to store data
    private final ObservableList<Lab> dataList = FXCollections.observableArrayList();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
                               
    	//cod.setCellValueFactory(new PropertyValueFactory<>("cod"));       

    	
    	testCode.setCellValueFactory(new PropertyValueFactory<>("testCode"));       
    	testName.setCellValueFactory(new PropertyValueFactory<>("testName"));        
    	lab.setCellValueFactory(new PropertyValueFactory<>("lab"));        
    	
    	testCode1.setCellValueFactory(new PropertyValueFactory<>("testCode"));       
    	testName1.setCellValueFactory(new PropertyValueFactory<>("testName"));        
    	lab1.setCellValueFactory(new PropertyValueFactory<>("lab"));        
           
    	testCode2.setCellValueFactory(new PropertyValueFactory<>("testCode"));       
    	testName2.setCellValueFactory(new PropertyValueFactory<>("testName"));        
    	lab2.setCellValueFactory(new PropertyValueFactory<>("lab"));    
    
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
		
		// Add data in Table Angio Lab.
		tableviewangio.setItems(dataList);   
		
    }
    
    /// select data from table onClick
    @FXML
	private void displaySelected(MouseEvent event) {
		Lab person = tableview.getSelectionModel().getSelectedItem();
		if(person == null) {
			filterField.setText(" ");
		}else
		{
			String code = person.getTestCode();
			String name=person.getTestName();
			String lab=person.getLab();
			filterField.setText(name);
		}
	}
    
    
    // Add data from one table to another table
    @FXML
    void handleButtonAction(ActionEvent event) {
    	
    	
    	Lab person = tableview.getSelectionModel().getSelectedItem();
    	String bcode = person.getTestCode();
    	String bname=person.getTestName();
    	String blab=person.getLab();
    	
    	//////////////////
    	
    	filterField.setText("");
    	
    	dat.add(new Lab(bcode,bname,blab));
    
    	tableviewselectedlist.setItems(dat);
    	
    	
    	
    }
    
    /// remove Data from Selected table
    @FXML
    private void removeData(ActionEvent event) {
    	
    	tableviewselectedlist.getItems().removeAll(tableviewselectedlist.getSelectionModel().getSelectedItems());
    }
    
    
    /// Save Selected data in File
    @FXML
    private void buttonSave(ActionEvent event) {
    	Lab lab = new Lab();
    	
    	List<List<String>> arrList = new ArrayList<>();
    	
    	
    	for(int i=0;i<tableviewselectedlist.getItems().size();i++) {
    		lab=tableviewselectedlist.getItems().get(i);
    		arrList.add(new ArrayList<>());
    		arrList.get(i).add(lab.getTestCode());
    		arrList.get(i).add(""+lab.getTestName());
    		arrList.get(i).add(""+lab.getLab());

    	}
    	for(int i=0;i<arrList.size();i++) {
    		for(int j=0;j<arrList.get(i).size();j++){
    			System.out.print(arrList.get(i).get(j));
    		}
    	}
    	
    
    }
}
