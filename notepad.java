
/**
 * @Java_application_Programming_Assignement_1
 * @Theory prof: Prof. Surbhi
 * @Lab prof: prof. cormier
 * @section: 302
 * @due: 14/03/2021
 * @author Meech Lamirande Kabuasa
 * @studentID Lami0039
 * @Version 1.0
 */


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * @author Meech Lamirande Kabuasa
 * @Version 1.0
 */
public class notepad extends JFrame implements ActionListener {

    JFrame frame;
    JViewport view;
    JScrollPane editorScrollPane;
    JTextArea editorAreaText;
    JMenu file, Format, submenu;
    JMenuItem New, Open, Save, Edit, Exit;
    JCheckBoxMenuItem Bold, Italic;
    JRadioButtonMenuItem Small, Medium, Large;
    ButtonGroup buttonGroup;
    JMenuBar menuBar;
    ImageIcon img;
    Boolean saved = true;

    /**
     * the Main driver method of the program handles the main frame.
     * @param args
     * @throws IOException
     * @author Meech Lamirande kabuasa
     * @version 1.0
     */
    public static void main(String[] args) throws IOException {
        notepad notepad = new notepad();
        notepad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        notepad.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closing notepad");
                super.windowClosing(e);
            }
        });
        notepad.setVisible(true);


    }

    /**
     * Note Pad constructor, builds the frame, editor area, functionalities of the program.
     * @version 1.0
     * @author Meech Lamirande Kabuasa
     *         lami0039
     */
    public notepad() {


        frame = new JFrame();
        setTitle("Note Editor");
        setResizable(true);
        setSize(1020, 620);

        //gridbag layout
        GridBagConstraints grid = new GridBagConstraints();
        grid.weighty = 1;
        grid.weightx = 1;
        grid.fill = GridBagConstraints.BOTH;
        grid.gridx = 0;
        grid.gridy = 0;
        //NotePadEditor

        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        editorAreaText = new JTextArea(40, 20);
        editorAreaText.setLineWrap(true);
        editorAreaText.setFont(font);
        editorAreaText.setWrapStyleWord(true);
        editorAreaText.setLineWrap(true);
        //setting the area Text

        editorScrollPane = new JScrollPane(editorAreaText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // view = new JViewport();
        editorScrollPane.setViewportView(editorAreaText);
        editorScrollPane.setPreferredSize(getMinimumSize());
        // editorScrollPane.setViewportView(view);

        //gridbag layout;
        setLayout(new GridBagLayout());
        //add(editorAreaText,grid);
        add(editorScrollPane, grid);


        // setLayout(new BorderLayout());
        //intializing my menus
        menuBar = new JMenuBar();
        file = new JMenu("File");
        Format = new JMenu("Format");

        //setting the radio menu;
        submenu = new JMenu("Size");
        //TODO TESTER CODE
        Small = new JRadioButtonMenuItem("Small");
        Medium = new JRadioButtonMenuItem("Medium");
        Large = new JRadioButtonMenuItem("Large");

        //setting the JmenuItems;
        New = new JMenuItem("New");
        Open = new JMenuItem("Open");
        Save = new JMenuItem("Save");
        Edit = new JMenuItem("Edit");
        Exit = new JMenuItem("Exit");
        Bold = new JCheckBoxMenuItem("Bold");
        Italic = new JCheckBoxMenuItem("Italic");

       // buttonGroup = new ButtonGroup(Small,Medium,Large);

        //setting the accelerator keys;
        New.setAccelerator(shortcut(KeyEvent.VK_N));
        Open.setAccelerator(shortcut(KeyEvent.VK_O));
        Save.setAccelerator(shortcut(KeyEvent.VK_S));
        Edit.setAccelerator(shortcut(KeyEvent.VK_E));
        Bold.setAccelerator(shortcut(KeyEvent.VK_B));
        Italic.setAccelerator(shortcut(KeyEvent.VK_I));
        Small.setAccelerator(shortcut(KeyEvent.VK_1));
        Medium.setAccelerator(shortcut(KeyEvent.VK_2));
        Large.setAccelerator(shortcut(KeyEvent.VK_3));

        /**
         * Action Listener New
         * purpose: once triggered this event checks the see if you wish to save the current file you're working on. if
         * the users cancels nothing happens and breaks;
         * if the user selects no? : the file is wiped and a the editor feild is cleared
         * if the user saves? : the file is written just as if it were saved.
         * @version 1.0
         * @author Meech Lamirande Kabuasa
         *         lami0039
         */
        New.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //set text feild to nothing when it exitst when it exists
                System.out.println("New Trigger");

                Object[] options = {"save", "don't save", "Cancel"};
                int option = JOptionPane.showOptionDialog(frame, "Save before opening new file?", "save?",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);

                //TODO if text empty reject this function.
                System.out.println(option + "<--- option");

                switch (option) {
                    case 0:
                        Save.doClick();
                    case 1:
                        editorAreaText.setText("");
                        setTitle("Notepad - new");
                    case 2:
                        break;
                }

            }
        });
        /**
         * Action Listener Open
         * purpose: this event will look for a file and if the file object is empty it displays a message "this file can't be found" and nothing happens
         * if the file is found a buffer object reads the file line by line and writes appends the line in the text pad editor
         * @version 1.0
         * @author Meech Lamirande Kabuasa
         *         lami0039
         */
        Open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //set text feild to nothing when it exitst when it exists
                String filename = "";
                String Directory = "";
                JFileChooser fileChooser = new JFileChooser();
                FileDialog files = new FileDialog(frame, "open", FileDialog.LOAD);
                files.setVisible(true);
                filename = files.getFile();
                Directory = files.getDirectory();
                if (new File(Directory + filename).exists()) {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(new File(Directory + filename)));
                        editorAreaText.setText("");
                        String Line = null;
                        while ((Line = reader.readLine()) != null) {
                            editorAreaText.append(Line + "\n");
                        }
                        reader.close();
                    } catch (IOException Exception) {
                        //TODO MUST WRITE CODE HERE TO SHOW THAT THE FILE DOESN'T EXIT.
                        JOptionPane.showMessageDialog(null, "File doesn't Exist");
                        // FileDialog savefile = new FileDialog(frame,"File Doesn't exit",FileDialog.);
                        //  Exception.printStackTrace();
                    }
                    setTitle("Notepad - " + filename);
                } else {
                    JOptionPane.showMessageDialog(null, "File doesn't Exist");
                }
                //fileChooser.accept();
                System.out.println("Open trigger");
            }
        });

        /**
         * Action Listener save
         * purpose: this event listener once triggered looks for a file if it exists, windows prompts if the user wishes to overwrite it!
         * the logic checks for extensions - no file input - reprompts the user if they want to save as text file.
         * once overwritten the frame title changes.
         * @version 1.0
         * @author Meech Lamirande Kabuasa
         *         lami0039
         */
        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //set text  feild to nothing when it exitst when it exists
                String directory = "";
                String filename = "";
                FileDialog savefile = new FileDialog(frame, "save", FileDialog.SAVE);
                savefile.setVisible(true);
                    filename = savefile.getFile();
                    System.out.println("****************************************************************"+filename.length());
                    if (filename.length()==0){
                        JOptionPane.showMessageDialog(null, "File not saved.");
                    }else if(savefile.getFile().endsWith(".txt")){
                    JOptionPane.showMessageDialog(null, "Illegal Extension");
                    }else {
                        if(!savefile.getFile().endsWith(".txt")){
                            Object[] options = {"Yes", "No"};
                            int option = JOptionPane.showOptionDialog(frame, "Save as .txt?", "save?",
                                    JOptionPane.YES_NO_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    options,
                                    options[1]);
                            if (option==1){
                                filename+=".txt";
                            }
                        }
                        directory = savefile.getDirectory();
                        setTitle("Notepad - " + filename);
                        try {
                            FileWriter writer = new FileWriter(directory + filename);
                            writer.write(editorAreaText.getText());
                            writer.close();
                        } catch (IOException exception) {
                            System.out.println();
                            // exception.printStackTrace();
                        }
                    }
                System.out.println("Save Trigger");
            }
        });

        /**
         * Action Listener edit.
         * purpose: 0 purpose in this code.
         * version 1.0
         * @author Meech Lamirande Kabuasa
         *         lami0039
         */
        Edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("No requirements for Edit yet");
            }
        });

        /**
         * Action Listener Exit
         * purpose: gracefully tells the program it's quitting
         * @version 1.0
         * @author Meech Lamirande Kabuasa
         *         lami0039
         */
        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exiting application");
                System.exit(0);
            }
        });

        /**
         * Action Listener Bold
         * purpose: triggers the font to change to Bold
         *  if the font is already bold it un-bolds it.
         *  it then gets text area then sets the font appropriately
         *   @version 1.0
         *   @author Meech Lamirande Kabuasa
         *           lami0039
         */
        Bold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //set text feild to nothing when it exitst when it exists
                if (!editorAreaText.getFont().isBold()) {
                    if (editorAreaText.getFont().isItalic()) {
                        editorAreaText.setFont(editorAreaText.getFont().deriveFont(Font.ITALIC | Font.BOLD));
                        Italic.setSelected(false);
                    }
                    editorAreaText.setFont(editorAreaText.getFont().deriveFont(Font.BOLD));
                } else {
                    editorAreaText.setFont(editorAreaText.getFont().deriveFont(Font.PLAIN));
                }
                System.out.println("Bold Trigger");
            }
        });

        /**
         * Action Listener Italics
         * purpose: triggers the font to change to italic
         * if the font is already italics it un-italicizes.
         * gets the text area and sets the font that it.
         *  @version 1.0
         *  @author Meech Lamirande Kabuasa
         *          lami0039
         */
        Italic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!editorAreaText.getFont().isItalic()) {
                    if (editorAreaText.getFont().isBold()) {
                        editorAreaText.setFont(editorAreaText.getFont().deriveFont(Font.ITALIC + Font.BOLD));
                        Bold.setSelected(false);
                    }
                    editorAreaText.setFont(editorAreaText.getFont().deriveFont(Font.ITALIC));
                } else {
                    editorAreaText.setFont(editorAreaText.getFont().deriveFont(Font.PLAIN));
                }
                System.out.println("Italic trigger");
                //set text feild to nothing when it exitst when it exists

            }
        });

        /**
         * Action Listener medium
         * purpose: triggers the font size to be large.
         * setfont to a different font specified by the size 14
         * @version 1.0
         * @author Meech Lamirande Kabuasa
         *         lami0039
         */
        Medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorAreaText.setFont(editorAreaText.getFont().deriveFont((float) 14));
                System.out.println("Medium trigger");
                if(Large.isSelected()){
                    Large.setSelected(false);
                }
                if(Small.isEnabled()){
                    Small.setSelected(false);
                }
            }
        });

        /**
         * Action Listener small
         * purpose: triggers the font size to be large.
         * setfont to a different font specified by the size 12
         * @version 1.0
         * @author Meech Lamirande Kabuasa
         *         lami0039
         */
        Small.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorAreaText.setFont(editorAreaText.getFont().deriveFont((float) 12));
                if(Large.isSelected()){
                    Large.setSelected(false);
                }
                if(Medium.isSelected()){
                    Medium.setSelected(false);
                }
                System.out.println("Small trigger");
            }
        });

        /**
         * Action Listener large
         * purpose: triggers the font size to be large.
         * setfont to a different font specified by the size 20
         * @author Meech Lamirande Kabuasa
         *          lami0039
         */
        Large.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorAreaText.setFont(editorAreaText.getFont().deriveFont((float) 20));
                System.out.println("Large Trigger");
                if(Small.isSelected()){
                    Small.setSelected(false);
                }
                if(Medium.isSelected()){
                    Medium.setSelected(false);
                }
            }
        });

        Small.doClick();
        //ADD TO FILE MENU
        file.add(New);
        file.add(Open);
        file.add(Save);
        file.add(Edit);
        file.add(Exit);
        //ADD TO FORMAT MENU
        Format.add(Bold);
        Format.add(Italic);
        Format.add(submenu);
        //ADD SUBMENU TO FORMAT
        submenu.add(Small);
        submenu.add(Medium);
        submenu.add(Large);
        //ADD BOTH MENU'S TO MENU BAR
        menuBar.add(file);
        menuBar.add(Format);
        //SET THE BAR
        setJMenuBar(menuBar);
        //SET the EDITOR TEXT AREA

        setIconImage(new ImageIcon(("img.png")).getImage());
        frame.pack();

    }
    /**
        name: toggleFonts
        purpose: quick accessor method to return a font style.
        @param i,j
        @return Font;
        Author@lami0039
     */
    public Font toggleFonts(int i, int j) {
        return new Font("Times New Roman", i, j);
    }

    /**
     name: toggleFonts
     purpose: quick accessor method to return a keystroke style.
     @param key
     @return Keystroke;
     Author@lami0039
     */
    public KeyStroke shortcut(int key) {
        return KeyStroke.getKeyStroke(
                key, KeyEvent.CTRL_DOWN_MASK
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}

