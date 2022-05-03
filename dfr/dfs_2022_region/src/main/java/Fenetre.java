import javax.swing.*;

public class Fenetre extends JFrame {

    public Fenetre() {
        this.setSize(500,500);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton bouton = new JButton("clic moi :)");

        JPanel panneau = new JPanel();

        this.setContentPane(panneau);
        panneau.add(bouton);

        Evenement evenement = new Evenement();

        bouton.addActionListener(evenement);

        this.setVisible(true);
    }
}
