package com.sample.pharmacy;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

public class HomePage extends Application implements Initializable {

    Main main=new Main();

    double x, y;

    //region =========SideMenu=========
    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton addMedicineButton;

    @FXML
    private JFXButton viewMedicineButton;

    @FXML
    private JFXButton LogOutBtn;

    @FXML
    private AnchorPane slider;

    //endregion=======================

    //region =========TopMenu==========

    @FXML
    private JFXButton menuHomeButton;

    @FXML
    private JFXButton menuAddMedicineButton;

    @FXML
    private JFXButton menuViewMedicineButton;

    @FXML
    private ImageView Exit;


    //endregion========================

    //region ==========Panes===========

    @FXML
    private AnchorPane HomePane;

    @FXML
    private AnchorPane AddMedicinePane;

    @FXML
    private AnchorPane ViewMedicinePane;

    @FXML
    private AnchorPane MedicineListPane;

    //endregion=================

    //region ===========Home===========

    @FXML
    private Label salesNum;

    @FXML
    private Label pharmNum;

    //endregion========================

    //region =======AddMedicines=======

    @FXML
    private Label successfullAdd;

    @FXML
    private TextField idField;

    @FXML
    private TextField medicineNameField;

    @FXML
    private TextField companyNameField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField priceField;

    //endregion=====================

    //region =======ViewMedicines======

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Medicines> medicinesTable;

    @FXML
    private TableColumn<Medicines, String> idColumn;

    @FXML
    private TableColumn<Medicines, String> medicineNameColumn;

    @FXML
    private TableColumn<Medicines, String> companyNameColumn;

    @FXML
    private TableColumn<Medicines, String> orderDateColumn;

    @FXML
    private TableColumn<Medicines, Integer> quantityColumn;

    @FXML
    private TableColumn<Medicines, Integer> priceColumn;

    @FXML
    private TableColumn<Medicines, Integer> totalColumn;

    @FXML
    private Button countButton;

    @FXML
    private Button printButton;

    @FXML
    private TextArea txtBill;

    @FXML
    private TextField paymentField;

    @FXML
    private Label paymentText;

    @FXML
    private TextField balanceField;

    @FXML
    private TextField totalField;


    //endregion======================

    //region =======MedicineList=======

    @FXML
    private TableView<MedicinesDB> medDBTable;

    @FXML
    private TableColumn<MedicinesDB, Integer> idDB;

    @FXML
    private TableColumn<MedicinesDB, String> medNameDB;

    @FXML
    private TableColumn<MedicinesDB, Integer> quantityDB;

    @FXML
    private TableColumn<MedicinesDB, Integer> priceDB;

    //endregion========================


    private final ObservableList<Medicines> medList=FXCollections.observableArrayList();
    ObservableList<MedicinesDB> listDBMed=FXCollections.observableArrayList();

    AtomicLong ident=new AtomicLong(0);
    long id=ident.incrementAndGet();


    public void welcome() {

        try {
            Stage homePage = new Stage();

            Parent root =FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("homePage.fxml")));
            Scene scene = new Scene(root);

            scene.setOnMousePressed(mouseEvent -> {
                x = mouseEvent.getSceneX();
                y = mouseEvent.getSceneY();
            });

            scene.setOnMouseDragged(mouseEvent -> {
                homePage.setX(mouseEvent.getScreenX() - x);
                homePage.setY(mouseEvent.getScreenY() - y);
            });

            homePage.setTitle("Pharmacy System");
            homePage.initStyle(StageStyle.UNDECORATED);
            homePage.setScene(scene);
            homePage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        printButton.setDisable(true);
        Exit.setOnMouseClicked(mouseEvent -> System.exit(0));
        slider.setTranslateX(-270);

        //region SideMenuClick
        Menu.setOnMouseClicked(mouseEvent -> {
            TranslateTransition slide=new TranslateTransition();
            slide.setDuration(Duration.seconds(0.3));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-250);

            slide.setOnFinished(actionEvent-> {
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            } );
        });

        MenuBack.setOnMouseClicked(mouseEvent -> {
            TranslateTransition slide=new TranslateTransition();
            slide.setDuration(Duration.seconds(0.3));
            slide.setNode(slider);

            slide.setToX(-250);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished(actionEvent-> {
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            } );
        });
        //endregion

        idField.setText("MD" + id);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        medicineNameColumn.setCellValueFactory(new PropertyValueFactory<>("medicineName"));
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadData();

        countSales();
        countPharmacy();


    }

    //region HOME

    public void countSales() {
        DBConnect dbcon=new DBConnect();
        Connection con=dbcon.getConnection();

        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT count(*) AS count from sales");

            while(rs.next()) {
                int count=rs.getInt("count");
                String padded=String.format("%03d",count);
                salesNum.setText(padded);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void countPharmacy() {
        DBConnect dbcon=new DBConnect();
        Connection con=dbcon.getConnection();

        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT count(*) AS count FROM users");

            while(rs.next()) {
                int count=rs.getInt("count");
                String padded=String.format("%03d",count);
                pharmNum.setText(padded);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void contactButtonOnActive(ActionEvent event) {
    getHostServices().showDocument("https://t.me/serendepityiddyllic");
    }
    //endregion

    //region ADDMEDICINE

    public void submit(ActionEvent event) throws InterruptedException {
        if(medicineNameField.getText().isEmpty() || companyNameField.getText().isEmpty() || quantityField.getText().isEmpty()
        || priceField.getText().isEmpty()) {
            successfullAdd.setTextFill(Color.RED);
            successfullAdd.setText("Please, enter all details!");
        }
        else {
            successfullAdd.setTextFill(Color.GREEN);
            successfullAdd.setText("Order has been added");
            Medicines medicines = new Medicines(idField.getText(),medicineNameField.getText(), companyNameField.getText(),
                    dateField.getEditor().getText(), Integer.parseInt(quantityField.getText()), Integer.parseInt(priceField.getText()), calculateTotal());

            medList.add(medicines);
            medicinesTable.setItems(medList);

            ident.set(id++);
            idField.setText("MD" + id);
            medicineNameField.setText("");
            companyNameField.setText("");
            dateField.setValue(null);
            quantityField.setText("");
            priceField.setText("");

            calculateAllTotal();
        }
    }

    public void clearButtonOnActive(ActionEvent event) {
        medicineNameField.setText("");
        companyNameField.setText("");
        dateField.setValue(null);
        quantityField.setText("");
        priceField.setText("");

        successfullAdd.setText("");
    }

    public void clearSuccessful() {
        successfullAdd.setText("");
    }

    //endregion

    //region VIEWMEDICINE

    public void search() {
        FilteredList<Medicines> filteredList= new FilteredList<>(medList, b -> true);
            searchField.setOnKeyReleased(keyEvent -> {
            searchField.textProperty().addListener((observableValue, oldValue, newValue) ->
                    filteredList.setPredicate(medicines -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (medicines.getMedicineName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(medicines.getPrice()).indexOf(lowerCaseFilter) !=-1) {
                    return true;
                } else if (medicines.getOrderDate().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;
            }));

        SortedList<Medicines> sortedList=new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(medicinesTable.comparatorProperty());
        medicinesTable.setItems(sortedList);
            });
    }

    public void clearPaymentText() {
        paymentText.setText("");
    }

    public void countBtnOnActive() {

        if (paymentField.getText().isEmpty()) {
            paymentText.setText("Please, insert the payment amount!");
        }
        else if (Integer.parseInt(paymentField.getText()) < Integer.parseInt(totalField.getText())) {
            paymentText.setText("not enough money");
            paymentField.setText("");
        }
        else {
            balance();
            bill();
            countButton.setDisable(true);
            printButton.setDisable(false);
        }
    }

    public void removeMedicines(ActionEvent event) {
        ident.set(id--);
        idField.setText("MD" + id);
        medicinesTable.setItems(medList);
        medicinesTable.getItems().removeAll(medicinesTable.getSelectionModel().getSelectedItem());
    }

    public void calculateAllTotal() {
        int sum=0;
        for(int i=0;i<medicinesTable.getItems().size();i++) {
            sum = sum + totalColumn.getCellObservableValue(i).getValue();
        }
        totalField.setText(String.valueOf(sum));
    }

    public void balance() {
        int totalAll=Integer.parseInt(totalField.getText());
        int payment=Integer.parseInt(paymentField.getText());

        int bal=payment-totalAll;

        balanceField.setText(String.valueOf(bal));
    }

    public int calculateTotal() {
        int total;
       total=Integer.parseInt(quantityField.getText())*Integer.parseInt(priceField.getText());
       return total;
    }

    public void bill() {

        String paym = paymentField.getText();
        String totalAll = totalField.getText();
        String balance = balanceField.getText();

            txtBill.setText(txtBill.getText() + "******************************************\n");
            txtBill.setText(txtBill.getText() + "\t         Pharmacy\n");
            txtBill.setText(txtBill.getText() + "******************************************\n");

            txtBill.setText(txtBill.getText() + "Medicine" + "\t" + "Price" + "\t" + "Quantity" + "     " + "Total\n");

            for (int i = 0; i < medicinesTable.getItems().size(); i++) {
                String mname = medicineNameColumn.getCellObservableValue(i).getValue();
                Integer price = priceColumn.getCellObservableValue(i).getValue();
                Integer quan = quantityColumn.getCellObservableValue(i).getValue();
                Integer total = totalColumn.getCellObservableValue(i).getValue();

                txtBill.setText(txtBill.getText() + mname + "   \t" + price + "  \t" + quan + "    \t" + total + "\n");
            }

            txtBill.setText(txtBill.getText() + "\n");
            txtBill.setText(txtBill.getText() + "******************************************\n");
            txtBill.setText(txtBill.getText() + "\n");

            txtBill.setText(txtBill.getText() + "\t\t\t" + "Total: " + totalAll + "\n");
            txtBill.setText(txtBill.getText() + "\t\t\t" + "Payment: " + paym + "\n");
            txtBill.setText(txtBill.getText() + "\t\t\t" + "Change: " + balance + "\n");

            txtBill.setText(txtBill.getText() + "******************************************\n");
            txtBill.setText(txtBill.getText() + "\n");
            txtBill.setText(txtBill.getText() + "\t       THANK YOU!");
        }

    public void printMedicine() {

        //region Printer
        PrinterJob job=PrinterJob.createPrinterJob();
        boolean proseed=job.showPrintDialog(null);
        JobSettings ss1=job.getJobSettings();

        PageLayout pl1=ss1.getPageLayout();

        double pgW1=pl1.getPrintableWidth();
        double pgH1=pl1.getPrintableHeight();

        TextArea temp=new TextArea(txtBill.getText());

        temp.setPrefSize(pgW1,pgH1);
        temp.setWrapText(true);

        if(proseed) {
            boolean printed=job.printPage(temp);
            if(printed) {
                job.endJob();
            }
        }
        //endregion

        paymentField.clear();
        txtBill.clear();
        countButton.setDisable(false);

        //region MedID
        for(int i=0;i<medicinesTable.getItems().size();i++) {
            ident.set(id--);
        }
        idField.setText("MD" + id);

        for(int i=0;i<medicinesTable.getItems().size();i++) {
            medicinesTable.getItems().clear();
        }
        //endregion

        //region DBSales
        String nums=companyNameField.getText();

        try {
            Class.forName("org.postgresql.Driver");
            Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/pharmacy", "postgres", "niyazbek");

            PreparedStatement ps=con.prepareStatement("INSERT INTO sales(nums) VALUES (?)");

            ps.setString(1,nums);
            ps.executeUpdate();
            ps.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        countSales();
        //endregion


        printButton.setDisable(true);
    }


    //endregion

    //region MEDICINELIST

    public void loadData() {
        DBConnect dbcon=new DBConnect();
        Connection connectDB=dbcon.getConnection();

        try {
            Statement statement=connectDB.createStatement();
            ResultSet rs=statement.executeQuery("SELECT id,\"medNameDB\",\"quantityDB\",\"priceDB\" from \"DBMed\"");

            while(rs.next()) {

                Integer id=rs.getInt("id");
                String medName=rs.getString("medNameDB");
                Integer quantity=rs.getInt("quantityDB");
                Integer price=rs.getInt("priceDB");

                listDBMed.add(new MedicinesDB(id,medName,quantity,price));
            }

            idDB.setCellValueFactory(new PropertyValueFactory<>("idDB"));
            medNameDB.setCellValueFactory(new PropertyValueFactory<>("medNameDB"));
            quantityDB.setCellValueFactory(new PropertyValueFactory<>("quantityDB"));
            priceDB.setCellValueFactory(new PropertyValueFactory<>("priceDB"));

            medDBTable.setItems(listDBMed);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void refresheTable() {
        DBConnect dbcon=new DBConnect();
        Connection connectDB=dbcon.getConnection();

        try {
            listDBMed.clear();
            Statement statement=connectDB.createStatement();
            ResultSet rs=statement.executeQuery("SELECT id,\"medNameDB\",\"quantityDB\",\"priceDB\" from \"DBMed\"");

            while(rs.next()) {
                Integer id=rs.getInt("id");
                String medName=rs.getString("medNameDB");
                Integer quantity=rs.getInt("quantityDB");
                Integer price=rs.getInt("priceDB");

                listDBMed.add(new MedicinesDB(id,medName,quantity,price));
                medDBTable.setItems(listDBMed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region SideBarMenu

    public void homeButtonOnActive(ActionEvent event) {
        AddMedicinePane.setVisible(false);
        ViewMedicinePane.setVisible(false);
        MedicineListPane.setVisible(false);
        HomePane.setVisible(true);

    }

    public void addMedicineButtonOnActive(ActionEvent event) {
    HomePane.setVisible(false);
    AddMedicinePane.setVisible(true);
    MedicineListPane.setVisible(false);
    ViewMedicinePane.setVisible(false);


    }

    public void viewMedicineButtonOnActive(ActionEvent event) {
        HomePane.setVisible(false);
        AddMedicinePane.setVisible(false);
        MedicineListPane.setVisible(false);
        ViewMedicinePane.setVisible(true);

    }

    public void medicineListButtonOnActive(ActionEvent event) {
        HomePane.setVisible(false);
        AddMedicinePane.setVisible(false);
        ViewMedicinePane.setVisible(false);
        MedicineListPane.setVisible(true);
    }

    public void logOutBtnOnActive(ActionEvent event) {
        Stage home=(Stage) LogOutBtn.getScene().getWindow();
        home.close();

        Stage stage=new Stage();
        try {
            main.start(stage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
    }
    //endregion
}

