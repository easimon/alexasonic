package click.dobel.alexasonic.mapdb;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public final class DbFactories {

    private DbFactories() {
    }

    public static DbFactory file(final String fileName) {
        return new FileDbFactory(fileName);
    }

    public static DbFactory mem() {
        return new MemDbFactory();
    }

    private static class FileDbFactory implements DbFactory {
        private final String fileName;

        private FileDbFactory(final String fileName) {
            this.fileName = fileName;
        }

        @Override
        @SuppressWarnings("PMD.AccessorMethodGeneration")
        public DB createDb() {
            return DbFactory.applyCommonOptions(DBMaker.fileDB(fileName));
        }
    }

    private static class MemDbFactory implements DbFactory {

        @Override
        public DB createDb() {
            return DbFactory.applyCommonOptions(DBMaker.memoryDB());
        }
    }
}
