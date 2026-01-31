package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TreatmentDatabase {

    private static TreatmentDatabase instance;
    private final ObservableList<Treatments> treatments;

    private TreatmentDatabase() {
        treatments = FXCollections.observableArrayList();
    }

    public static TreatmentDatabase getInstance() {
        if (instance == null) {
            instance = new TreatmentDatabase();
        }
        return instance;
    }

    public ObservableList<Treatments> getTreatments() {
        return treatments;
    }

    public void addTreatment(Treatments treatment) {
        treatments.add(treatment);
    }
}
