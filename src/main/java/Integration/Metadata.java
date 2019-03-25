package Integration;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Metadata {

    private static final String FILEPATH = "filePath";

    private static final String FILENAME = "fileName";

    private String fileName;

    private String filePath;

    public String getFileName() {
        return fileName;
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    private void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public static ArrayList<Metadata> setToList(ResultSet res) throws Exception {

        ArrayList<Metadata> list = new ArrayList<>();

        Metadata c = new Metadata();

        while (res.next()) {
            c.setFileName(res.getString(FILENAME));
            c.setFilePath(res.getString(FILEPATH));
            list.add(c);
        }

        return list;
    }

}
