package jtextviewer;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class Concatenation {

    private JLabel label, opt1Label, opt2Label;
    private JTextField fileInput1, fileInput2;
    private JButton optBtn1, optBtn2, combineBtn, cancelBtn, copyBtn, saveBtn;
    private String currentDir;
    private JDialog dlg;
    private JTextArea textsCombined;
    private JFileChooser jfc;
    private Dimension dim10 = new Dimension(0, 10);
    private Filter filter = new Filter ();

    protected void showDialog() {

        Container conpane = new Container();
        textsCombined = new JTextArea(20, 20);
        JScrollPane txtScroll = new JScrollPane(); 
        txtScroll.setViewportView(textsCombined);	       
        dlg = new JDialog();        
        conpane = dlg.getContentPane();
        label = new JLabel("Combine two text files into one.");
        combineBtn = new JButton("Combine");
        conpane.setLayout(new BoxLayout(conpane, BoxLayout.Y_AXIS));
        conpane.add(label);
        conpane.add(centerPanel());
        conpane.add(combineBtn);        
        conpane.add(Box.createRigidArea(dim10));
        conpane.add(txtScroll);        
        conpane.add(Box.createRigidArea(dim10));
        conpane.add(bottomPanel());
        conpane.add(Box.createRigidArea(dim10));

        combineBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    concatenate();
                } catch (IOException ie) {
                    JOptionPane.showMessageDialog(dlg, "IO Error", "IO Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dlg.setTitle("Combine");
        dlg.setResizable(true);
        dlg.setVisible(true);
        dlg.pack();
        dlg.setLocation(300, 100);
        dlg.setAlwaysOnTop(true);
        dlg.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

    private JPanel centerPanel() {
        JPanel panel = new JPanel();
        opt1Label = new JLabel("First textfile:");
        opt2Label = new JLabel("Second textfile:");
        fileInput1 = new JTextField(20);
        fileInput2 = new JTextField(20);
        fileInput1.setEditable(false);
        fileInput2.setEditable(false);
        optBtn1 = new JButton("Browse");
        optBtn1.setSize(10, 5);
        optBtn2 = new JButton("Browse");
        optBtn2.setSize(10, 5);

        optBtn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                browse(fileInput1);
            }
        });

        optBtn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                browse(fileInput2);
            }
        });

        panel.add(opt1Label);
        panel.add(fileInput1);
        panel.add(optBtn1);
        panel.add(opt2Label);
        panel.add(fileInput2);
        panel.add(optBtn2);
        panel.setLayout(new GridLayout(2, 3));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return panel;
    }

    private JPanel bottomPanel() {
        JPanel botPanel = new JPanel();
        saveBtn = new JButton("Save as file");
        cancelBtn = new JButton("Cancel");
        botPanel.add(saveBtn);
        botPanel.add(cancelBtn);
        botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.X_AXIS));
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jfc = new JFileChooser();
                int opt = jfc.showSaveDialog(dlg);
                if (opt == JFileChooser.APPROVE_OPTION) {
                    new IOStream().save(dlg, jfc, textsCombined);
                }
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dlg.dispose();
            }
        });
        return botPanel;
    }

    private void concatenate() throws FileNotFoundException {

        if (fileInput1.getText().equalsIgnoreCase("") || fileInput2.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(dlg, "Please select two files");
        } else {
            SequenceInputStream inputStream;
            FileInputStream file1 = new FileInputStream(fileInput1.getText());
            FileInputStream file2 = new FileInputStream(fileInput2.getText());
            inputStream = new SequenceInputStream(file1, file2);
            boolean eof = false;
            int byteCount = 0;
            String str = "";

            while (!eof) {
                try {
                    int c = inputStream.read();
                    if (c == -1) {
                        eof = true;
                    } else {
                        str += (char) c;
                        ++byteCount;
                    }
                } catch (IOException ie) {
                    JOptionPane.showMessageDialog(dlg, "IO Error", "IO Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            try {
                textsCombined.setText(str);
                inputStream.close();
                file1.close();
                file2.close();
            } catch (IOException ie) {
                JOptionPane.showMessageDialog(dlg, "Cannot combine these files, IO Error", "IO Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JFileChooser browse(JTextField target) {
        jfc = new JFileChooser();
        jfc.setFileFilter(filter);
        int opt = jfc.showOpenDialog(dlg);
        if (opt == JFileChooser.APPROVE_OPTION) {
            String fileAbsolutePath = jfc.getSelectedFile().getAbsolutePath();
            target.setText(fileAbsolutePath);
        }
        return jfc;
    }
}
