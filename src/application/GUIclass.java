package application;
	
import application.control.badInputException;
import application.control.controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

//display class for all javaFX classes uses controller object to interact with data
public class GUIclass extends Application {


	static controller c = new controller();
	
	
	// creating all stage elements
	private   Label enterName, enterId, enterDob, InvalidInText;
	private   TextField fieldName, fieldId, fieldDob;
	private   Button addButton,listButton,removeButton,saveButton, loadButton, exitButton;
	private   Button promptsave, promptexit ;
	private   Label enterModuleName, enterModuleId, enterGrade;
	private	  TextField fieldModuleName, fieldModuleId, fieldGrade;
	private   Button refreshModules, addModule, removeModule, refreshDropdown, sortDefault, sortGrade, sortName, sortID;
    
	Text studentText = new Text("");
	Text moduleText = new Text("");
	
	
	// creating list view of students
	private static final ListView<Object> listView = new ListView<Object>(c.getStudentView()); 	       
	private static final ListView<Object> listView2 = new ListView<Object>(c.getStudentView()); 		 
	// list view of modules
	private static final ListView<Object> moduleListView = new ListView<Object>(c.getModuleView()); 
	
	private ComboBox<String> dropdown = new ComboBox<String>();
	private ComboBox<String> dropdownModule = new ComboBox<String>(); 
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// INITIALISING STAGE ELEMENTS
			 enterName = new Label("Enter student name");
			 fieldName = new TextField();
			 enterId = new Label("Enter student ID");
			 fieldId = new TextField();
			 enterDob = new Label("Enter date of birth (dd/mm/yyy)");
			 fieldDob = new TextField();

			 addButton = new Button("Add Student");
			 listButton  = new Button("Refrest Student List");
			 removeButton =  new Button("Remove Student");
			 
			 enterModuleName = new Label("Enter module name");
			 fieldModuleName = new TextField();
			 enterModuleId = new Label("Enter module ID");
			 fieldModuleId = new TextField();
			 enterGrade = new Label("Enter grade");
			 fieldGrade = new TextField();
			 
			 refreshModules = new Button("Refresh Module List");
			 addModule = new Button("Add Module");
			 removeModule = new Button("Remove Module");
			 
			 refreshDropdown = new Button("Refresh Student list");
			 sortDefault = new Button("Sort default");
			 sortGrade = new Button("Sort grade");
			 sortName = new Button("Sort name");
			 sortID = new Button("Sort ID");
			 
			 // stage elements for bottom bar
			 saveButton = new Button("Save");
			 loadButton = new Button("Load");
			 exitButton = new Button("Exit");
			 
			 
			 
			 
			 
			 // PROMPTS
			 promptexit = new Button("Exit without Saving");
			 promptsave = new Button("Save & Exit");
			 Stage prompt = new Stage();// creating prompt for exit
             prompt.initModality(Modality.APPLICATION_MODAL);
             prompt.initOwner(primaryStage);
             VBox promptV1 = new VBox(20);
             promptV1.getChildren().addAll(promptsave, promptexit);
             Scene promptScene = new Scene(promptV1, 240, 240);
             prompt.setScene(promptScene);
             
			 InvalidInText = new Label("Data entered was invalid!");// creating prompt in case of wrong data
			 InvalidInText.setTextFill(Color.RED);
             Stage invalidInput = new Stage();
             invalidInput.initModality(Modality.APPLICATION_MODAL);
             invalidInput.initOwner(primaryStage);
             VBox iv1 = new VBox(20);
             iv1.getChildren().addAll(InvalidInText);
             Scene invSc = new Scene(iv1, 240, 120);
             invalidInput.setScene(invSc);

             
             
			 // ACTION EVENTS
		     // -> creates object of method for interface // only works if interface has single method
			 addButton.setOnAction( e->{ 				 // e refers to the specific actionEvent
			 try{
				 c.readStudent(fieldName.getText(), fieldId.getText(), fieldDob.getText());
			 }catch(badInputException ex){		
				 InvalidInText.setText(ex.getMessage());
	             invalidInput.show(); //
			 }
			 });
			 
			 saveButton.setOnAction( e-> c.save()); 
			 
			 loadButton.setOnAction( e-> c.load());
			 
			 promptsave.setOnAction( e -> {
				 c.save();
				 c.exit();
			 });
			 
			 promptexit.setOnAction( e -> c.exit());
			 
			 exitButton.setOnAction( e -> {				             
				                prompt.show(); // shows exit prompt
			});
			 
			 listButton.setOnAction( e-> c.refreshView());
						 
			 removeButton.setOnAction(e-> 
			 	{
			 		try{ //remove student selected in the listview by index, should be fine
			 			c.remove(listView.getSelectionModel().getSelectedIndex());
			 			c.refreshView();
			 		}catch(Exception ex) {
			 			//ex.printStackTrace();
			 			
			 		}
			 	});
			 
			 // action events for module tab
			 refreshModules.setOnAction(e-> {
				 try{
					 	c.refreshModules(listView2.getSelectionModel().getSelectedIndex());
				 	}catch(Exception ex) {
			 			//ex.printStackTrace();
				 		c.refreshView();
			 			
			 		}
			 });
			 
			 listView2.setOnMousePressed(e->{
				 try{
					 	c.refreshModules(listView2.getSelectionModel().getSelectedIndex());
				 	}catch(Exception ex) {
			 			//ex.printStackTrace();
			 			
			 		}
			 });
			 
			 removeModule.setOnAction(e-> 
			 	{
			 		try{ //remove student selected in the listview by index, should be fine
			 			c.removeModule(listView2.getSelectionModel().getSelectedIndex(), moduleListView.getSelectionModel().getSelectedIndex());
			 			c.refreshModules(listView2.getSelectionModel().getSelectedIndex());
			 		}catch(Exception ex) {
			 			//ex.printStackTrace();
			 			
			 		}
			 	});
			 
			 addModule.setOnAction( e->{ 				 // e refers to the specific actionEvent
				 try{
					 c.readModule(listView2.getSelectionModel().getSelectedIndex(), fieldModuleName.getText(), fieldModuleId.getText(), fieldGrade.getText());  
					 c.refreshModules(listView2.getSelectionModel().getSelectedIndex());
				 }catch(badInputException ex){
					 InvalidInText.setText(ex.getMessage());
		             invalidInput.show(); //
				 }catch(Exception ex) {
					 InvalidInText.setText("Select a Student!!!");
		             invalidInput.show();
				 }
				 });
			 
			 // action events for record
			 refreshDropdown.setOnAction(e-> {
				 c.setDropdown(dropdownModule, -1, 0);
				 c.setDropdown(dropdown);
			 
			 });
			 
			 dropdown.setOnAction(e-> {
				 studentText.setText(c.printDetails(dropdown.getSelectionModel().getSelectedIndex()));
				 c.setDropdown(dropdownModule, dropdown.getSelectionModel().getSelectedIndex(), 0);
			 });
			 
			 dropdownModule.setOnAction(e-> {
				 moduleText.setText(c.printDetails(dropdown.getSelectionModel().getSelectedIndex(), dropdownModule.getSelectionModel().getSelectedIndex()));
			 });
			 
			 sortDefault.setOnAction(e->{
				 c.setDropdown(dropdownModule, dropdown.getSelectionModel().getSelectedIndex(), 0);
			 });
			 
			 sortGrade.setOnAction(e->{
				 if(c.getModuleSort()==1) {
					 c.setDropdown(dropdownModule, dropdown.getSelectionModel().getSelectedIndex(), 4);
				 }else {
				 c.setDropdown(dropdownModule, dropdown.getSelectionModel().getSelectedIndex(), 1);
				 }
			 });
			 
			 sortName.setOnAction(e->{
				 if(c.getModuleSort()==3) {
					 c.setDropdown(dropdownModule, dropdown.getSelectionModel().getSelectedIndex(), 6);
					 } 
				 else {
					 c.setDropdown(dropdownModule, dropdown.getSelectionModel().getSelectedIndex(), 3);
				 }
			 });
			 
			 sortID.setOnAction(e->{
				 if(c.getModuleSort()==2) {
					 c.setDropdown(dropdownModule, dropdown.getSelectionModel().getSelectedIndex(), 5);
				 }else {
				 c.setDropdown(dropdownModule, dropdown.getSelectionModel().getSelectedIndex(), 2);
				 }
			 });
			 
			 
			 // setting layout of student tab
			 HBox fp1 = new HBox();
			 HBox fp2 = new HBox();
			 HBox fp3 = new HBox();
			 FlowPane fields1 = new FlowPane(10,5);
			 fp1.getChildren().addAll(enterName,fieldName);
			 fp2.getChildren().addAll(enterId,fieldId);
			 fp3.getChildren().addAll(enterDob,fieldDob);
			 fields1.getChildren().addAll(fp1, fp2, fp3);
			    
			 FlowPane hSbuttons = new FlowPane(10,5);
			 hSbuttons.getChildren().addAll(addButton,listButton,removeButton);
			    
			 VBox v1student = new VBox ();
			 v1student.getChildren().addAll(fields1,hSbuttons);
			 
			 HBox hbstudent = new HBox(10);
			 listView.setPrefWidth(720);
			 hbstudent.getChildren().addAll(listView);
			 
			 VBox vstudent = new VBox (10);
			 vstudent.setPadding(new Insets(10, 5, 5, 24));
			 vstudent.getChildren().addAll(v1student,hbstudent);
			 
			 
			 
			 
			 //creating layout of modules tab
			 HBox fp4 = new HBox();
			 HBox fp5 = new HBox();
			 HBox fp6 = new HBox();
			 FlowPane fields2 = new FlowPane(10,5);
			 fp4.getChildren().addAll(enterModuleName,fieldModuleName);
			 fp5.getChildren().addAll(enterModuleId,fieldModuleId);
			 fp6.getChildren().addAll(enterGrade,fieldGrade);
			 fields2.getChildren().addAll(fp4, fp5, fp6);
			 
			 FlowPane hMbuttons = new FlowPane(10,5);
			 hMbuttons.getChildren().addAll(refreshModules, addModule, removeModule);   
			
			    
			 VBox v1module = new VBox ();
			 v1module.getChildren().addAll(fields2,hMbuttons);
			 
			 HBox hmodule = new HBox(10);
			 listView2.setPrefWidth(360);
			 moduleListView.setPrefWidth(360);
			 hmodule.getChildren().addAll(listView2, moduleListView);
			 
			 VBox vmodule = new VBox (10);
			 vmodule.setPadding(new Insets(10, 5, 5, 24));
			 vmodule.getChildren().addAll(v1module, hmodule);
			 
			 
			 
			 // setting layout for student record tab
			 VBox textvbox = new VBox();
			 VBox text2vbox = new VBox();
			 textvbox.setPrefWidth(360);
			 textvbox.getChildren().add(studentText);
			 text2vbox.getChildren().add(moduleText);
			 text2vbox.setPrefWidth(360);
			 HBox hrecordtext = new HBox(10);
			 hrecordtext.getChildren().addAll(textvbox, text2vbox);
			 
			 HBox hrecord = new HBox(10);
			 FlowPane recordfp1 = new FlowPane(10, 5);
			 FlowPane recordfp2 = new FlowPane(10, 5);
			 recordfp1.getChildren().addAll(dropdown, refreshDropdown, dropdownModule);
			 recordfp2.getChildren().addAll(sortDefault, sortGrade, sortName, sortID);
			 hrecord.getChildren().addAll(recordfp1, recordfp2);
			 
			 VBox vrecords = new VBox (10);
			 vrecords.setPadding(new Insets(10, 5, 5, 24));
			 vrecords.getChildren().addAll(hrecord, hrecordtext);
			 
			 
			 
			 HBox vbottom = new HBox(10);
			 vbottom.getChildren().addAll(loadButton, saveButton, exitButton);
			 
			 // creating tabpane
			 TabPane tabPane= new TabPane();
			 Tab tab1 = new Tab("students");
			 Tab tab2 = new Tab("modules");
			 Tab tab3 = new Tab("records");
			 tab1.setContent(vstudent);
			 tab2.setContent(vmodule);
			 tab3.setContent(vrecords);
			 tabPane.getTabs().addAll(tab1, tab2, tab3);
			 tab1.setClosable(false);
			 tab2.setClosable(false);
			 tab3.setClosable(false);
			 tabPane.setSide(Side.LEFT);
			
			 VBox vfull = new VBox (10);
			 vfull.getChildren().addAll(tabPane,vbottom);
			 
			 
			primaryStage.setTitle("student list");
			Scene scene = new Scene(vfull,960,480);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
