package com.dju.demo.helpers;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import org.sqlite.SQLiteException;

import java.sql.*;
import java.util.concurrent.TimeUnit;

public class SQLiteJDBC {
    private static boolean _MAIN_RUN = false;
    private static int subCount = 0;
    static private BehaviorSubject<Boolean> closed = BehaviorSubject.create();
    static private Observable<Boolean> _dbClosed;
    private Connection _connection;

    public SQLiteJDBC() {
        closed.onNext(true);

        _dbClosed = closed
                .filter(aBoolean -> aBoolean)
                .map(aBoolean -> {
            SQLiteJDBC.subCount++;
            // System.out.println("Sub close: " + SQLiteJDBC.subCount);

            if (this._connection != null) {
                if (!this._connection.isClosed()) {
                    this._connection.close();
                    System.out.println("\t[" + SQLiteJDBC.openDBCount + "] closedDB: " + SQLiteJDBC.subCount);
                }
                this._connection = null;
            }

            return true;
        });

        _dbClosed.subscribe((p) -> {
            System.out.println("handled close.");
        });
    }

    private static int openDBCount = 0;
    public void openDB() {
        SQLiteJDBC helper = this;
        openDBCount++;

        SQLiteJDBC._dbClosed
            .firstOrError()
            .subscribe(new SingleObserver() {
                final int _id = openDBCount;

                @Override
                public void onSubscribe(Disposable disposable) {

                }

                @Override
                public void onSuccess(Object o) {
                    System.out.println("\t[" + this._id + "] openDb called.");
                    helper.doOpenDB();
                }

                @Override
                public void onError(Throwable throwable) {

                }
            });
    }

    private void doOpenDB() {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        } finally {
            this._connection = c;
            if(c != null) {
                closed.onNext(false);
            }
        }
    }

    public boolean createTable(String sql) {
        //Create the observable
        System.out.println("isNoSuchTable 11: creating table: ");
        Single<Boolean> testSingle = SQLiteJDBC._dbClosed
                .firstOrError()
                .map(aBool -> {
                    System.out.println("isNoSuchTable 11: creating table");
                    return doCreateTable(sql);
                });

        return testSingle.blockingGet();
    }

    private Boolean doCreateTable(String sql) {
        this.openDB();
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            // System.out.println("Opened database successfully");

            stmt = this._connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
//            this._connection.close();
            this.closeDB();

            // System.out.println("Table created successfully");
            return true;
        } catch ( Exception e ) {
            // System.out.println("Table not created");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            this.closeDB();
//            System.exit(0);

            return false;
        }
    }

    public String selectData(String sql) throws SQLiteException {
        //Create the observable
        Single<String> testSingle = SQLiteJDBC._dbClosed
                .firstOrError()
                .map(aBoolean -> {
                    String res = doSelectData(sql);
                    return res == null ? "[]" : res;
                });

//        String resF = testSingle.blockingGet();
//        resF = resF.equals("") ? null : resF;

        return testSingle.blockingGet();
    }

    private String doSelectData(String sql) throws org.sqlite.SQLiteException {
        //        Connection c = this.openDB();
        this.openDB();
        Statement stmt = null;

        boolean isClosed = false;

        try {
            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            this._connection.setAutoCommit(false);
            // System.out.println("Opened database successfully");

            stmt = this._connection.createStatement();
            ResultSet rs = stmt.executeQuery( sql );

            String name = null;
            while ( rs.next() ) {
                name = rs.getString("value");
            }
            rs.close();
            stmt.close();
//            this._connection.close();
            this.closeDB();
            isClosed = true;
            // System.out.println("Records created successfully");
            return name;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

            this.closeDB();

            if(e.getMessage().contains("no such table")) {
                throw (org.sqlite.SQLiteException)e;
            }
//            System.exit(0);
            return null;
        }
    }

    private boolean doInsertData(String sql) {
        this.openDB();
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            this._connection.setAutoCommit(false);
            // System.out.println("Opened database successfully");

            stmt = this._connection.createStatement();
//            String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
//                    "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
            stmt.executeUpdate(sql);

//            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
//                    "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
//            stmt.executeUpdate(sql);
//
//            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
//                    "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
//            stmt.executeUpdate(sql);
//
//            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
//                    "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
//            stmt.executeUpdate(sql);

            stmt.close();
            this._connection.commit();
            this.closeDB();
            // System.out.println("Records created successfully");
            return true;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

            this.closeDB();
//            System.exit(0);
            return false;
        }
    }

    public boolean insertData(String sql) throws InterruptedException {
        //Create the observable
        boolean val = SQLiteJDBC.closed.getValue();
        Single<Boolean> testSingle = SQLiteJDBC._dbClosed
                .delay(200, TimeUnit.MILLISECONDS)
                .firstOrError()
                .map(aBoolean -> {
                    return doInsertData(sql);
                });

        return testSingle.blockingGet();
    }

    private void closeDB() {
        SQLiteJDBC.closed.onNext(true);
        // System.out.println(String.format("after close db %s ?", SQLiteJDBC.subCount));
    }

    public static void main( String args[] ) throws InterruptedException {
        if(_MAIN_RUN) {
            return;
        }

        _MAIN_RUN = true;

        SQLiteJDBC helper = new SQLiteJDBC();
//        helper.openDB();
        System.out.println("[main] Opened database successfully");

//        String sql = "CREATE TABLE SPLITMAN " +
//                "(ID INT PRIMARY KEY     NOT NULL," +
//                " NAME           TEXT    NOT NULL, " +
//                " AGE            INT     NOT NULL, " +
//                " ADDRESS        CHAR(50), " +
//                " SALARY         REAL)";

        String sql = "CREATE TABLE SPLITMAN (VALUE    TEXT    NOT NULL)";
        boolean res = false;

        try {
            res = helper.createTable(sql);
        } catch (Exception e) {
            System.out.println("Not creating data...");
            return;
        }

        if(!res) {
            System.out.println("Not creating data...");
            return;
        }

        System.out.println("Table created.");

        final String json = "[{\"password\":\"s\",\"invites\":[],\"id\":1,\"type\":\"user\",\"email\":\"hatsune.miku.asb@wspt.co.uk\",\"username\":\"s\"},{\"password\":\"a\",\"invites\":[],\"id\":2,\"type\":\"user\",\"email\":\"le_zhumain@msn.com\",\"username\":\"a\"}]";
        res = helper.insertData(String.format("INSERT INTO SPLITMAN (VALUE) VALUES ('%s')", json));
        if(res) {
            System.out.println("Data added.");
        }

        String data = null;
        try {
            data = helper.selectData("SELECT rowid, value from \"SPLITMAN\" order by rowid desc LIMIT 1");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if(data != null) {
            System.out.println("Got data:");
            System.out.println(data);
        }
    }
}

