package com.metadata;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;


public class Main extends JFrame implements ActionListener {
    TextArea metaField;
    Button chooseFileBtn,printBtn;
    File path;
    String details="";
    Main(){
        setBounds(200,100,500,500);
        setTitle("File Metadata");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        chooseFileBtn=new Button("Select File");
        chooseFileBtn.setBounds(20,20,100,36);
        chooseFileBtn.setFocusable(false);
        chooseFileBtn.setBackground(Color.darkGray);
        chooseFileBtn.addActionListener(this);
        chooseFileBtn.setForeground(Color.white);

        printBtn =new Button("Save");
        printBtn.setBounds(130,20,100,36);
        printBtn.setFocusable(false);
        printBtn.setBackground(Color.darkGray);
        printBtn.addActionListener(this);
        printBtn.setForeground(Color.white);

        metaField=new TextArea(details);
        metaField.setBounds(20,80,440,360);
        metaField.setBackground(Color.darkGray);
        metaField.setForeground(Color.white);
        metaField.setEditable(false);
        metaField.setFocusable(false);

        add(chooseFileBtn);
        add(printBtn);
        add(metaField);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==chooseFileBtn){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showOpenDialog(null);
            if(response==JFileChooser.APPROVE_OPTION){
                path=fileChooser.getSelectedFile().getAbsoluteFile();
                try {
                    BasicFileAttributes file = Files.readAttributes(Paths.get(String.valueOf(path)),BasicFileAttributes.class);
                    details="File Name : "+path.getName()+"\n"+
                            "File Size : "+file.size()+" KB\n" +
                            "File Path : "+path+"\n" +
                            "File Length : "+path.length()+"\n" +
                            "Can Write : "+path.canWrite()+"\n" +
                            "Can Read : "+path.canRead()+"\n" +
                            "Can Execute : "+path.canExecute()+"\n" +
                            "Creation Time : "+file.creationTime()+"\n" +
                            "Is Directory : "+file.isDirectory()+"\n" +
                            "Is Regular File : "+file.isRegularFile()+"\n" +
                            "Is Symbolic link : "+file.isSymbolicLink()+"\n" +
                            "Last Access Time : "+file.lastAccessTime()+"\n" +
                            "Last Modified Time : "+file.lastModifiedTime()+"\n";
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                metaField.setText(details);
            }

        }
        if (e.getSource() == printBtn) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file;
                PrintWriter fileOut = null;

                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(details);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } finally {
                    fileOut.close();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }

}
