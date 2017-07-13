package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import sample.main.Main;
import sample.utils.JsonAyristir;
import sample.utils.URLBaglanti;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private DatePicker baslangicTarihi;

    @FXML
    private DatePicker bitisTarihi;

    @FXML
    private ListView<String> sehirListesi;

    @FXML
    private ProgressIndicator progress;

    private ObservableList<String> sehirler;
    private ObservableList<String> sehir;
    private File dir;
    private static final String jsonDosyasi = "src/iller.json";
    private LocalDate baslangic, bitis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("MainController başlatildi.");
        sehirler = FXCollections.observableList(new JsonAyristir().sehirleriGetir(jsonDosyasi));
        sehirListesi.setItems(sehirler);
        sehirListesi.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        progress.setVisible(false);
        baslangicTarihi.setValue(LocalDate.now().minusDays(1));
        bitisTarihi.setValue(LocalDate.MAX.now().minusDays(1));
        //System.out.println(sehirler);
    }

    public void btnKonumClick() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Konum secin");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        dir = chooser.showDialog(Main.stage);
        System.out.println(dir.getAbsolutePath().replace("\\", "/"));
    }

    public void btnGetirClick() {
        //mbox("Test","TEst", Alert.AlertType.INFORMATION);
        baslangic = baslangicTarihi.getValue();
        bitis = bitisTarihi.getValue();
        if (sehir != null && baslangic != null && bitis != null && dir != null) {
            if (sehir.size() > 0) {
                System.out.println(baslangic.toString() + " - " + bitis.toString() + " / " + sehir);
                if (baslangic.compareTo(bitis) < 1) {
                    System.out.println("baslangic<bitis ya da baslangic=bitis  baslangic.adddays +1");
                    progress.setVisible(true);
                    new Thread(new URLBaglanti(dir.getAbsolutePath().replace("\\", "/"), baslangic, bitis, sehir, progress)).start();
                } else
                    mbox("Uyari", "Tarih araligini yanlis girdiniz.", Alert.AlertType.WARNING);
            } else
                mbox("Uyari", "Sehir secmediniz.", Alert.AlertType.WARNING);
        } else
            mbox("Uyari", "Bir seyler ters gitti.", Alert.AlertType.WARNING);
    }

    public void listMouseClick() {
        //if sayac > 1 ? array : var
        sehir = sehirListesi.getSelectionModel().getSelectedItems();
        //String yeniSehir = sehir.replaceAll("\"","").replaceAll("\\[","").replaceAll("\\]","");
        //sehir=yeniSehir;
        //System.out.println(sehir);
    }

    public static void mbox(String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("fx bildirim");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
