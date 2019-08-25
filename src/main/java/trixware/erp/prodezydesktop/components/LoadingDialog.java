/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

/**
 *
 * @author Rajesh
 */
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class LoadingDialog extends JDialog {

private JLabel label = new JLabel("working");

public LoadingDialog(JFrame owner) {

    super(owner, ModalityType.APPLICATION_MODAL);

   // setUndecorated(true);
    add(label);
    pack();
    
    

    // move window to center of owner
    int x = owner.getX()
        + (owner.getWidth() - getPreferredSize().width) / 2;
    int y = owner.getY()
        + (owner.getHeight() - getPreferredSize().height) / 2;
    setLocation(x, y);



    repaint();


}

public void setMessage(String message) {
    label.setText(message);
}


public static void showLoadingDialog(JFrame owner) {
if (loadingDialog != null)
    loadingDialog.dispose();



loadingDialog = new LoadingDialog(owner);

new Thread() {
    @Override
    public void run() {
    loadingDialog.setVisible(true);
    };
}.start();

}

public static void setLoadingMessage(String message) {
loadingDialog.setMessage(message);
}


public static void hideLoadingDialog() {

if (loadingDialog != null) {
    loadingDialog.setVisible(false);
    loadingDialog.dispose();
    loadingDialog = null;
}


}



private static LoadingDialog loadingDialog;

/*
public static void load(String[] args) {


final JFrame mainWindow = new JFrame("Main frame");
mainWindow.setLayout(new GridLayout(3, 3));


for (int i = 1; i <= 9; i++) {

    final int workTime = i;

    JButton workButton = new JButton("work for " + i + " second");

    //action listener, which had to show loading dialog and countdown seconds before finish
    workButton.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {

        showLoadingDialog(mainWindow);

        for (int j = 0; j < workTime; j++)
        try {

            // ... do some work here

            setLoadingMessage("remain " + (workTime - j)
                + " second(s)");
            loadingDialog.repaint();
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        hideLoadingDialog();
    }
    });


    mainWindow.add(workButton);

}

mainWindow.pack();
mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
mainWindow.setLocationRelativeTo(null);



mainWindow.setVisible(true);

}*/






}