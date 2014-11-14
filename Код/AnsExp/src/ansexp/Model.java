/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.*;
import org.netbeans.swing.outline.Outline;

/**
 *
 * @author Pete
 */
public class Model {

    private File dbFile;
    private File xmlFile;
    private File classFile;
    private final ZipFile zipFile;
    private Outline outline;

    public Model(ZipFile zipFile) throws IOException {
        this.zipFile = zipFile;
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            makeFile(entry);
            if (isComplete()) {
                break;
            }
        }
    }

    private boolean isComplete() {
        return dbFile != null && xmlFile != null && classFile != null;
    }

    private void makeFile(ZipEntry entry) throws IOException {
        String extension = getExt(entry.getName());

        File tempFile = File.createTempFile(Integer.toString(entry.hashCode()), "." + extension);
        tempFile.deleteOnExit();

        switch (extension) {
            case "xml":
                xmlFile = tempFile;
                break;
            case "sqlite":
                dbFile = tempFile;
                break;
            case "class":
                classFile = tempFile;
                break;
            default:
                return;
        }

        InputStream stream = this.zipFile.getInputStream(entry);
        FileOutputStream tmpOut = new FileOutputStream(tempFile);
        byte b[] = new byte[stream.available() + 50];
        stream.read(b);
        tmpOut.write(b);

    }

    /**
     * @return the dbFile
     */
    public File getDbFile() {
        return dbFile;
    }

    /**
     * @return the xmlFile
     */
    public File getXmlFile() {
        return xmlFile;
    }

    /**
     * @return the calcFile
     */
    public File getCalcFile() {
        return classFile;
    }

    private String getExt(String path) {
        String[] s = path.split("\\.");
        return s[s.length - 1];
    }
   
}
