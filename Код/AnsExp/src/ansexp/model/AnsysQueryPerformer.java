/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp.model;

import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Pete
 */
//Добавить обработку ошибок когда например не создана директория или не считана настройка из xml
// ошибки работы с файлами
public class AnsysQueryPerformer {

    private final File ansysDir;
    private final File workingDir;
    private final File queryFile;

    public AnsysQueryPerformer(File ansysDir, File workingDir, File queryFile) {
        this.ansysDir = ansysDir;
        this.workingDir = workingDir;
        this.queryFile = queryFile;
    }

    public void runQuery(String query, String userDirName, String projectName) throws IOException, FileNotFoundException, SAXException, ParserConfigurationException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("cmd");
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        Process p = pb.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        BufferedOutputStream out = new BufferedOutputStream(p.getOutputStream());

        String command = "\"" + ansysDir + "\"";
        String setDir = "-dir " + workingDir + "\\" + userDirName + "\\" + projectName;
        String setJobName = "-j " + projectName;
        String setInputFile = "-i " + "\"" + queryFile + "\"";
        String setOutputFile = "-o " + "\"" + workingDir + "\\" + userDirName + "\\" + projectName + "\\output.txt" + "\"";
        command = command + " " + setDir + " " + setInputFile + " " + setOutputFile + " " + setJobName + " -b list " + "\n";
        out.write(command.getBytes());
        out.flush();
    }

    public String queryToFile(String query, String userName, String projectName) throws FileNotFoundException, IOException, SAXException, ParserConfigurationException {
        String dirName = workingDir + "\\" + userName + "\\" + projectName;
        File dir = new File(dirName);
        dir.mkdirs();
        try (FileOutputStream fout = new FileOutputStream(queryFile)) {
            fout.write(query.getBytes());
        }
        return queryFile.getAbsolutePath();
    }
}
