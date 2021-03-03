/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tavuk;

/**
 *
 * @author berat
 */
public class OsUtils {

    private static String OS;

    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public static String fixFilePath(String str) {
        str = str.replace("\\\\", OsUtils.getSeparator());
        str = str.replace("/", OsUtils.getSeparator());
        return str;
    }

    public static String getSeparator() {
        return getOsName().startsWith("Windows") ? "\\" : "/";
    }
}
