package com.developers.EduManage.Controller;

import com.developers.EduManage.db.database;
import com.developers.EduManage.model.Student;
import com.developers.EduManage.view.tm.StudentTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StudentFormController {
    public  AnchorPane CONTEXT  ;
    public TextField txtxid;
    public TextField txtxname;
    public DatePicker txtdob;
    public TextField txtxaddress;
    public TableView<StudentTm> tblview;
    public TableColumn coloption;
    public TableColumn coladddress;
    public TableColumn cldob;
    public TableColumn clfullname;
    public TableColumn clid;


    public void initialize(){
            coladddress.setCellFactory(new PropertyValueFactory<>("address"));
            cldob.setCellFactory(new PropertyValueFactory<>("dob"));
            coloption.setCellFactory(new PropertyValueFactory<>("btn"));
            clid.setCellFactory(new PropertyValueFactory<>("id"));
            clfullname.setCellFactory(new PropertyValueFactory<>("fullname"));


        SetStudentId();
        setTableData();
        tblview.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            setdata(newValue);

        }));
    }

    private void setdata(StudentTm tm) {
        txtxid.setText(tm.getId());
        txtxname.setText(tm.getFullname());
        txtxaddress.setText(tm.getAddress());
        txtdob.setValue(LocalDate.parse(tm.getDob(),DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        Labeled btn = null;
        btn.setText("updata student");
    }

    private void setTableData() {
        ObservableList<StudentTm> oblist= FXCollections.observableArrayList();
        for (Student st:database.studenttable){
            Button btn=new Button("delete");
            StudentTm tm= new StudentTm(
            st.getAddress(),
            new SimpleDateFormat("yyyy-MM-dd").format(st.getDate()),
                    st.getFullname(),
            st.getStudentid(),
            btn
            );


        }
        tblview.setItems(oblist);
    }


    public void saveonaction(ActionEvent actionEvent) {
        Student student= new Student(
                txtdob.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                txtxaddress.getText(),
                txtxname.getText(),
                txtxid.getText()
        );

        database.studenttable.add(student);
        SetStudentId();
        setTableData();
        new Alert(Alert.AlertType.INFORMATION,"student saved").show();
    }
    private void SetStudentId(){ // Autogenerated student code of the sutudent==>(s-1)
        if (!database.studenttable.isEmpty()){
            Student laststudent=database.studenttable.get(database.studenttable.size()-1);
            String lastId=laststudent.getStudentid();
            String[] splitdata =lastId.split("-");
            String lastintidernumberasstring=splitdata[1];
            int lastintidasint=Integer.parseInt(lastintidernumberasstring);
            lastintidasint++;
            String generatedstudentid="s-"+lastintidasint;
            txtxid.setText(generatedstudentid);


        }
        else {
            txtxid.setText("s-1");
        }


    }
    private void clear(){
        txtxid.clear();
        txtxname.clear();
        txtxaddress.clear();
        txtdob.setValue(null);

    }
}
