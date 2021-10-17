package CourseWork.view;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class CourseView {
    private SimpleStringProperty title = new SimpleStringProperty();
    private LocalDate startDate;
    private LocalDate endDate;

    public CourseView(String title, LocalDate startDate, LocalDate endDate) {
        this.title = new SimpleStringProperty(title);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CourseView() {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
