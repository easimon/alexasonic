package click.dobel.alexasonic.mapdb;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public interface DbFactory {
    DB createDb();

    static DB applyCommonOptions(final DBMaker.Maker maker) {
        return maker
            .transactionEnable()
            .closeOnJvmShutdown()
            .make();
    }
}