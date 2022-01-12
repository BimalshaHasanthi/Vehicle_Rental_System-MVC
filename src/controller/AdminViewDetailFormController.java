package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.HirePayment;
import model.RentPayment;
import model.tableModel.HirePaymentTM;
import model.tableModel.RentPaymentTM;

import java.util.ArrayList;

public class AdminViewDetailFormController {
    public AnchorPane manageDetailContext;
    public TableView<RentPaymentTM> tblRent;
    public TableColumn<RentPaymentTM,String> colRentInvoice;
    public TableColumn<RentPaymentTM,String> colRentId;
    public TableColumn<RentPaymentTM,String> colRentClientId;
    public TableColumn<RentPaymentTM,String> colVehicleId;
    public TableColumn<RentPaymentTM,String> colRentTotal;
    public TableView<HirePaymentTM> tblHire;
    public TableColumn<HirePaymentTM,String> colHireInvoice;
    public TableColumn<HirePaymentTM,String> colHireId;
    public TableColumn<HirePaymentTM,String> colClientId;
    public TableColumn<HirePaymentTM,String> colHireTotal;


    ObservableList<HirePaymentTM> obList1= FXCollections.observableArrayList();
    ObservableList<RentPaymentTM> obList2= FXCollections.observableArrayList();


    public void initialize(){

        viewRent();
        viewHire();

        colRentInvoice.setCellValueFactory(new PropertyValueFactory<>("rentInvoiceNo"));
        colRentId.setCellValueFactory(new PropertyValueFactory<>("rentId"));
        colRentClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colRentTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        colHireInvoice.setCellValueFactory(new PropertyValueFactory<>("hireInvoiceNo"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colHireId.setCellValueFactory(new PropertyValueFactory<>("hireId"));
        colHireTotal.setCellValueFactory(new PropertyValueFactory<>("totalPayment"));


    }

    private void viewHire() {
        obList1.clear();
        ArrayList<HirePayment> hires=new HirePaymentController().getAllHires();
        ArrayList<HirePaymentTM> hireList=new ArrayList<>();
        for(int i=0; i<hires.size(); i++) {
            HirePayment h=hires.get(i);
            hireList.add(new HirePaymentTM(
                    h.getHireInvoiceNo(),
                    h.getHireId(),
                    h.getClientId(),
                    h.getTotalPayment()
                    )
            );
        }
        obList1.addAll(hireList);
        tblHire.setItems(obList1);
    }

    private void viewRent() {
        obList2.clear();
        ArrayList<RentPayment> rent=new RentPaymentController().getAllRent();
        ArrayList<RentPaymentTM> rentList=new ArrayList<>();
        for(int i=0; i<rent.size(); i++) {
            RentPayment r=rent.get(i);
            rentList.add(new RentPaymentTM(
                        r.getRentInvoiceNo(),
                        r.getRentId(),
                        r.getClientId(),
                        r.getVehicleId(),
                        r.getTotal()
                    )
            );
        }
        obList2.addAll(rentList);
        tblRent.setItems(obList2);
    }


}
