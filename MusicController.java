// Music Controlling Part
package application;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;


public class MusicController {

    /*
     * Simple controller class for sample.fxml :
     */
	@FXML
    private MediaPlayer mediaPlayer;
    @FXML
    private MediaView mediaView2;
    @FXML
    private Slider slider;
    private Stage stage;
    @FXML
	private Slider volumeSlider;
    
    @FXML
    private Slider musicSlider;
    @FXML
    private Duration mediaDuration;
    @FXML
    private Button closeButton;
    @FXML
    private void openButton(ActionEvent event) {

        try {

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Select mp3 ", "*.mp3");

            fileChooser.getExtensionFilters().add(extensionFilter);

            File selectedFile = fileChooser.showOpenDialog(null);
            String filepath = selectedFile.toURI().toString();

            mediaPlayer = new MediaPlayer(new Media(filepath));
            mediaView2.setMediaPlayer(mediaPlayer);
            	/*
            	 4682b4
	b0c4de
	87ceeb
            	 */
            
            mediaPlayer.play();
            mediaPlayer.setAutoPlay(true);
            mediaDuration = mediaPlayer.getTotalDuration();

            //For auto-resize :
           

        } catch (Exception exception) {

            System.out.println((char) 27 + "[36m" + "[X] Exception was caused :: Re-run the application and try again. " + (char) 27 + "[0m");
            exception.printStackTrace();
        }
        
        volumeSlider.setValue(mediaPlayer.getVolume()*100);
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable observable)
			{
				mediaPlayer.setVolume(volumeSlider.getValue()/100);
			}
			
		});
		mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {

			@Override
			public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
				// TODO Auto-generated method stub
				musicSlider.setValue(newValue.toSeconds());
				musicSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
			}
			
		});
		
		musicSlider.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				mediaPlayer.seek(mediaDuration.seconds(musicSlider.getValue()));
			}
			
		});
		
			
    }
    
   
    public void play(ActionEvent event)
	{
		mediaPlayer.play();
		mediaPlayer.setRate(1);
	}
	public void pause(ActionEvent event)
	{
		mediaPlayer.pause();
	}
	public void fast(ActionEvent event)
	{
		mediaPlayer.setRate(2);// Speed Rate
	}
	public void slow(ActionEvent event)
	{
		mediaPlayer.setRate(0.5);
	}
	public void reload(ActionEvent event)
	{
		mediaPlayer.seek(mediaPlayer.getStartTime());
		mediaPlayer.play();
	}
	
	public void stop(ActionEvent event)
	{
		mediaPlayer.stop();
	}
	public void Close(ActionEvent event)
	{
		 Stage stage = (Stage) closeButton.getScene().getWindow();
		    // do what you have to do
		 	mediaPlayer.dispose();
		    stage.close();
	}
}
