/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp.model;

import java.io.*;

/**
 *
 * @author Pete
 */
public class AnsysQueryPerformer {

    private final File ansysDir;
    private final File workingDir;
    private final File queryFile;

    public AnsysQueryPerformer(File ansysDir, File workingDir, File queryFile) {
        this.ansysDir = ansysDir;
        this.workingDir = workingDir;
        this.queryFile = queryFile;
    }

    public void run(String jobName) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd");
        //pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        Process p = pb.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream(), "windows-1251"));
        BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream(), "windows-1251"));
        BufferedOutputStream out = new BufferedOutputStream(p.getOutputStream());

        String command = "chcp 1251\n  \"" + ansysDir + "\"  -g";
        String setDir = "-dir " + "\"" + workingDir + "\"";
        String setJobName = " -j " + "\"Test\"";
        String setInputFile = " -i " + "\"" + queryFile + "\"";
        String setOutputFile = " -o " + "\"" + workingDir + "\\output.txt" + "\"";
        command = command + " " + setDir + " " + setInputFile + " " + setOutputFile + " " + setJobName + " -b list " + "-s read -l en-us -t -d win32  \n ";
        out.write(command.getBytes("windows-1251"));
        out.flush();
        out.close();
    }
}
