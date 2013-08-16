package jtextviewer;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;

/**
 * @author Abednego Setyawan
 */
public class JTextViewer implements ActionListener {

    private JFrame frame;
    private JFileChooser jfc;
    private JMenuBar menubar;
    private JMenu fileMenu, editMenu, helpMenu;
    private JMenuItem openMenu, newMenu, saveMenu, closeTabMenu, closeTabPopMenu, saveTabPopMenu, exitMenu, concatMenu, removeAllMenu, aboutMenu;
    private final JTabbedPane tab = new JTabbedPane();    
    private IOStream ioStream = new IOStream();
    private Filter filter = new Filter();
    private static int count = 1;    
    public JTextArea txtArea;

    public JTextViewer() {
        buildForm();
    }

    public static void main(String[] args) {
        new JTextViewer();
    }

    private void buildForm() {

        frame = new JFrame("JTextViewer");
        menubar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        helpMenu = new JMenu("Help");
        openMenu = new JMenuItem("Open");
        newMenu = new JMenuItem("New");
        saveMenu = new JMenuItem("Save");
        exitMenu = new JMenuItem("Exit");
        concatMenu = new JMenuItem("Combine");
        closeTabMenu = new JMenuItem("Close");
        removeAllMenu = new JMenuItem("Remove All Tabs");
        aboutMenu = new JMenuItem("About");

        Container c = new Container();
        c = frame.getContentPane();
        drawTabbedPane("Untitled");
        c.add(tab);

        fileMenu.add(openMenu);
        fileMenu.add(newMenu);
        fileMenu.add(saveMenu);
        fileMenu.add(exitMenu);
        editMenu.add(concatMenu);
        editMenu.add(closeTabMenu);
        editMenu.add(removeAllMenu);
        helpMenu.add(aboutMenu);      

        openMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        newMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        saveMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        exitMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        removeAllMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.SHIFT_MASK|InputEvent.CTRL_MASK));

        menubar.add(fileMenu);
        menubar.add(editMenu);
        menubar.add(helpMenu);
        frame.setJMenuBar(menubar);

        openMenu.addActionListener(this);
        newMenu.addActionListener(this);
        saveMenu.addActionListener(this);
        exitMenu.addActionListener(this);
        concatMenu.addActionListener(this);
        removeAllMenu.addActionListener(this);
        closeTabMenu.addActionListener(this);
        aboutMenu.addActionListener(this);

        frame.setVisible(true);
        frame.pack();
        frame.setResizable(true);
        frame.setLocation(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }    
   
    private JPopupMenu tabPopupMenu() {
        JPopupMenu tabPop = new JPopupMenu();
        closeTabPopMenu = new JMenuItem("Close");
        saveTabPopMenu = new JMenuItem("Save");
        tabPop.add(closeTabPopMenu);
        tabPop.add(saveTabPopMenu);
        closeTabPopMenu.addActionListener(this);
        saveTabPopMenu.addActionListener(this);
        tabPop.setLightWeightPopupEnabled(true);   
        tabPop.addPropertyChangeListener(new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt) {
                 if (tab.getSelectedIndex()<0) {
                    closeTabPopMenu.setEnabled(false);
                    saveTabPopMenu.setEnabled(false);
                } else {
                    closeTabPopMenu.setEnabled(true);
                    saveTabPopMenu.setEnabled(true);
                }
            }
        });     
        return tabPop;
    }

    public void drawTabbedPane(String tabTitle) {
        JScrollPane scroll = new JScrollPane();
        txtArea = new JTextArea(20, 30);
        scroll.setViewportView(txtArea);
        tab.addTab(tabTitle, scroll);
        tab.setComponentPopupMenu(tabPopupMenu());        
        tab.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                 if (tab.getSelectedIndex()<0) {
                    closeTabMenu.setEnabled(false);
                    saveMenu.setEnabled(false);
                    removeAllMenu.setEnabled(false);
                } else {
                    closeTabMenu.setEnabled(true);
                    saveMenu.setEnabled(true);
                    removeAllMenu.setEnabled(true);
                }
            }
        });
    }

    public void actionPerformed(ActionEvent evt) {
        JComponent jc = (JComponent) evt.getSource();

        if (jc == openMenu) {           
            jfc = new JFileChooser();
            jfc.setFileFilter(filter);
            jfc.setDialogTitle("Open Text File");
            int opt = jfc.showOpenDialog(frame);
            if (opt == JFileChooser.APPROVE_OPTION) {                              
                    drawTabbedPane(jfc.getSelectedFile().getName());
                    ioStream.open(frame, jfc, txtArea, frame);               
            }
        } else if (jc == newMenu) {
            drawTabbedPane("Untitled" + count);
            count++;
        } else if (jc == saveMenu || jc == saveTabPopMenu) {
            jfc = new JFileChooser();           
            int opt = jfc.showSaveDialog(frame);
            if (opt == JFileChooser.APPROVE_OPTION) {
                boolean displayAfterSave = true;
                ioStream.saveAs(frame, jfc, txtArea, displayAfterSave);
                if (displayAfterSave) {
                    drawTabbedPane(jfc.getSelectedFile().getName());
                    ioStream.open(frame, jfc, txtArea, frame);                    
                    tab.remove(tab.getSelectedIndex());
                }
            }
        } else if (jc == concatMenu) {
            Concatenation con = new Concatenation();
            con.showDialog();
        } else if (jc == closeTabMenu || jc == closeTabPopMenu) {
            if (tab.getTabCount() == 1) {
                tab.removeTabAt(0);
            } else {
                tab.removeTabAt(tab.getSelectedIndex());
            }
        } else if (jc == removeAllMenu) {
            tab.removeAll();
        } else if (jc == exitMenu) {
            System.exit(0);
        } else if (jc == aboutMenu) {
              new AboutDialog().setVisible(true);
        }
    }    
}
