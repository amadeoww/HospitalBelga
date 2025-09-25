import views.HospitalBelgaInterface;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HospitalBelgaInterface().setVisible(true);
            }
        });
    }
}