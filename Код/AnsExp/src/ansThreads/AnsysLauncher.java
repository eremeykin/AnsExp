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
import java.io.InputStreamReader;
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
    private final File queryFile;
    RuningForm rForm = new RuningForm();

    public AnsysLauncher(File ansysDir, File workingDir, File queryFile) {
        setDaemon(true);
        this.ansysDir = ansysDir;
        this.workingDir = workingDir;
        this.queryFile = queryFile;
        rForm.setVisible(true);
    }

    @Override
    public void run() {
        BufferedReader in = null;
        File outPutFile = new File(workingDir + "\\output.txt");
        outPutFile.delete();
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd");
            //pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            Process p = pb.start();
            in = new BufferedReader(new InputStreamReader(p.getInputStream(), "windows-1251"));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream(), "windows-1251"));
            BufferedOutputStream out = new BufferedOutputStream(p.getOutputStream());
            String jobName = "AnsExp";
            String command = "chcp 1251\n  \"" + ansysDir + "\"  -g";
            String setDir = "-dir " + "\"" + workingDir + "\"";
            String setJobName = " -j " + "\"Test\"";
            String setInputFile = " -i " + "\"" + queryFile + "\"";
            String setOutputFile = " -o " + "\"" + outPutFile.toString() + "\"";
            command = command + " " + setDir + " " + setInputFile + " " + setOutputFile + " " + setJobName + " -b list \n";// + "-s read -l en-us -t -d win32  \n ";
            out.write(command.getBytes("windows-1251"));
            out.flush();
            out.close();
            String line = "";
            //p.waitFor();
            Thread outFiller = new Thread(new Runnable() {

                @Override
                public void run() {
                    BufferedReader outReader = null;

                    while (p.isAlive()) {
                        try {
                            outReader = new BufferedReader(new FileReader(outPutFile));
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(AnsysLauncher.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            while (outReader.ready()) {
                                try {
                                    rForm.addOutput(outReader.readLine());//System.out.print(c);
                                } catch (IOException ex) {
                                    Logger.getLogger(AnsysLauncher.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(AnsysLauncher.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }
            );
            outFiller.start();
            Thread errFiller = new Thread(new Runnable() {

                @Override
                public void run() {
                    BufferedReader outReader = null;
                    boolean errStarts = false;
                    try {
                        outReader = new BufferedReader(new FileReader(outPutFile));
                        while (p.isAlive()) {
                            try {
                                while (outReader.ready()) {
                                    String out = outReader.readLine();//System.out.print(c);
                                    if (out.startsWith(" *** ERROR ***") || errStarts) {
                                        rForm.addError(out);
                                        errStarts = !out.equals("");
                                    }
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(AnsysLauncher.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(AnsysLauncher.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            outReader.close();
                        } catch (IOException ex) {
                            Logger.getLogger(AnsysLauncher.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            //errFiller.start();

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AnsysLauncher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AnsysLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
