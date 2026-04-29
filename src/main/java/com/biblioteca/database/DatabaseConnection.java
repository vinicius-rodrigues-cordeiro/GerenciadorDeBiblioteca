package com.biblioteca.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL     = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USUARIO = "root";
    private static final String SENHA   = "sua_senha";

    private static HikariDataSource dataSource;

    // Construtor privado — configura e valida o pool na inicialização
    private DatabaseConnection() {}

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USUARIO);
            config.setPassword(SENHA);

            // Tamanho do pool
            config.setMaximumPoolSize(10);  // máximo de conexões simultâneas
            config.setMinimumIdle(2);        // mínimo de conexões em espera

            // Tempo máximo aguardando uma conexão disponível (30s)
            config.setConnectionTimeout(30000);

            // Tempo máximo que uma conexão fica ociosa (10min)
            config.setIdleTimeout(600000);

            // Valida a conexão antes de entregar ao DAO
            config.setConnectionTestQuery("SELECT 1");

            dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar pool de conexões: "
                    + e.getMessage(), e);
        }
    }

    // Singleton
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Fecha o pool ao encerrar a aplicação
    public static void fechar() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}