import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */

class FileReader {

    /**
     * @param file
     * @return
     * @throws IOException
     */

    byte[] genByteArray(File file) throws IOException {
        byte[] byteArray = new byte[(int) file.length()];
        new FileInputStream(file).read(byteArray);

        return byteArray;
    }


    /**
     * @param byteArray
     * @return
     */

    String[] genStringArray(byte[] byteArray) {
        String[] stringArray = new String[byteArray.length];

        for (int i = 0; i < byteArray.length; i++) {
            stringArray[i] = String.format("%02x", byteArray[i]);
        }

        return stringArray;
    }

    /**
     * @param file
     * @return
     * @throws Exception
     */

    String[][] patternFileReader(File file) throws Exception {
        ArrayList<String[]> patternList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
        String line = reader.readLine();
        int lineIndex = 0;
        while (line != null) {
            patternList.add(parse(line, lineIndex));
            line = reader.readLine();
            lineIndex++;
        }

        return patternList.toArray(new String[patternList.size()][]);
    }

    /**
     * @param line
     * @param index
     * @return
     * @throws Exception
     */

    private String[] parse(String line, int index) throws Exception {
        String[] lineArray = line.split(" ");
        for (String hex : lineArray) {
            if (hex.length() != 2) throw new Exception(String.format("Byte Error in Patern at line %s", index));
        }
        return lineArray;
    }


}
