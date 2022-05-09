package application;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ModifyCo {
	@FXML
	private TextField check_crNo;
	@FXML
	private TextField upd_name;
	@FXML
	private TextField upd_age;
	@FXML
	private TextField upd_gender;
	@FXML
	private TextField upd_mobileNo;
	
	@FXML
	private TableView<Lab> upd_tableview;
	@FXML
	private TableColumn<Lab, String> utestCode;
	@FXML
	private TableColumn<Lab, String> utestName;
	@FXML
	private TableColumn<Lab, String> ulab;

	
	private final ObservableList<Lab> updateData = FXCollections.observableArrayList();
	
	public void initialize(URL url, ResourceBundle rb) {
		
		check_crNo.textProperty().addListener((observable, oldValue, newValue)->{
			if(!newValue.matches("\\d*")){
				check_crNo.setText(newValue.replaceAll("[^\\d]",""));
					
		}if(newValue.length()==16) {
			check_crNo.setText(oldValue);
		}
		});
		
		
		
	}
	@FXML
	public void checkID(ActionEvent event) {
		
	
		utestCode.setCellValueFactory(new PropertyValueFactory<>("testCode"));
		utestName.setCellValueFactory(new PropertyValueFactory<>("testName"));
		ulab.setCellValueFactory(new PropertyValueFactory<>("lab"));
		String LabTest[][] = new String[500][100];
		File masterfile = new File("Investigation_data.txt");
		int loc = 0;
		try (Scanner myReader = new Scanner(masterfile)) {
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();

				StringTokenizer st = new StringTokenizer(data, "@");
				while (st.hasMoreTokens()) {
					LabTest[loc][0] = "" + st.nextToken();
					LabTest[loc][1] = "" + st.nextToken();
					LabTest[loc][2] = "" + st.nextToken();
					updateData.addAll(new Lab(LabTest[loc][0], LabTest[loc][2], LabTest[loc][1],""));

					loc++;
				}

				@SuppressWarnings("unlikely-arg-type")
				boolean s = upd_tableview.getItems().contains(check_crNo.getText());
				
				if(s)
				{
					System.out.println("Erooorr");
				}
				else
				{
					System.out.println("no Erooorr");
				}

				upd_tableview.setItems(updateData);

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	@FXML
	public void updateRecord(ActionEvent event) {
		
	}

	
}
