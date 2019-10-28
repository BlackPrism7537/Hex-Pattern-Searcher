import javax.swing.*;
import java.awt.*;

/**
 *
 */

class Gui extends JFrame {

    TextArea text;
    TextArea patternDisplay;
    JMenuItem selectFile;
    JMenuItem selectPattern;
    JMenuItem runSearch;
    JMenuItem clearText;
    JMenuItem saveText;
    JMenuItem exit;

    /**
     *
     */

    Gui() {
        super("Pattern Search");

        text = new TextArea();
        patternDisplay = new TextArea();
        JMenuBar toolBar = new JMenuBar();

        // --setting up File Menu-- //
        JMenu file = new JMenu("File");

        selectFile = new JMenuItem("Select File|Directory.");
        file.add(selectFile);

        selectPattern = new JMenuItem("Select Pattern.");
        file.add(selectPattern);

        runSearch = new JMenuItem("Run Search.");
        file.add(runSearch);

        saveText = new JMenuItem("Save Results.");
        file.add(saveText);

        clearText = new JMenuItem("Clear.");
        file.add(clearText);

        exit = new JMenuItem("Exit.");
        file.add(exit);

        toolBar.add(file);

        // --setting up Help Menu-- //
        JMenu help = new JMenu("Help");

        toolBar.add(help);

        text.setEditable(false);
        patternDisplay.setEditable(false);

        getContentPane().setPreferredSize(new Dimension(810, 220));

        JSplitPane panelSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, text, patternDisplay);


        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(toolBar, BorderLayout.NORTH);
        getContentPane().add(panelSplit, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }


}
