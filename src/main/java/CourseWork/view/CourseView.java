package CourseWork.view;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class CourseView {
    private SimpleStringProperty title = new SimpleStringProperty();
    private SimpleStringProperty startDate= new SimpleStringProperty();
    private SimpleStringProperty endDate= new SimpleStringProperty();

    public CourseView(String title, String startDate, String endDate) {
        this.title = new SimpleStringProperty(title);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getStartDate() {
        return startDate.get();
    }

    public SimpleStringProperty startDateProperty() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public String getEndDate() {
        return endDate.get();
    }

    public SimpleStringProperty endDateProperty() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
    }
}
