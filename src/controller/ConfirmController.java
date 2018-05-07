package controller;

import java.util.List;

import bookingRoom.Booked;
import bookingRoom.BookingRequest;
import bookingRoom.DatabaseManage;
import bookingRoom.Total;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ConfirmController {

	@FXML
	TextField name;
	@FXML
	TextField id;
	@FXML
	TextField call;
	@FXML
	Label confirmBed;
	@FXML
	Label confirmBreakfast;
	@FXML
	Label bookingDay;
	@FXML
	Label costRoom;
	@FXML
	Button confirm;
	@FXML
	Label nameRoom;
	@FXML
	Label discount;

	private static String day = BookingRequest.getInstance().getListFile().get(2);
	private static Total total = Total.getinstance();
	private String arrive = HomeController.readfile().get(0);
	private String depart = HomeController.readfile().get(1);

	@FXML
	public void initialize() {
		handleCostRoom();
		confirm.setOnAction(this::confirm);
	}

	public void handleCostRoom() {
		int days = Integer.parseInt(day);
		String name = "";

		for (String n : total.getNameRoom()) {
			name += " " + n + " ";
		}
		int sum = total.getRoomPrice() + total.showBreakfast() + total.showExtraBed();
		bookingDay.setText(String.format("You reserve %s days", day));
		confirmBreakfast.setText(
				String.format("Add Breakfast %d bed = %d Baht", total.countBreakfast(), total.showBreakfast()));
		confirmBed
				.setText(String.format("Add extra-bed %d bed = %d Baht", total.countExtraBed(), total.showExtraBed()));
		costRoom.setText(String.format("( %d + %d +%d ) x %d days = %d Baht", total.getRoomPrice(),
				total.showBreakfast(), total.showExtraBed(), days, sum * days));
		nameRoom.setText("Including " + name + " room");

	}
	
//	public void handleDiscount(){
//		if(S)
//	}

	public void confirm(ActionEvent event) {
		for (int i = 0; i < total.getNameRoom().size(); i++) {
			collectIndatabase(total.getNameRoom().get(i));
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("You reserve already");
		alert.showAndWait();
	}

	public void collectIndatabase(String nameRoom) {
		String Name = name.getText().trim();
		switch (nameRoom) {
		case ("suite"):
			for (String s : RoomController.SutList) {
				Booked bk = new Booked(s, arrive, depart, Name);
				bookingRoom.DatabaseManage.updateReserving(bk.getReserveCode(), bk.getRoomCode(), arrive, depart, Name);
			}
			break;
		case ("superior"):
			for (String s : RoomController.SprList) {
				Booked bk = new Booked(s, arrive, depart, Name);
				bookingRoom.DatabaseManage.updateReserving(bk.getReserveCode(), bk.getRoomCode(), arrive, depart, Name);
			}
			break;
		case ("standard"):
			for (String s : RoomController.StdList) {
				Booked bk = new Booked(s, arrive, depart, Name);
				bookingRoom.DatabaseManage.updateReserving(bk.getReserveCode(), bk.getRoomCode(), arrive, depart, Name);
			}
			break;
		case ("deluxe"):
			for (String s : RoomController.DlxList) {
				Booked bk = new Booked(s, arrive, depart, Name);
				bookingRoom.DatabaseManage.updateReserving(bk.getReserveCode(), bk.getRoomCode(), arrive, depart, Name);
			}
			break;
		default:
			break;
		}
		DatabaseManage.collectName(Name, Integer.parseInt(id.getText().trim()),
				Integer.parseInt(call.getText().trim()));
	}
}
