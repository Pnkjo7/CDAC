package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class DocumentController2 {

	@FXML
	VBox hbox;

	static String loc = "", Lab = "", Coll = "", Pat = "";
	static int j;

	static int count = 0;
	String uniq="";
	
	@FXML
	public void initialize() throws IOException {

		
		loc=FXMLDocumentController.loc;
		Pat=FXMLDocumentController.Pat;
		Coll=FXMLDocumentController.Coll;

				
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMYY");
		formatter.format(date);
				
		String str = formatter.format(date);
		uniq = str + count;
		count = count + 1;

		Code128Bean code128 = new Code128Bean();
		code128.setHeight(15f);
		code128.setModuleWidth(0.3);
		code128.setQuietZone(10);
		code128.doQuietZone(true);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/x-png", 150, BufferedImage.TYPE_BYTE_BINARY,
				false, 0);

		code128.generateBarcode(canvas, uniq);
		canvas.finish();

		// ByteArrayInputStream in = new ByteArrayInputStream(baos.toByteArray());
		FileOutputStream fos = new FileOutputStream("barcode.png");
		fos.write(baos.toByteArray());
		fos.flush();
		fos.close();

		Image image = new Image(new File("barcode.png").toURI().toString());

		j = FXMLDocumentController.si;

		String a[] = new String[70];
		int k = 0;
		for (int i = 0; i < j; i++) {
			ImageView img = new ImageView(image);

			Lab = FXMLDocumentController.Lab;

			StringTokenizer bt = new StringTokenizer(Lab, "@");
			while (bt.hasMoreTokens()) {
				a[k] = bt.nextToken();
				System.out.println(a[k]);
				k++;
			}

			HBox hb1 = new HBox();
			hb1.setSpacing(5);
			HBox hb2 = new HBox();
			hb2.setSpacing(5);
			HBox hb3 = new HBox();
			hb3.setSpacing(5);
			HBox hb4 = new HBox();
			hb4.setSpacing(5);
			
			Label l1 = new Label("Location");
			l1.setStyle("-fx-font-weight: bold");
			l1.setPrefWidth(90);
			
			Label l2 = new Label("Lab Name");
			l2.setStyle("-fx-font-weight: bold");
			l2.setPrefWidth(90);
			
			Label l3 = new Label("Collection Date");
			l3.setStyle("-fx-font-weight: bold");
			l3.setPrefWidth(90);
			
			Label l4 = new Label("Patient Name");
			l4.setStyle("-fx-font-weight: bold");
			l4.setPrefWidth(90);
			
			Text t1 = new Text();
			Text t2 = new Text();
			Text t3 = new Text();
			Text t4 = new Text();

			t1.setText(loc);
			t2.setText(a[i]);
			t3.setText(Coll);
			t4.setText(Pat);
			
			hb1.getChildren().addAll(l1, t1);
			hb2.getChildren().addAll(l2, t2);
			hb3.getChildren().addAll(l3, t3);
			hb4.getChildren().addAll(l4, t4);
			
			hbox.getChildren().addAll(img, hb1, hb2, hb3, hb4);

		}
	}

}
