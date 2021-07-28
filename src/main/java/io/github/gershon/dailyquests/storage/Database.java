package io.github.gershon.dailyquests.storage;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.player.QuestProgress;
import org.spongepowered.api.scheduler.Task;

import java.sql.*;
import java.util.UUID;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:database/players.db";

    public static boolean connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            return true;
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return false;
    }

    public void createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS players (\n"
                + " id varchar(64) not null,\n"
                + " questId varchar(64) not null,\n"
                + " questsCompleted integer DEFAULT 0,\n"
                + " taskAmount integer DEFAULT 0,\n"
                + " unique (id, questId),\n"
                + ");";

        executeSQLStatement(sql);
    }

    public void savePlayer(QuestPlayer questPlayer) {
        String playerSql = "INSERT INTO players (id, questsCompleted, questId, taskAmount) VALUES(?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "questId = ?, " +
                "questsCompleted = ?, " +
                "taskAmount = ?";

        try (Connection conn = DailyQuests.getInstance().getConnection()) {
            questPlayer.getQuestProgressMap().keySet().forEach(key -> {
                try {
                    PreparedStatement pstmt = null;
                    pstmt = conn.prepareStatement(playerSql);
                    pstmt.setString(1, questPlayer.getPlayerId().toString());
                    pstmt.setInt(2, questPlayer.getQuestsCompleted());
                    pstmt.setString(3, key);
                    pstmt.setInt(4, questPlayer.getQuestProgressMap().get(key).getTaskAmount());

                    pstmt.setInt(5, questPlayer.getQuestsCompleted());
                    pstmt.setString(6, key);
                    pstmt.setInt(7, questPlayer.getQuestProgressMap().get(key).getTaskAmount());


                    pstmt.executeUpdate();
                } catch (SQLException throwables) {
                    DailyQuests.getInstance().getLogger().warn(throwables.getMessage());
                }
            });
        } catch (SQLException e) {
            DailyQuests.getInstance().getLogger().warn(e.getMessage());
        }
    }

    public void loadPlayer(UUID uuid) {
        QuestPlayer questPlayer = null;

        try (Connection conn = DailyQuests.getInstance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM players where id='" + uuid.toString() + "'");
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                questPlayer = new QuestPlayer(uuid);
                questPlayer.setQuestsCompleted(results.getInt("questsCompleted"));
                String questId = results.getString("questId");
                int amount = results.getInt("taskAmount");
                questPlayer.getQuestProgressMap().put(questId, new QuestProgress(questId, amount));

            }
            if (questPlayer != null) {
                DailyQuests.getInstance().playerMap.put(uuid, questPlayer);
            } else {
                questPlayer = new QuestPlayer(uuid);
                DailyQuests.getInstance().playerMap.put(uuid, questPlayer);
                QuestPlayer finalQuestPlayer = questPlayer;
                //Task.builder().execute(() -> DailyQuests.getDatabase().savePlayer(finalQuestPlayer))
                //        .async()
                //        .submit(DailyQuests.getInstance());
            }
        } catch (SQLException e) {
            DailyQuests.getInstance().getLogger().warn(e.getMessage());
        }
    }

    private static void executeSQLStatement(String sql) {
        try (Connection conn = DailyQuests.getInstance().getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            DailyQuests.getInstance().getLogger().warn(e.getMessage());
        }
    }
}
