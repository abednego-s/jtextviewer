package jtextviewer;

import java.awt.*;
import javax.swing.*;

class AboutDialog extends JDialog {
    
private JLabel label;
private Dimension dim15 = new Dimension(0, 15);

    public AboutDialog() {
        
        Container con = new Container();
        con = this.getContentPane();       
        label = new JLabel("JTextViewer");        
        label.setFont(new Font("Tahoma", 1, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        con.add(label);
        con.add(Box.createRigidArea(dim15));
        con.add(ctrPanel());           
        con.add(Box.createRigidArea(dim15));
        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
        setTitle("About");       
        setResizable(false);        
        setLocation(300, 300);
        pack();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    private JPanel ctrPanel(){
       
        JPanel ctrPane = new JPanel();    
        label = new JLabel("JTextViewer 1.0");
        ctrPane.add(label);
        label = new JLabel("Programmed by: Abednego Setyawan");
        ctrPane.add(label); 
        ctrPane.add(Box.createRigidArea(dim15));        
        ctrPane.setBorder(BorderFactory.createTitledBorder("Profile"));       
        ctrPane.add(Box.createGlue());
        ctrPane.setLayout(new BoxLayout(ctrPane, BoxLayout.Y_AXIS));
        return ctrPane;
        
    }
}
