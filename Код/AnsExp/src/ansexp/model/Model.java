/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp.model;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import org.netbeans.swing.outline.Outline;

/**
 *
 * @author Pete
 */
public class Model {

    private File sqliteFile;
    private File xmlFile;
    private File classesDir;
    private File mainDir;
    private final XMLParser parser;
    private final Node root;
    private final Outline outline;

    private static ArrayDeque<Model> models = new ArrayDeque<>();

    public Model(File mainDir) throws IOException, SQLException, XMLParser.XMLParsingException, ClassNotFoundException {
        if (!mainDir.isDirectory()) {
            throw new IllegalArgumentException("It is not a directory: " + mainDir);
        }
        this.mainDir = mainDir;
        // Find all matching files
        File[] xmlFiles = mainDir.listFiles(getFilter("xml"));
        File[] sqliteFiles = mainDir.listFiles(getFilter("sqlite"));
        File[] classesDirs = mainDir.listFiles(getFilter("classes"));
        // Select on instance of files
        xmlFile = xmlFiles.length > 0 ? xmlFiles[0] : null;
        sqliteFile = sqliteFiles.length > 0 ? sqliteFiles[0] : null;
        classesDir = classesDirs.length > 0 ? classesDirs[0] : null;
        // Create root
        parser = new XMLParser(xmlFile, sqliteFile);
        root = parser.getResultNode();
        outline = new OutlineCreator(getRoot()).getOutline();
        models.add(this);
    }

    private boolean isComplete() {
        return sqliteFile != null && xmlFile != null && classesDir != null;
    }

    /**
     * @return the dbFile
     */
    public File getSqliteFile() {
        return sqliteFile;
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
    public File getClassesDir() {
        return classesDir;
    }

    /**
     * @return the outline
     */
    public Outline getOutline() {
        return outline;
    }



    private FileFilter getFilter(String ext) {
        if (ext.equals("classes")) {
            return new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory() && pathname.getName().equals(ext);
                }
            };
        }
        return new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                String[] tokens = pathname.getName().split("\\.");
                String extension = tokens[tokens.length - 1];
                return extension.equals(ext);
            }
        };
    }

    /**
     * @return the root
     */
    public Node getRoot() {
        return root;
    }
    
    public  Connection getConnection(){
        return parser.getConnection();
    } 

}
