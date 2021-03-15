package main.ui.admin.edit_doctor;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import database.DoctorDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import model.Doctor;

import java.io.IOException;
import java.sql.SQLException;

public class EditDoctorController {
    @FXML
    private Label doctor_name;

    @FXML
    private StackPane myStackPane;

    @FXML
    private Button dismissButton;

    @FXML
    private Label specialist2;

    @FXML
    private Label hospitalAffiliations;

    @FXML
    private Label contact;

    @FXML
    private Label email;

    @FXML
    private Label address;

    @FXML
    private TextArea professionalSummary;

    @FXML
    private ListView<HBox> educationalBackgroundList;

    @FXML
    private ListView<HBox> professionalExperienceList;

    @FXML
    private ListView<HBox> avaiableDurationList;

    @FXML
    private ListView<HBox> chamberAddressList;


    EditDoctorController editDoctorController;

    public int selectedDoctorId;
    String educationalBackground, professionalExperience;
    Doctor doctor;

    public void setDoctorNumber(int doctorId, EditDoctorController editDoctorController) throws SQLException {

        selectedDoctorId = doctorId;
        this.editDoctorController = editDoctorController;
        DoctorDao doctorDao = new DoctorDao();

        doctor = doctorDao.getDoctorProfile(selectedDoctorId+"");
        doctor_name.setText(doctor.getName());
        specialist2.setText(doctor.getSpecialist());
        hospitalAffiliations.setText(doctor.getAffiliations());
        email.setText(doctor.getEmail());
        contact.setText(doctor.getPhone());
        address.setText(doctor.getAddress());
        professionalSummary.setText(doctor.getProfessionalExperience());
        professionalExperience = doctor.getProfessionalExperience();
        educationalBackground = doctor.getEducationalBackground();

        String[] educationList = educationalBackground.split(",");
        String[] experienceList = professionalExperience.split(",");

        for (int i = 0; i < educationList.length ; i++) {
            HBox hBox = createCard(educationList[i].trim());
            HBox btnContainer = (HBox) hBox.getChildren().get(2);
            ImageView button = (ImageView) btnContainer.getChildren().get(0);
            int finalI = i;
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Tile pressed " + finalI);
                    educationalBackgroundList.getItems().remove(finalI);
                }
            });
            educationalBackgroundList.getItems().add(hBox);
        }

        for (int i = 0; i < experienceList.length; i++){
            HBox hBox = createCard(experienceList[i].trim());
            HBox btnContainer = (HBox) hBox.getChildren().get(2);
            ImageView button = (ImageView) btnContainer.getChildren().get(0);
            int finalI = i;
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Tile pressed " + finalI);
                    professionalExperienceList.getItems().remove(finalI);
                }
            });
            professionalExperienceList.getItems().add(hBox);
        }


        for (int i = 0; i < 2; i++) {
            HBox hBox = createCard("habijabi");
            HBox btnContainer = (HBox) hBox.getChildren().get(2);
            ImageView button = (ImageView) btnContainer.getChildren().get(0);
            int finalI = i;
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Tile pressed " + finalI);
                    avaiableDurationList.getItems().remove(finalI);
                }
            });
            avaiableDurationList.getItems().add(hBox);
        }


        for (int i = 0; i < 2; i++) {
            HBox hBox = createCard("habijabi");
            HBox btnContainer = (HBox) hBox.getChildren().get(2);
            ImageView button = (ImageView) btnContainer.getChildren().get(0);
            int finalI = i;
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Tile pressed " + finalI);
                    chamberAddressList.getItems().remove(finalI);
                }
            });
            chamberAddressList.getItems().add(hBox);
        }




    }


    public void recieveTextBack(String addItem, String attribute){
        DoctorDao doctorDao = new DoctorDao();
        if(attribute.equals("Educational Background")) {
            HBox hBox = createCard(addItem);
            HBox btnContainer = (HBox) hBox.getChildren().get(2);
            ImageView button = (ImageView) btnContainer.getChildren().get(0);
            int finalI = educationalBackgroundList.getItems().size();
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event){
                    educationalBackgroundList.getItems().remove(finalI);
                }
            });
            educationalBackgroundList.getItems().add(hBox);
            String newData = educationalBackground + "," +addItem;
            doctorDao.updateDoctorAttribute("educaional_background",newData,selectedDoctorId);

        }else if(attribute.equals("Professional Experience")){
            HBox hBox = createCard(addItem);
            HBox btnContainer = (HBox) hBox.getChildren().get(2);
            ImageView button = (ImageView) btnContainer.getChildren().get(0);
            int finalI = professionalExperienceList.getItems().size();
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    professionalExperienceList.getItems().remove(finalI);
                }
            });
            professionalExperienceList.getItems().add(hBox);
            String newData = professionalExperience + "," +addItem;
            doctorDao.updateDoctorAttribute("professional_experience",newData,selectedDoctorId);
        }
    }



    @FXML
    void addEducationalBackgroundClick(MouseEvent event) throws IOException {

        try{
            JFXDialogLayout content = new JFXDialogLayout();
            content.getStyleClass().add("jfx-dialog-overlay-pane");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/ui/admin/edit_doctor/add_features_dialog.fxml"));
            loader.load();
            JFXDialog dialog = new JFXDialog(myStackPane, loader.getRoot(), JFXDialog.DialogTransition.CENTER);
            dialog.getStyleClass().add("jfx-dialog-layout");
            AddFeaturesController dialogController = loader.getController();
            dialogController.setTitle("Educational Background",editDoctorController);
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    void addProfessionalExperienceClick(MouseEvent event) {
        try{
            JFXDialogLayout content = new JFXDialogLayout();
            content.getStyleClass().add("jfx-dialog-overlay-pane");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/ui/admin/edit_doctor/add_features_dialog.fxml"));
            loader.load();
            JFXDialog dialog = new JFXDialog(myStackPane, loader.getRoot(), JFXDialog.DialogTransition.CENTER);
            dialog.getStyleClass().add("jfx-dialog-layout");
            AddFeaturesController dialogController = loader.getController();
            dialogController.setTitle("Professional Experience",editDoctorController);
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void onDismissClick(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void onEditClick(ActionEvent event) {

        try{
            JFXDialogLayout content = new JFXDialogLayout();
            content.getStyleClass().add("jfx-dialog-overlay-pane");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/ui/admin/edit_doctor/edit_doctor_dialog.fxml"));
            loader.load();
            JFXDialog dialog = new JFXDialog(myStackPane, loader.getRoot(), JFXDialog.DialogTransition.CENTER);
            dialog.getStyleClass().add("jfx-dialog-layout");
            EditDoctorDialogController dialogController = loader.getController();
            dialogController.setLabel(selectedDoctorId, doctor.getSpecialist(), doctor.getEmail(),
                    doctor.getPhone(), doctor.getAffiliations(), doctor.getAffiliations(), dialog);
            //change affiliations here according to ui
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public HBox createCard(String education) {
        HBox hBox = new HBox();

        ImageView icon = new ImageView();
        icon.getStyleClass().add("bullet-icon");
        icon.setFitWidth(10);
        icon.setFitHeight(10);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        vBox.setPrefWidth(50);
        vBox.getChildren().addAll(icon);

        VBox vBox1 = new VBox();
        vBox1.setAlignment(Pos.CENTER);
        Label nameLabel = new Label(education);
        nameLabel.getStyleClass().add("text-card-subtitle");
        nameLabel.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(nameLabel);

        ImageView icon2 = new ImageView();
        icon2.getStyleClass().add("delete-icon");
        icon2.setFitWidth(30);
        icon2.setFitHeight(30);

        HBox hBox1 = new HBox();
        HBox.setHgrow(hBox1, Priority.ALWAYS);
        hBox1.setAlignment(Pos.CENTER_RIGHT);
        hBox1.getChildren().add(icon2);

        hBox.getChildren().addAll(vBox, vBox1, hBox1);

        return hBox;

    }


}
