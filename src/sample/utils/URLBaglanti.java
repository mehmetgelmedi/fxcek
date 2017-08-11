package sample.utils;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by MEG on 12.07.2017.
 */
public class URLBaglanti implements Runnable {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private String konum;
    private LocalDate baslangic,bitis;
    private ObservableList<String> sehir;
    private ProgressIndicator progressIndicator;
    private String url;

    public URLBaglanti (String url, String konum, LocalDate baslangic, LocalDate bitis, ObservableList<String> sehir, ProgressIndicator progressIndicator){
        this.konum=konum;
        this.baslangic=baslangic;
        this.bitis=bitis;
        this.sehir=sehir;
        this.progressIndicator=progressIndicator;
        this.url=url;
    }

    public void get() {
        System.out.println("indirmelere baslandi.");
        System.out.println(konum);
        while (baslangic.compareTo(bitis) < 1) {
            for (String s : sehir) {
                //System.out.println(s.toString().toUpperCase());
                URLConnection baglanti = null;
                String hedefURL = url.replace("altsehir", s.toLowerCase().toString()).replaceAll("baslangic", baslangic.format(formatter).toString()).replaceAll("bitis",baslangic.format(formatter).toString()).replaceAll("sehir",s.toUpperCase().toString());
                System.out.println(hedefURL);
                try {
                    System.out.println(s +" "+ baslangic +" indirilmeye baslandi.");
                    InputStream in = new URI(hedefURL).toURL().openStream();
                    {
                        new File(konum +"/"+ baslangic.format(formatter).toString()).mkdirs();
                        Files.copy(in, Paths.get(konum +"/"+ baslangic.format(formatter).toString() +"/"+ s + ".pdf"), StandardCopyOption.REPLACE_EXISTING);
                        in.close();
                        System.out.println(s +" "+ baslangic +" indirilmesi tamamlandi.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            baslangic = baslangic.plusDays(1);
        }
        System.out.println("tum indirmeler tamamlandi.");
    }

    @Override
    public void run() {
        this.get();
        progressIndicator.setVisible(false);

    }
}