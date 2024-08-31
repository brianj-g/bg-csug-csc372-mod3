/*
 * 
 * Module 3 CTA Option 1: Creating a User Interface with JavaFX
 * Brian Gunther
 * CSC372: Programming II
 * Colorado State University Global
 * Dr. Vanessa Cooper
 * September 1, 2024
 * 
 */


import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;
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
	private boolean colorChanged = false;
	private Label colorStatusMessage = null;
	private MenuItem mi1 = null;
	private MenuItem mi2 = null;
	private MenuItem mi3 = null;
	private MenuItem mi4 = null;
	
	// Initialize stream and writer for printing formatted text in alerts
	private StringWriter alertStream = null;
	private PrintWriter alertWriter = null;
	
	/**
	 * Javafx app initialization
	 */
	@Override
	public void start(Stage menuStage) {
		Scene menuScene = null;
		HBox dateTimePane = null;
		VBox buttonPane = null;
		Menu menu = null;
		MenuBar topBar = null;
		
		// Configure menu
		menu = new Menu("Menu");
		topBar = new MenuBar();
		topBar.getMenus().add(menu);
		
		// Configure menu items and ActionEvent listeners
		mi1 = new MenuItem("Show Date/Time");
		mi1.setOnAction((ActionEvent e) -> showDateTime());
		menu.getItems().add(mi1);
		
		mi2 = new MenuItem("Log to File");
		mi2.setOnAction((ActionEvent e) -> {
			try {
				writeLog();
			} catch (IOException ex1) {
				System.out.println("Could not open file");
			}
		});
		menu.getItems().add(mi2);
		
		mi3 = new MenuItem("Change Background Color");
		mi3.setOnAction((ActionEvent e) -> changeBColor());
		menu.getItems().add(mi3);

		mi4 = new MenuItem("Exit");
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
		buttonPane = new VBox();
		buttonPane.setPadding(new Insets(0, 0, 50, 0));
		buttonPane.setAlignment(javafx.geometry.Pos.CENTER);
		buttonPane.getChildren().add(clearButton);
		
		// Configure a status message when the color is set
		colorStatusMessage = new Label();
		colorStatusMessage.setPadding(new Insets(10, 10, 10, 10));
		buttonPane.getChildren().add(colorStatusMessage);
		
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
		outputStream = new FileOutputStream(outputFile, true);
		outputWriter = new PrintWriter(outputStream);
		alertStream = new StringWriter();
		alertWriter = new PrintWriter(alertStream);
		
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
			alertWriter.flush();
			alertStream.close();
		}
	}
	
	/**
	 * Set the frame's background to a random shade of Green.  The color should not change after the initial shade is set.
	 */
	public void changeBColor() {
		// Only change the color on the first time the event is triggered
		if (!this.colorChanged) {
			// Green hues seem to exist roughly between 90 and 150, so a random number in this range should work.
			// A random number is generated in the range of 0-60 and added to 120 to create the hue.
			Random r = new Random();
			int greenHue = 90 + r.nextInt(61);
			
			// The saturation and brightness can also be randomized for more variety using the Math.random() method
			Double max = 1.0;
			Double min = 0.3;
			Double s = min + (Math.random() * (max-min));
			Double b = min + (Math.random() * (max-min));
		
			// Create an hsb color (Hue / Saturation / Brightness) using the random hue.  The brightness and saturation are static.
			Color greenColor = Color.hsb(greenHue, s, b);

			innerPane.setBackground(new Background(new BackgroundFill(greenColor, null, null)));
			this.colorStatusMessage.setText("Color changed: " + greenColor.toString());
			this.colorChanged = true;
			
			/* Optionally, we can disable the menu item to make it unselectable here as well. This would be
			* a better solution for the user experience, but it goes against the assignment's instructions that
			* the color should not change when the user selects the menu item repeatedly. 
			* Therefore, this will be commented out.
			*
			* mi3.setDisable(true);
			*
			*/
		}
	}
	/**
	 * Exit the program gracefully, with a confirmation
	 */
	public void exitProgram() {
		Alert exitAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to exit?");
		exitAlert.setTitle("Exit Confirmation");
		
		// The showAndWait() method returns Optional<ButtonType> to provide standard option responses
		Optional<ButtonType> toExit = exitAlert.showAndWait();
		if (toExit.get() == ButtonType.OK) {
			Platform.exit();
		}

	}
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
