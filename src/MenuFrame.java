import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MenuFrame extends Application {
	@Override
	public void start(Stage menuStage) {
		
		Scene menuScene = null;
		BorderPane innerPane = null;
		HBox dateTimePane = null;
		Label dateTimeLabel = null;
		TextField dateTimeText = null;
		Menu menu = null;
		MenuBar topBar = null;
		
		menu = new Menu("Menu");
		topBar = new MenuBar();
		topBar.getMenus().add(menu);
		
		MenuItem mi1 = new MenuItem("Show Date/Time");
		menu.getItems().add(mi1);
		
		MenuItem mi2 = new MenuItem("Log to File");
		menu.getItems().add(mi2);
		
		MenuItem mi3 = new MenuItem("Change Background Color");
		menu.getItems().add(mi3);
		
		MenuItem mi4 = new MenuItem("Exit");
		menu.getItems().add(mi4);
		
		dateTimeLabel = new Label("Date/Time");
		dateTimeLabel.setPadding(new Insets(10, 10, 10, 10));
		dateTimeText = new TextField();
		dateTimeText.setEditable(false);
		dateTimeText.setPadding(new Insets(10, 10, 10, 10));
		
		dateTimePane = new HBox();
		dateTimePane.setPadding(new Insets(50, 50, 50, 50));
		dateTimePane.getChildren().add(dateTimeLabel);
		dateTimePane.getChildren().add(dateTimeText);
		
		innerPane = new BorderPane();
		innerPane.setTop(topBar);
		innerPane.setCenter(dateTimePane);
		
		menuScene = new Scene(innerPane);

		menuStage.setTitle("Menu Frame Project");
		menuStage.setScene(menuScene);
		menuStage.show();
	}
	
	public void showDateTime() {
		// TODO get the current date/time and set this in the text field
	}
	
	public void writeLog() {
		// TODO write the value from the text field to log
	}
	
	public void changeBColor() {
		// TODO change the background color of the frame
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
