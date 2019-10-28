import java.util.ArrayList;

class PatternSearcher {

    /**
     *
     */

    private ArrayList<String> resultsList;

    /**
     * @param byteCodes
     * @param patternSet
     * @return
     */

    String[] patternSearch(String[] byteCodes, String[][] patternSet) {

        resultsList = new ArrayList<String>();
        for (int i = 0; i < byteCodes.length; i++) {
            for (String[] strings : patternSet) {
                if (byteCodes[i].equals(strings[0])) {
                    for (int k = 1; k < strings.length; k++) {
                        if (!byteCodes[i + k].equalsIgnoreCase(strings[k])) break;
                        if (k == strings.length - 1) {
                            addResult(i, strings);
                        }
                    }
                }
            }
        }

        //System.out.println(Arrays.toString(byteCodes));

        if (resultsList.isEmpty()) resultsList.add("No patterns found");
        return resultsList.toArray(new String[resultsList.size()]);
    }

    /**
     * @param index
     * @param pattern
     */

    private void addResult(int index, String[] pattern) {
        StringBuilder patternString = new StringBuilder();
        for (String i : pattern) patternString.append(i);

        String result = String.format("Pattern found: %s, at offset: %s within the file.", patternString.toString(), index);

        resultsList.add(result);
    }
}
