public class OsUtils {

        private static String OS = null;
        public static String fetchedOS = getOsName();

        public OsUtils(){
            System.out.println("SYSTEM IS RUNNING OS VERSION: " + fetchedOS + ". VARIABLES UPDATED ACCORDINGLY.");
        }

        public static String getOS(){
            return fetchedOS;
        }

        public static String getOsName() {
            if(OS == null) {
                OS = System.getProperty("os.name");
            }
            return OS;
        }

        public static boolean isWindows() {
            return getOsName().startsWith("Windows");
        }

        public static boolean isUnix() {
            return getOsName().startsWith("Windows");
        }

}
