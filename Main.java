import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/***
 * @author z.barcoe
 */

public class Main {

    private FileReader fr;
    private Gui gui;
    private File file;
    private File patternFile;
    private String[][] pattern;

    /**
     *
     */

    private Main() {
        gui = new Gui();
        fr = new FileReader();

        gui.selectFile.addActionListener((event) -> fileSelector());
        gui.selectPattern.addActionListener((event) -> {
            try {
                patternFile = patternSelector();
                patternLoader(patternFile);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });
        gui.runSearch.addActionListener((event) -> runSearch());
        gui.saveText.addActionListener((event) -> save());
        gui.clearText.addActionListener((event) -> clear());
        gui.exit.addActionListener((event) -> exit());

    }

    public static void main(String[] args) {
        new Main();
    }

    /**
     * @param file     String array of the files byte codes
     * @param fileName the name of the current file
     * @param patterns
     * @param tabs
     */

    private void search(String[] file, String fileName, String[][] patterns, String tabs) {
        String[] results = new PatternSearcher().patternSearch(file, patterns);
        int numResults = 0;

        gui.text.append(String.format("%sFilename: %s \n", tabs, fileName));
        if (!results[0].equals("No patterns found")) numResults = results.length;
        gui.text.append(String.format("%sNumber Of Matches: %s\n", tabs, numResults));

        for (String result : results) {
            if (result != null) gui.text.append(tabs + result + "\n");
        }

    }

    /**
     * @param selectedFile
     * @param patterns
     * @param tabs
     * @throws IOException
     */

    private void directoryCheck(File selectedFile, String[][] patterns, String tabs) throws IOException {
        if (selectedFile.isDirectory()) {
            File[] fileArray = selectedFile.listFiles();
            assert fileArray != null;
            gui.text.append(String.format("%sDirectory: %s (%s files)\n", tabs, selectedFile.getName(), fileArray.length));
            gui.text.append(String.format("%sPattern File: %s \n", tabs, this.patternFile.getName()));
            gui.text.append(tabs + "------------------\n");
            tabs += "\t";
            for (File file : fileArray) {
                directoryCheck(file, patterns, tabs);
                gui.text.append(tabs + "------------------\n");
            }
        } else {
            fileHandler(selectedFile, patterns, tabs);
        }
    }

    /**
     * @param file
     * @param patterns
     * @param tabs
     * @throws IOException
     */

    private void fileHandler(File file, String[][] patterns, String tabs) throws IOException {
        byte[] bytes = fr.genByteArray(file);
        String[] stringArray = fr.genStringArray(bytes);
        search(stringArray, file.getName(), patterns, tabs);
    }

    /**
     * @param file
     * @throws Exception
     */

    private void patternLoader(File file) throws Exception {
        String[][] pattern = fr.patternFileReader(file);
        gui.patternDisplay.setText("");
        for (String[] line : pattern) {
            for (String bytes : line) {
                gui.patternDisplay.append(bytes + " ");
            }
            gui.patternDisplay.append("\n");
        }
        this.pattern = pattern;
    }

    /**
     *
     */

    private void fileSelector() {

        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Select File|Directory");
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        fc.showOpenDialog(new JPanel());
        this.file = fc.getSelectedFile();
        gui.setTitle(String.format("Pattern Search : %s", this.file.getName()));
    }

    /**
     * @return
     */

    private File patternSelector() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Select Pattern Fie");

        fc.showOpenDialog(new JPanel());

        return fc.getSelectedFile();
    }

    /**
     *
     */

    private void runSearch() {

        if (this.file != null) {
            if (this.pattern != null) {
                try {
                    directoryCheck(this.file, this.pattern, "");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "File Not Found");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No Patterns Selected");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No File Selected");

        }
    }

    /**
     *
     */

    private void save() {
        String data = gui.text.getText();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a File");
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("txt", "txt"));

        int userSelection = fileChooser.showSaveDialog(gui);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                FileOutputStream os = new FileOutputStream(new File(fileToSave.getAbsolutePath()));
                os.write(data.getBytes(), 0, data.length());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "File Not Found");
            }
        }
    }

    /**
     *
     */

    private void clear() {
        this.file = null;
        this.patternFile = null;
        this.pattern = null;

        gui.text.setText("");
        gui.patternDisplay.setText("");
    }

    /**
     *
     */

    private void exit() {
        if (JOptionPane.showConfirmDialog(gui,
                "Are you sure you want to close this window?", "Close Window?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

}
