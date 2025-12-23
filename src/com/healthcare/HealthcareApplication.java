package com.healthcare;

import com.healthcare.controller.HealthcareController;
import com.healthcare.view.MainView;
import javax.swing.SwingUtilities;
import java.io.File;


public class HealthcareApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HealthcareController controller = new HealthcareController();
            
            String dataDirectory = System.getProperty("user.dir") + File.separator + "data";
            File dataDir = new File(dataDirectory);
            if (dataDir.exists()) {
                controller.loadData(dataDirectory);
            }
            
            MainView mainView = new MainView(controller);
            mainView.setVisible(true);
        });
    }
}



