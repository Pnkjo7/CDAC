package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

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
import javafx.scene.input.KeyEvent;
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
	private TextField txt_visitDate;
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
	static int si = 0;
	static String loc = "", Lab = "", Coll = "", Pat = "";
	private final ObservableList<Lab> dat = FXCollections.observableArrayList();
	private ObservableList<Lab> dataList;
	private ObservableList<Lab> items;
	private final ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female", "Others");
	private final ObservableList<String> department = FXCollections.observableArrayList("Emergency", "OPD", "X-RAY",
			"Cardio");

	@FXML
	private Label time;
	private volatile boolean stop = false;

	static final String UNICODE_FORMAT = "UTF8";
	static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	KeySpec ks;
	SecretKeyFactory skf;
	Cipher cipher;
	byte[] arrayBytes;
	String myEncryptionKey;
	String myEncryptionScheme;
	SecretKey key;

	public FXMLDocumentController() throws Exception {
		myEncryptionKey = "Hitesh#HarshHitesh#Harsh";
		myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
		arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
		ks = new DESedeKeySpec(arrayBytes);
		skf = SecretKeyFactory.getInstance(myEncryptionScheme);
		cipher = Cipher.getInstance(myEncryptionScheme);
		key = skf.generateSecret(ks);
	}

	public void TrippleDes() throws Exception {
		myEncryptionKey = "Hitesh#HarshHitesh#Harsh";
		myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
		arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
		ks = new DESedeKeySpec(arrayBytes);
		skf = SecretKeyFactory.getInstance(myEncryptionScheme);
		cipher = Cipher.getInstance(myEncryptionScheme);
		key = skf.generateSecret(ks);
	}

	static String encryptedString = "ftvXr1XMrROIGEQseNkU2+N7STzCJWnnJ+brVaNug+ZVU3TRnyRqCybdeyxb85KsXMbirwv5IL10F9iEoXCiRH6DvICF8XfJVEvOcC9ZEFfn5ZxZW0QxDpLTwU7lMQO1+tQErVZ8CQHKohSU+4PPoC9DsjQuKiCWi7fcNzssy/Mu83z+sHSk2w9SVwUmpgi7";

	String decrypt(String encryptedString) {
		
		
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decodeBase64(encryptedString);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = new String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}

	String sCr = "";String sName = "";String sGender = "";String sAge = "";String sDepartment = "";String sMobile = "";String sHospital = "";String sDate=""; 
	

	String test1 = "";String test2 = "";String test3 = "";
	
	
	String s1 = "";
	String decrypted ="";
	String s2 = "";

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		
		String decrypted = decrypt(encryptedString);

		String FirstToken[][] = new String[500][5];
		int a = 0;

		StringTokenizer at = new StringTokenizer(decrypted, "$");

		while (at.hasMoreTokens()) {
			FirstToken[a][0] = "" + at.nextToken();
			FirstToken[a][1] = "" + at.nextToken();
			s1 = FirstToken[a][0];
			s2 = FirstToken[a][1];
			System.out.println(s1);
			System.out.println(s2);
			a++;

		}

		CurrentTime();
		txt_gender.setItems(gender);
		txt_department.setItems(department);

//		txt_crNo.textProperty().addListener((observable, oldValue, newValue) -> {
//			if (!newValue.matches("\\d*")) {
//				txt_crNo.setText(newValue.replaceAll("[^\\d]", ""));
//
//			}
//			if (newValue.length() == 16) {
//				txt_crNo.setText(oldValue);
//			}
//		});
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

	

	// on Key Pressed fill the Demography details and Selects test. 
	@SuppressWarnings({ "unlikely-arg-type", "null" })
	@FXML
	void fillDetails(ActionEvent event) {

		String datas = txt_crNo.getText();
		if(datas.equals(""))
		{
			datas = " @ @ @ @ @ @ @ @ @ @";
		}
		StringTokenizer sReg = new StringTokenizer(datas);
		
		String fields[] = new String[11];
		int r=0;
		
		while(sReg.hasMoreTokens()) {
			fields[r] = sReg.nextToken("@");
			System.err.println(fields[r]);
			r++;
		}
		
		txt_crNo.setText(fields[0]);
		txt_PatientName.setText(fields[1]);
		String gen = fields[2];
		if(gen.equals("M"))
		{
			txt_gender.setValue("Male");
			
			
		}else if(gen.equals("F"))
		{
			txt_gender.setValue("Female");			
		}
		else
		{
			txt_gender.setValue("Others");	
		}
		
		txt_age.setText(fields[3]);
		txt_mobileNo.setText(fields[5]);
		txt_Location.setText(fields[6]);
		txt_department.setValue(fields[4]);
		txt_visitDate.setText(fields[7]);
		txt_diagnosis.setText("Medicine");
		
		
		
		String thirdToken[] = new String[500];
		int j = 0;
		StringTokenizer dt = new StringTokenizer(s2, "&");

		while (dt.hasMoreTokens()) {
			thirdToken[j] =dt.nextToken();
			
			test1=thirdToken[j];
			Lab person1=new Lab();
			for(int k=0;k<dataList.size();k++)
			{ 
				String rand=dataList.get(k).getTestName();
				person1=tableview.getItems().get(k);
				if(test1.equals(rand)){
					person1.getRemark().setSelected(true);
			
			}
			j++;

			}
			
		}
		
	}

	/// select data from table onClick
	@FXML
	private void displaySelected(MouseEvent event) {
		Lab person = tableview.getSelectionModel().getSelectedItem();
		
		if(person.getRemark().isSelected())
		{
			person.getRemark().setSelected(false);
		}
		else
		{
			person.getRemark().setSelected(true);
		}


	}

	// Add data from one table to another table
	@FXML
	void handleButtonAction(ActionEvent event) {

		tableviewselectedlist.getItems().clear();
		for (Lab lb : tableview.getItems()) {
			if (lb.getRemark().isSelected()) {

				tableviewselectedlist.getItems().add(lb);


			}
			

		}

	}

/// remove Data from Selected table
	@FXML
	private void removeData(ActionEvent event) {

		for (Lab lb : tableviewselectedlist.getSelectionModel().getSelectedItems()) {
			if (lb.getRemark().isSelected()) {

				lb.remark.setSelected(false);
				tableviewselectedlist.getItems()
						.removeAll(tableviewselectedlist.getSelectionModel().getSelectedItems());
			} else {
				System.out.print("NO Test");
			}

		}

	}

	/// Save Selected data in File
	static String Labs = "";

	@FXML
	private void buttonSave(ActionEvent event) {
		si = tableviewselectedlist.getItems().size();
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
		sb.append(txt_crNo.getText().toString() + "@");
		sb.append(txt_PatientName.getText().toString() + "@");
		sb.append(txt_age.getText().toString() + "@");
		sb.append(txt_gender.getValue().toString() + "@");
		sb.append(txt_mobileNo.getText().toString() + "@");
		sb.append(txt_visitDate.getText().toString() + "@");

		sb.append(txt_Location.getText().toString() + "@");
		sb.append(txt_department.getValue().toString() + "@");
		sb.append(txt_diagnosis.getText().toString() + "@");
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
			stage.setResizable(false);
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
