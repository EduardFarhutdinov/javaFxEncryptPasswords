package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import sample.main.Main;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    private TextField textFieldLengthPassword;

    @FXML
    private TextField textFieldCountPassword;

    @FXML
    private MenuItem load;

    @FXML
    private MenuItem save;

    @FXML
    private ListView<String> listPasswords;



    @FXML
    private Button buttonGeneratedPass;
    ObservableList<String> passwords = FXCollections.observableArrayList();


    ObservableList<String> password;
    @FXML
    void buttonGeneratedPassOnAction(ActionEvent event) {

        listPasswords.getItems().clear();


        int count = Integer.parseInt(textFieldCountPassword.getText());
        int length = Integer.parseInt(textFieldLengthPassword.getText());

        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .usePunctuation(true)
                .build();

        ObservableList<String> password = passwordGenerator.generate(length, count);


        listPasswords.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        password.stream().forEach(i -> passwords.add(i + "\n"));
        System.out.println(passwords);
        listPasswords.setItems(passwords);

    }

    public boolean saveFile(String path) throws IOException {
        boolean save = false;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            if (listPasswords != null) {

//                oos.writeObject(listPasswords.getItems().toString());
                oos.writeObject(listPasswords.getItems().stream().collect(Collectors.joining()));
//                System.out.println(listPasswords.getItems().toString());
                save = true;
            }

        }
        return save;
    }

    @FXML
    void saveMenu(ActionEvent event) {
        //Создание объекты типа FileChooser
        FileChooser fc = new FileChooser();
        //Установка названия объекту
        fc.setTitle("Окно сохранения файла");
        //Отображение окна для уазания путь сохранения файла
        File file = fc.showSaveDialog(Main.getStage());
        if (file != null) {
            //Получение абсолютного пути
            String path = file.getAbsolutePath();
            boolean save = false;
            try {
                //Сохранение файла
                save = saveFile(path);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                Alarm.showAlert(Alert.AlertType.ERROR, "Сохранение", "Ошибка сохранения", "Объект не был сохранен.");
            }
            if (!save) {
                Alarm.showAlert(Alert.AlertType.ERROR, "Сохранение", "Ошибка сохранения", "Объект не создан.");
            }
        }
    }

    public ObservableList<String> loadFile(String path) throws IOException {
        ObservableList<String> pass = FXCollections.observableArrayList();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            try {

                pass.add(ois.readObject().toString());
                System.out.println(pass);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(String.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return pass;
    }

    @FXML
    void loadMenu(ActionEvent event) {
        listPasswords.getItems().clear();

        FileChooser fc = new FileChooser();
        fc.setTitle("Окно выбора файла");
        //Путь к файлу
        File file = fc.showOpenDialog(Main.getStage());
        if (file != null) {
            String path = file.getAbsolutePath();
            try {

                ObservableList<String> pass = loadFile(path);

//                for (String password : pass) {
//                    passwords.add(password);
//                }
                listPasswords.setItems(pass);


            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                Alarm.showAlert(Alert.AlertType.ERROR, "Загрузка", "Ошибка загрузки", "Объект не был загружен.");
            }
        }
    }


}
