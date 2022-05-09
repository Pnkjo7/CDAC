package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
	@FXML
	private TextField txt_crNo;
	@FXML
	TextField txt_PatientName;
	@FXML
	private TextField txt_age;
	@FXML
	private ComboBox<String> txt_gender;
	@FXML
	private TextField txt_mobileNo;
	@FXML
	private DatePicker txt_visitDate;
	@FXML
	private TextField txt_Location;
	@FXML
	private ComboBox<String> txt_department;
	@FXML
	private TextField txt_diagnosis;
	@FXML
	VBox aaa;
	@FXML
	private TableColumn<Lab, String> cod;
	@FXML
	private Button modifyBtn;
	@FXML
	private Label label;
	@FXML
	private TextField filterField;
	@FXML
	private TableView<Lab> tableview;
	@FXML
	private TableColumn<Lab, String> testCode;
	@FXML
	private TableColumn<Lab, String> testName;
	@FXML
	private TableColumn<Lab, String> lab;
	@FXML
	private TableColumn<Lab, String> actionCol;
	@FXML
	private TableColumn<Lab, String> testCode2;
	@FXML
	private TableColumn<Lab, String> testName2;
	@FXML
	private TableColumn<Lab, String> lab2;
	@FXML
	private TableView<Lab> tableviewselectedlist;
	static int si=0;
	static String loc = "", Lab = "", Coll = "", Pat = "";
	private final ObservableList<Lab> dat = FXCollections.observableArrayList();
	private ObservableList<Lab> dataList;
	private ObservableList<Lab> items;
	private final ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female");
	private final ObservableList<String> department = FXCollections.observableArrayList("Emergency", "OPD", "X-RAY",
			"Cardio");

	@FXML
	private Label time;
	private volatile boolean stop = false;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		CurrentTime();
		txt_gender.setItems(gender);
		txt_department.setItems(department);

		txt_crNo.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				txt_crNo.setText(newValue.replaceAll("[^\\d]", ""));

			}
			if (newValue.length() == 16) {
				txt_crNo.setText(oldValue);
			}
		});
		txt_PatientName.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\sa-zA-Z*")) {
				txt_PatientName.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));

			}
		});

		txt_age.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				txt_age.setText(newValue.replaceAll("[^\\d]", ""));

			}
			if (newValue.length() == 3) {
				txt_age.setText(oldValue);
			}
		});

		txt_mobileNo.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				txt_mobileNo.setText(newValue.replaceAll("[^\\d]", ""));

			}
			if (newValue.length() == 11) {
				txt_mobileNo.setText(oldValue);
			}
		});
		txt_Location.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\sa-zA-Z*")) {
				txt_Location.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));

			}
		});
		txt_diagnosis.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\sa-zA-Z*")) {
				txt_diagnosis.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
			}
		});
		dataList = FXCollections.observableArrayList();
		items = FXCollections.observableArrayList();
		actionCol.setCellValueFactory(new PropertyValueFactory<Lab, String>("remark"));
//		testCode.setCellValueFactory(new PropertyValueFactory<Lab,String>("testCode"));
		testName.setCellValueFactory(new PropertyValueFactory<Lab, String>("testName"));
		lab.setCellValueFactory(new PropertyValueFactory<Lab, String>("lab"));
		testName2.setCellValueFactory(new PropertyValueFactory<>("testName"));
		lab2.setCellValueFactory(new PropertyValueFactory<>("lab"));
		tableviewselectedlist.setPlaceholder(new Label("No Test Selected"));
		String LabTest[][] = new String[500][6];
		File masterfile = new File("Lab_Master.txt");
		int loc = 0;
		try (Scanner myReader = new Scanner(masterfile)) {
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();

				StringTokenizer st = new StringTokenizer(data, "@");
				while (st.hasMoreTokens()) {
					LabTest[loc][0] = "" + st.nextToken();
					LabTest[loc][1] = "" + st.nextToken();
					LabTest[loc][3] = "" + st.nextToken();

					dataList.addAll(new Lab(" ", LabTest[loc][3], LabTest[loc][1], ""));
					loc++;
				}
			}
		} catch (Exception aa) {

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

				if (lab.getTestName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (lab.getLab().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else
					return false; // Does not match.
			});
		});
		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Lab> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tableview.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tableview.setItems(sortedData);
	}

	/*
	 * 
	 * public void preview(ActionEvent event) throws IOException{ Stage stage = new
	 * Stage(); stage.initModality(Modality.APPLICATION_MODAL);
	 * stage.initOwner((((Node) event.getSource()).getScene().getWindow())); Parent
	 * root =FXMLLoader.load(getClass().getResource("Preview.fxml")); Scene scene =
	 * new Scene(root); stage.setTitle("Preview"); stage.setScene(scene);
	 * stage.show();
	 * 
	 * }
	 */

	/// select data from table onClick
	@FXML
	private void displaySelected(MouseEvent event) {
		Lab person = tableview.getSelectionModel().getSelectedItem();
		if (person == null) {
			filterField.setText(" ");
		} else {
			String code = person.getTestCode();
			String name = person.getTestName();
			String lab = person.getLab();
			filterField.setText(name);
		}
	}

	// Add data from one table to another table
	@FXML
	void handleButtonAction(ActionEvent event) {

		tableviewselectedlist.getItems().clear();
		for (Lab lb : tableview.getItems()) {
			if (lb.getRemark().isSelected()) {

				tableviewselectedlist.getItems().add(lb);

				
				// lb.remark.setSelected(false);

			}
			/*
			 * tableviewselectedlist.setRowFactory(t -> new TableRow<Lab>() {
			 * 
			 * @Override protected void updateItem(Lab lb, boolean empty) { if (lb == null)
			 * { this.setTextFill(Color.ANTIQUEWHITE); } //System.err.println("0");} else {
			 * this.setTextFill(Color.YELLOWGREEN); //System.err.println("1"); } } });
			 */

		}

	}

/// remove Data from Selected table
	@FXML
	private void removeData(ActionEvent event) {

		
				for (Lab lb : tableviewselectedlist.getSelectionModel().getSelectedItems()) {
					if (lb.getRemark().isSelected()) {

						lb.remark.setSelected(false);
						tableviewselectedlist.getItems().removeAll(tableviewselectedlist.getSelectionModel().getSelectedItems());
					}else
					{System.out.print("NO Test");}


				}

		
		
	}

	/// Save Selected data in File
	static String Labs = "";

	@FXML
	private void buttonSave(ActionEvent event) {
		si=tableviewselectedlist.getItems().size();
		loc = txt_Location.getText();
		Pat = txt_PatientName.getText();

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YY");
		formatter.format(date);
		String str = formatter.format(date);
		Coll = str;

		StringBuilder sb = new StringBuilder();
		Random rand = new Random();
		int rand_num = rand.nextInt();

		String str_num = "1005" + rand_num;

		sb.append(str_num.toString() + "@");
		// sb.append(txt_crNo.getText().toString() + "@");
		sb.append(txt_PatientName.getText().toString() + "@");
		// sb.append(txt_age.getText().toString() + "@");
		// sb.append(txt_gender.getValue().toString() + "@");
		// sb.append(txt_mobileNo.getText().toString() + "@");
		// sb.append(txt_visitDate.getValue().toString() + "@");

		sb.append(txt_Location.getText().toString() + "@");
		// sb.append(txt_department.getValue().toString() + "@");
		// sb.append(txt_diagnosis.getText().toString() + "@");
		Lab lab = new Lab();

		List<List<String>> arrList = new ArrayList<>();

		for (int i = 0; i < tableviewselectedlist.getItems().size(); i++) {
			lab = tableviewselectedlist.getItems().get(i);
			arrList.add(new ArrayList<>());
			arrList.get(i).add(lab.getTestCode());
			arrList.get(i).add("" + lab.getTestName());
			arrList.get(i).add("" + lab.getLab());
			Labs = Labs + lab.getLab() + "@";
		}

		Lab = Labs;
	
		

		try {
			File file = new File("Investigation_data.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sb.toString());
			for (int i = 0; i < arrList.size(); i++) {
				for (int j = 0; j < arrList.get(i).size(); j++) {
					bw.write(arrList.get(i).get(j) + "@");
				}
			}
			bw.write("\n");
			bw.close();
			fw.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {

			Parent root1 = FXMLLoader.load(getClass().getResource("Preview.fxml"));

			Stage stage = new Stage();

			stage.setTitle("Preview");
			stage.setScene(new Scene(root1));
			Image I = new Image(getClass().getResourceAsStream("laboratory-test.png"));
			stage.getIcons().add(I);
			stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	public void modifyButton(ActionEvent event) {

		try {
			Parent root1 = FXMLLoader.load(getClass().getResource("Modify.fxml"));

			Stage stage = new Stage();

			stage.setTitle("Modify");
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//// Show date and time function in Pane
	private void CurrentTime() {
		Thread thread = new Thread(() -> {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
			while (!stop) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					System.out.println(e);

				}
				final String timenow = sdf.format(new Date());
				Platform.runLater(() -> {
					time.setText(timenow);
				});
			}
		});
		thread.start();
	}
}
