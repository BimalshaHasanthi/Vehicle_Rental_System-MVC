package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Insurence;
import model.Vehicle;
import model.tableModel.RentTM;
import model.tableModel.VehicleTM;

import java.util.ArrayList;

public class VehicleAvailabilityFormController {
    public TableView<VehicleTM> tblVehicle;
    public TableColumn<VehicleTM,String> colVehicleId;
    public TableColumn<VehicleTM,String> colVehicleNo;
    public TableColumn<VehicleTM,String> colType;
    public TableColumn<VehicleTM,String> colBrand;
    public TableColumn<VehicleTM,String> colRentCost;
    public TableColumn<VehicleTM,String> colHireCost;
    public TableColumn<VehicleTM,String> colDiscount;
    public TableColumn<VehicleTM,String> colStatus;
    public TableColumn<VehicleTM,String> colColour;

    ObservableList<VehicleTM> obList= FXCollections.observableArrayList();

    public void initialize(){

        viewAllVehicle();

        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
        colType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colColour.setCellValueFactory(new PropertyValueFactory<>("colour"));
        colRentCost.setCellValueFactory(new PropertyValueFactory<>("rentalCost"));
        colHireCost.setCellValueFactory(new PropertyValueFactory<>("hireCost"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

    }

    private void viewAllVehicle() {
        obList.clear();
        ArrayList<Vehicle> vehicles=new VehicleController().getAllVehicle();
        ArrayList<Insurence> insurances=new InsurenceController().getAllInsurence();
        ArrayList<VehicleTM> vehicleList=new ArrayList<>();
        for(int i=0; i<vehicles.size(); i++) {
            Vehicle ve=vehicles.get(i);
            Insurence in=insurances.get(i);
            vehicleList.add(new VehicleTM(
                            ve.getVehicleId(),
                            ve.getVehicleNo(),
                            ve.getVehicleType(),
                            ve.getBrand(),
                            ve.getColour(),
                            ve.getRentalCost(),
                            ve.getHireCost(),
                            ve.getDiscount(),
                            ve.getvStatus(),
                            ve.getInsurenceId(),
                            in.getCompanyName(),
                            in.getContact(),
                            in.getDetail()
                    )
            );
        }
        obList.addAll(vehicleList);
        tblVehicle.setItems(obList);

    }


}
