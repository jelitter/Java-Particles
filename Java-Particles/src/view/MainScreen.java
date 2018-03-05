package view;

import java.util.ArrayList;
import model.Particle;

import application.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainScreen extends BorderPane {
	
	private final int PADDING = 6;

	//gameloop variables
	private Timeline gameLoop;

	//main variables
	private Main main;
	private final DrawScreen center;

	public MainScreen(Main main) {
		this.main = main;

		VBox buttonBox = new VBox(PADDING);
		ArrayList<Button> buttons = new ArrayList<Button>(); 
		
		buttonBox.setPadding(new Insets(PADDING));
		buttonBox.setStyle("background: #000");

		buttons.add(new Button("Gravity +"));
		buttons.add(new Button("Gravity -"));
		buttons.add(new Button("Max Size +"));
		buttons.add(new Button("Max Size -"));

		for (Button b : buttons) {
			b.setMinWidth(120);
			b.setOnMouseClicked(e -> {
				String buttonText = b.getText();
				System.out.println("Button clicked: " + b.getText());
				switch(buttonText)
				{
					case ("Gravity +"):
						Particle.changeGravity("w");
						break;
					case ("Gravity -"):
						Particle.changeGravity("s");
						break;
					case ("Max Size +"):
						Particle.MAX_SIZE++;
						break;
					case ("Max Size -"):
						Particle.MAX_SIZE--;
						break;
					default:
						break;
				}
			});
			buttonBox.getChildren().add(b);
		}

		this.setLeft(buttonBox);
		this.center = new DrawScreen();
		setCenter();
		runGameLoop();
	}

	private void setCenter() {
		this.setCenter(center);
	}

	private void runGameLoop() {
		EventHandler<ActionEvent> gameUpdate = event
				-> {
					center.update();
					center.draw();
				};
				gameLoop = new Timeline(new KeyFrame(Duration.millis(33.3), gameUpdate));
				gameLoop.setCycleCount(Animation.INDEFINITE);
				gameLoop.play();
	}

	public void stopGameLoop() {
		gameLoop.stop();
	}

}
