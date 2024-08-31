import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Random;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

/**
 * Creates a simple GUI frame with a menu and a text field
 */
public class MenuFrame extends Application {
	private TextField dateTimeText = null;
	private FileOutputStream outputStream = null;
	private Button clearButton = null;
	private PrintWriter outputWriter = null;
	private String outputFile = "log.txt";
	private BorderPane innerPane = null;
	private Color backgroundColor = null;
	
	@Override
	public void start(Stage menuStage) {
		Scene menuScene = null;
		HBox dateTimePane = null;
		HBox buttonPane = null;
		Menu menu = null;
		MenuBar topBar = null;
		
		// Configure menu
		menu = new Menu("Menu");
		topBar = new MenuBar();
		topBar.getMenus().add(menu);
		
		// Configure menu items and ActionEvent listeners
		MenuItem mi1 = new MenuItem("Show Date/Time");
		mi1.setOnAction((ActionEvent e) -> showDateTime());
		menu.getItems().add(mi1);
		
		MenuItem mi2 = new MenuItem("Log to File");
		mi2.setOnAction((ActionEvent e) -> {
			try {
				writeLog();
			} catch (IOException ex1) {
				System.out.println("Could not open file");
			}
		});
		menu.getItems().add(mi2);
		
		MenuItem mi3 = new MenuItem("Change Background Color");
		mi3.setOnAction((ActionEvent e) -> changeBColor());
		menu.getItems().add(mi3);
		
		MenuItem mi4 = new MenuItem("Exit");
		mi4.setOnAction((ActionEvent e) -> exitProgram());
		menu.getItems().add(mi4);
		
		// Configure the text field
		dateTimeText = new TextField();
		dateTimeText.setPrefColumnCount(20);
		dateTimeText.setEditable(false);
		dateTimeText.setPadding(new Insets(10, 10, 10, 10));
		dateTimePane = new HBox();
		dateTimePane.setPadding(new Insets(50, 50, 50, 50));
		dateTimePane.getChildren().add(dateTimeText);
		
		// Configure the clear button
		clearButton = new Button("Clear");
		clearButton.setOnAction((ActionEvent e) -> {
			this.dateTimeText.clear();
		});
		buttonPane = new HBox();
		buttonPane.setPadding(new Insets(0, 0, 50, 0));
		buttonPane.setAlignment(javafx.geometry.Pos.CENTER);
		buttonPane.getChildren().add(clearButton);
		
		
		// Configure the border pane
		innerPane = new BorderPane();
		innerPane.setTop(topBar);
		innerPane.setCenter(dateTimePane);
		innerPane.setBottom(buttonPane);
		
		// Scene and Stage configuration
		menuScene = new Scene(innerPane);
		menuStage.setTitle("Menu Frame Project");
		menuStage.setScene(menuScene);
		menuStage.show();
	}
	
	/**
	 * Retrieve the current local date and time, format it, and place it in the dateTimeText field.
	 */
	public void showDateTime() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		this.dateTimeText.setText(currentDateTime.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy h:mm a")).toString());		
	}
	
	/**
	 * Obtain the string from the text field dateTimeField and write it to a log file
	 * @throws IOException 
	 */
	public void writeLog() throws IOException {
		// Initialize the file output stream and writer
		outputStream = new FileOutputStream(outputFile);
		outputWriter = new PrintWriter(outputStream);
		
		// Initialize stream and writer for printing formatted text in alerts
		StringWriter alertStream = new StringWriter();
		PrintWriter alertWriter = new PrintWriter(alertStream);
		
		String d = this.dateTimeText.getText();
		if (d.isEmpty()) {
			// Show a warning if there was no date to print
			Alert alert = new Alert(AlertType.WARNING,"There is no output to write. Please populate the date first.");
			alert.showAndWait();
		} else {
			outputWriter.println(this.dateTimeText.getText());
			outputWriter.flush();
			outputStream.close();

			alertWriter.printf("Successfully wrote to the log file: %s", outputFile);
			Alert alert = new Alert(AlertType.INFORMATION,alertStream.toString());
			alert.showAndWait();
		}
	}
	
	/**
	 * Set the frame's background to a random shade of Green.  The color should not change after the initial shade is set.
	 */
	public void changeBColor() {
		
		// Green hues exist roughly between 120 and 180, so a random number in this range should work.
		// A random number is generated in the range of 0-60 and added to 120 to create the hue.
		Random r = new Random();
		int greenHue = 120 + r.nextInt(61);
		
		// Create an hsb color (Hue / Saturation / Brightness) using the random hue.  The brightness and saturation are static.
		Color greenColor = Color.hsb(greenHue, 1.0, 0.5);
		
		innerPane.setBackground(new Background(new BackgroundFill(greenColor, null, null)));
		
	}
	
	public void exitProgram() {
		// TODO cleanly exit
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
