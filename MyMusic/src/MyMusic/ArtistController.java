package MyMusic;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.util.List;

public class ArtistController {
    private Artist artist;
    private User user;
    private boolean inEditMode;

    @FXML
    private Label artistNameLabel;
    @FXML
    private ImageView artistImageView;
    @FXML
    private VBox artistInfoBox;
    @FXML
    private HBox artistAlbumsBox;
    @FXML
    private HBox artistTracksBox;
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField artistNameTextField;
    @FXML
    private TextField imagePathTextField;



    private Node getAlbumResults(List<Album> albumList) {
        // VBox that will contain everything and be returned
        VBox albumResults = new VBox();
        albumResults.setSpacing(5);
        albumResults.setPadding(new Insets(10,5,10,5));

        if(albumList.size() > 0) {
            // Scroll pane
            ScrollPane albumScrollPane = new ScrollPane();
            albumScrollPane.setPrefHeight(200);
            albumScrollPane.setPrefWidth(1600);
            albumScrollPane.setFitToHeight(true);

            // Album results in a VBox that will be the content of the scroll pane
            VBox albumVBox = new VBox();

            // Row of album item boxes
            HBox itemBoxRow = new HBox();
            itemBoxRow.setPadding(new Insets(10,10,10,10));
            itemBoxRow.setSpacing(20);

            for (int i = 0; i <= albumList.size(); i++) {
                if (i == albumList.size()) {
                    albumVBox.getChildren().add(itemBoxRow);
                    break;
                }

                try {
                    Album album = albumList.get(i);
                    ItemBox itemBox = new ItemBox(album);
                    itemBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            PageChanger.getInstance().goToAlbumPage(artistNameLabel.getScene(), album, user);
                        }
                    });
                    itemBoxRow.getChildren().add(itemBox);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // Add item HBox row to search results box after every 9th item
                if ((i + 1) % 9 == 0) {
                    albumVBox.getChildren().add(itemBoxRow);

                    // Create new item box row
                    itemBoxRow = new HBox();
                    itemBoxRow.setPadding(new Insets(10, 10, 10, 10));
                    itemBoxRow.setSpacing(20);
                }
            }
            albumScrollPane.setContent(albumVBox);
            albumResults.getChildren().add(albumScrollPane);
        }

        return albumResults;
    }

    @FXML
    public void toggleEditMode() {
        if (inEditMode) {
            inEditMode = false;
            artistNameLabel.setManaged(true);
            artistNameLabel.setVisible(true);
            editButton.setManaged(true);
            editButton.setVisible(true);
            saveButton.setManaged(false);
            saveButton.setVisible(false);
            artistNameTextField.setManaged(false);
            artistNameTextField.setVisible(false);
            imagePathTextField.setManaged(false);
            imagePathTextField.setVisible(false);
        }
        else {
            inEditMode = true;
            artistNameLabel.setManaged(false);
            artistNameLabel.setVisible(false);
            editButton.setManaged(true);
            editButton.setVisible(true);
            saveButton.setManaged(true);
            saveButton.setVisible(true);
            artistNameTextField.setManaged(true);
            artistNameTextField.setVisible(true);
            imagePathTextField.setManaged(true);
            imagePathTextField.setVisible(true);

            artistNameTextField.setText(artist.getName());
            imagePathTextField.setText(artist.getImagePath());
        }
    }

    @FXML
    private void save() {
        DatabaseManager databaseManager = null;
        try {
            databaseManager = new DatabaseManager();

            artist.setName(artistNameTextField.getText());
            artist.setImagePath(imagePathTextField.getText());

            artistNameLabel.setText(artist.getName());
            Image image = new Image(artist.getImagePath());
            artistImageView.setImage(image);

            databaseManager.updateArtist(artist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goBack() {
        PageChanger.getInstance().goToSearchPage(artistNameLabel.getScene(), user);
    }

    public void setUp(Artist artist, User user) {
        this.artist = artist;
        this.user = user;
        inEditMode = false;

        if (artist != null) {
            Image image = new Image(artist.getImagePath());
            artistImageView.setImage(image);
            artistNameLabel.setText(artist.getName());
            artistAlbumsBox.getChildren().add(getAlbumResults(artist.getAlbums()));

            TableView tableView = new TrackTableView(artist.getTracks());
            tableView.setMinWidth(1600);
            artistTracksBox.getChildren().add(tableView);
        }

        // Admin
        if (user.isAdmin()) {
            editButton.setVisible(true);
            editButton.setManaged(true);
        }
        else {
            editButton.setVisible(false);
            editButton.setManaged(false);
        }
    }
}
