package pl.paweu.safes.database;

import pl.paweu.safes.config.Settings;
import pl.paweu.safes.manager.SafeManager;
import pl.paweu.safes.object.Safe;

import java.io.IOException;
import java.sql.*;

public class MySQL {

    private Connection conn;
    private final Settings settings;
    private final SafeManager safeManager;

    public MySQL(Settings settings, SafeManager safeManager){
        this.settings = settings;
        this.safeManager = safeManager;
    }

    public void load() {
        this.openConnection();
        if (this.conn == null) return;
        try {
            Statement stmt = this.conn.createStatement();
            String sb = "CREATE TABLE IF NOT EXISTS `paweU-Safes` (" +
                    "id int not null, " +
                    "owner varchar(30) not null, " +
                    "lore varchar(16) not null, " +
                    "items text not null, " +
                    "primary key(id));";
            stmt.executeUpdate(sb);
            int safes = 0;
            ResultSet rs = stmt.executeQuery("SELECT * FROM `paweU-Safes`");
            while (rs.next()) {
                Safe safe = new Safe(rs);
                this.safeManager.loadSafe(safe);
                ++safes;
            }
            System.out.println("[paweU-Safes] Zaladowano sejfy w liczbie " + safes);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        this.closeConnection();
    }

    public void save() {
        this.openConnection();
        if (this.conn == null) return;
        try {
            Statement stmt = this.conn.createStatement();
            for (Safe safe : this.safeManager.getSafes().values()) {
                if (!safe.isChanges()) continue;
                stmt.executeUpdate(safe.getSQL());
                safe.setChanges(false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.closeConnection();
    }

    private void openConnection() {
        try {
            if (conn != null && !conn.isClosed()) return;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://" + this.settings.mysqlHost + ":" +
                    this.settings.mysqlPort + "/" +
                    this.settings.mysqlDatabase + "?user=" +
                    this.settings.mysqlUsername + "&password=" +
                    this.settings.mysqlPassword);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println("[paweU-Safes] -> Nie mozna polaczyc sie z MySQL");
        }
    }

    private void closeConnection() {
        try {
            if(this.conn == null || this.conn.isClosed()) return;
            this.conn.close();
            this.conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

