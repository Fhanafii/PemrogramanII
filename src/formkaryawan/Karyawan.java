/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package formkaryawan;
import javax.swing.*;

/**
 *
 * @author fhana
 */
public class Karyawan {

    /**
     * @param args the command line arguments
     */
        public static void main(String[] args) {
        // Run the Swing UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create the main frame
                JFrame mainFrame = new JFrame("Karyawan Form");
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.setSize(600, 400);

                // Create FormLihatKaryawan instance (for viewing the table)
                FormLihatKaryawan formLihatKaryawan = new FormLihatKaryawan(mainFrame, true);

                // Create FormKaryawan instance (your main form)
                FormKaryawan formKaryawan = new FormKaryawan();

                // Pass the formLihatKaryawan to FormKaryawan
                formKaryawan.setFormLihatKaryawan(formLihatKaryawan);

                // Create a JDesktopPane to hold the internal frame
                JDesktopPane desktopPane = new JDesktopPane();
                mainFrame.add(desktopPane);
                desktopPane.add(formKaryawan);
                formKaryawan.setVisible(true);

                // Show the main frame
                mainFrame.setVisible(true);
            }
        });
    }
}
