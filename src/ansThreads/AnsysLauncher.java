/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansThreads;

import ansexp.forms.RuningForm;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pete
 */
public class AnsysLauncher extends Thread {

    private final File ansysDir;
    private final File workingDir;
    private File queryFile;
    RuningForm rForm = new RuningForm();

    public AnsysLauncher(File ansysDir, File workingDir) {
        setDaemon(true);
        this.ansysDir = ansysDir;
        this.workingDir = workingDir;
        rForm.setVisible(false);
    }

    public void setOutput(File outputFile) {
        queryFile = outputFile;
    }

    @Override
    public void run() {
        rForm.setVisible(true);
        BufferedReader in = null;
        File outPutFile = new File(getWorkingDir() + "\\output.txt");
        outPutFile.delete();
        outPutFile.getParentFile().mkdirs();
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd");
            Process p = pb.start();
            //in = new BufferedReader(new InputStreamReader(p.getInputStream(), "windows-1251"));
            //BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream(), "windows-1251"));
            BufferedOutputStream out = new BufferedOutputStream(p.getOutputStream());
            String command = "chcp 1251\n  \"" + getAnsysDir() + "\"  -g";
            String setDir = "-dir " + "\"" + getWorkingDir() + "\"";
            String setJobName = " -j " + "\"Test\"";
            String setInputFile = " -i " + "\"" + queryFile + "\"";
            String setOutputFile = " -o " + "\"" + outPutFile.toString() + "\"";
            command = command + " " + setDir + " " + setInputFile + " " + setOutputFile + " " + setJobName + " -b list \n";// + "-s read -l en-us -t -d win32  \n ";
            out.write(command.getBytes("windows-1251"));
            out.flush();
            out.close();
            //p.waitFor();
            Thread outFiller = new Thread(new Runnable() {

                @Override
                public void run() {
                    System.out.println(outPutFile.delete());
                    BufferedReader outReader = null;

                    while (p.isAlive()) {
                        try {
                            if (outReader == null) {
                                outReader = new BufferedReader(new FileReader(outPutFile));
                            }
                        } catch (FileNotFoundException ex) {
                        }
                        try {
                            while (outReader.ready()) {
                                try {
                                    String line = outReader.readLine();
                                    rForm.addOutput(line);
                                    if (line.contains("ERROR")) {
                                        rForm.addError(line);
                                    }
                                } catch (IOException ex) {
                                }
                            }
                        } catch (FileNotFoundException ex) {
                        } catch (Exception ex) {
                        }
                    }

                    if (outReader != null) {
                        try {
                            outReader.close();
                        } catch (IOException ex) {
                            Logger.getLogger(AnsysLauncher.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    rForm.hideProgressBar();
                }
            });
            outFiller.start();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AnsysLauncher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AnsysLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the ansysDir
     */
    public File getAnsysDir() {
        return ansysDir;
    }

    /**
     * @return the workingDir
     */
    public File getWorkingDir() {
        return workingDir;
    }

}
