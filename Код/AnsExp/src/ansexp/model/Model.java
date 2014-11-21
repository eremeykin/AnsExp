/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ansexp.model;

import ansexp.toolkit.Calculateable;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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
    private File output;
    private File macFile;
    private File ansysDir=new File(System.getenv("ANSYS150_DIR")+"\\bin\\winx64\\ANSYS150.exe");
    private File workingDir=new File(System.getProperty("user.home")+"\\Desktop\\AnsysTestWorkingDir");
    private final XMLParser parser;
    private final Node root;
    private final Outline outline;
    private Calculateable calc;
    private AnsysQueryPerformer aqPerformer;

    private static ArrayDeque<Model> models = new ArrayDeque<>();

    public Model(File mainDir) throws IOException, SQLException, XMLParser.XMLParsingException, ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!mainDir.isDirectory()) {
            throw new IllegalArgumentException("It is not a directory: " + mainDir);
        }
        this.mainDir = mainDir;
        // Find all matching files
        File[] xmlFiles = mainDir.listFiles(getFilter("xml"));
        File[] sqliteFiles = mainDir.listFiles(getFilter("sqlite"));
        File[] classesDirs = mainDir.listFiles(getFilter("classes"));
        File[] macFiles = mainDir.listFiles(getFilter("mac"));
        // Select on instance of files
        xmlFile = xmlFiles.length > 0 ? xmlFiles[0] : null;
        sqliteFile = sqliteFiles.length > 0 ? sqliteFiles[0] : null;
        classesDir = classesDirs.length > 0 ? classesDirs[0] : null;
        macFile = macFiles.length > 0 ? macFiles[0] : null;
        // Create root
        parser = new XMLParser(xmlFile, sqliteFile);
        root = parser.getResultNode();
        outline = new OutlineCreator(getRoot()).getOutline();
        models.add(this);
        loadCalculator();
    }

    private void loadCalculator() throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //m.getRoot().setValueById("JAW_HEIGHT", "test");
        URL[] urls = {getClassesDir().toURI().toURL()};
        URLClassLoader classLoader = new URLClassLoader(urls);
        Class cls = classLoader.loadClass("ansexp.calculator.DefaultCalculator");
        Constructor<?>[] constructors = cls.getConstructors();
        //Class[] requiredParametersTypes = {DataSource.class};
        for (Constructor constr : constructors) {
            if (constr.getParameterTypes().length == 0) {
                calc = (Calculateable) constr.newInstance();
                calc.setConnection(getConnection());
                break;
            }
        }
    }

    public void calculate() {
        calc.calculate(root);
        outline.repaint();
    }

    public void print() throws IOException {
        output = calc.printToFile(getMacFile());
    }

    public void run() throws IOException {
        calculate();
        aqPerformer = new AnsysQueryPerformer(getAnsysDir(), getWorkingDir(), output);
        aqPerformer.run("Test");
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

    public Connection getConnection() {
        return parser.getConnection();
    }

    /**
     * @return the mainDir
     */
    public File getMainDir() {
        return mainDir;
    }

    /**
     * @param mainDir the mainDir to set
     */
    public void setMainDir(File mainDir) {
        this.mainDir = mainDir;
    }

    /**
     * @return the macFile
     */
    public File getMacFile() {
        return macFile;
    }

    /**
     * @return the ansysDir
     */
    public File getAnsysDir() {
        return ansysDir;
    }

    /**
     * @param ansysDir the ansysDir to set
     */
    public void setAnsysDir(File ansysDir) {
        this.ansysDir = ansysDir;
    }

    /**
     * @return the workingDir
     */
    public File getWorkingDir() {
        return workingDir;
    }

}
