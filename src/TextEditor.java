import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener {

    //This is like the CSS all the modifications are here

    //isipin mo DOM Manipulation 'to

    //Instantiate text area
    JTextArea textArea;
    //For scrolling purposes
    JScrollPane scrollPane;
    //for fonts
    JSpinner fontSizeSpinner;
    //serves as text label
    JLabel fontLabel;

    JButton fontColorButton;
    JComboBox fontBox;

    //Menu

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;

    //Editor Grid
    TextEditor() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Note Taker");
        this.setSize(600, 600);
        this.setLayout(new FlowLayout());
        //default position into center
        this.setLocationRelativeTo(null);

        //declaration ng text area kung saan magtatype
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Courier", Font.PLAIN, 40));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 550));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fontLabel = new JLabel("Font: ");

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.setAlignmentY(100);
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(e -> textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue())));

        fontColorButton = new JButton("Color: ");
        fontColorButton.addActionListener(this);

        //Declare default font-set for fontBox
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox = new JComboBox<>(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");


        //----Menu-----


        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        //actionListeners
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);


        //Inside the Menu
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        //----Menu-----


        //lahat ng gusto mo ilagay sa GUI declare mo dito
        this.setJMenuBar(menuBar);
        this.add(fontLabel);
        this.add(fontSizeSpinner);
        this.add(fontColorButton);
        this.add(fontBox);
        this.add(scrollPane);
        //ito yung magtrigger if gusto mo sila lumitaw
        this.setVisible(true);
    }


    //Application Methods
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == fontColorButton) {
            JColorChooser colorChooser = new JColorChooser();
            //dialog box
            Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
            textArea.setForeground(color);
        }

        if (e.getSource() == fontBox) {
            textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
        }

        if (e.getSource() == openItem) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {

                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;

                try {
                    fileIn = new Scanner(file);
                    if (file.isFile()) {

                        while (fileIn.hasNextLine()) {

                            String line = fileIn.nextLine() + "\n";
                            //append
                            textArea.append(line);
                        }


                    }
                } catch (FileNotFoundException faulty) {
                    faulty.printStackTrace();
                } finally {
                    fileIn.close();
                }
            }
        }
        if (e.getSource() == saveItem) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                //create a new file

                File file;
                PrintWriter fileout = null;

                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileout = new PrintWriter(file);
                    fileout.println(textArea.getText());
                } catch (FileNotFoundException error) {
                    error.printStackTrace();
                } finally {

                    fileout.close();
                }


            }
        }

        if (e.getSource() == exitItem) {
            System.exit(0);
        }


    }
}
