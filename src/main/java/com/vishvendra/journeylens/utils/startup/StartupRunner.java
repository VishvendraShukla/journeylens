package com.vishvendra.journeylens.utils.startup;

import com.vishvendra.journeylens.service.user.AppUserService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupRunner implements CommandLineRunner {

  private final AppUserService appUserService;
  @Value("${spring.datasource.url}")
  private String dbUrl;
  @Value("${spring.datasource.username}")
  private String username;
  @Value("${spring.datasource.password}")
  private String password;
  @Value("${spring.datasource.driver-class-name}")
  private String className;
  @Value("${SESSION_DURATION_IN_SECONDS:10}")
  private Long sessionDurationInSeconds;

  public StartupRunner(AppUserService appUserService) {
    this.appUserService = appUserService;
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("SESSION TIME DURATION IS SET TO {}", sessionDurationInSeconds);
    boolean isUserTableEmpty = false;
    boolean tablesExists = false;
    boolean dbExists = false;
    String dbName = "journeylens";
    Connection connection = null;
    ResultSet resultSet = null;
    ResultSet tableResultSet = null;
    try {
      Class.forName(className);
      connection = DriverManager.getConnection(dbUrl, username, password);
      resultSet = connection.getMetaData().getCatalogs();

      while (resultSet.next()) {
        String catalogs = resultSet.getString(1);
        if (dbName.equals(catalogs)) {
          dbExists = true;
          break;
        }
      }
//      tablesExists = tablesExistsSQL(connection, dbName);
      isUserTableEmpty = isUserTableEmpty(connection);
      if (dbExists && isUserTableEmpty) {
        log.info("User table is empty, creating an admin");
        this.appUserService.createAdmin();
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      connection.close();
    }

  }

  private boolean tablesExistsSQL(Connection connection, String dbName) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
        + "FROM information_schema.tables "
        + "WHERE TABLE_SCHEMA = ?");
    preparedStatement.setString(1, dbName);

    ResultSet resultSet = preparedStatement.executeQuery();
    resultSet.next();
    return resultSet.getInt(1) != 0;
  }

  private boolean isUserTableEmpty(Connection connection) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
        + "FROM public.j_app_user ");
    ResultSet resultSet = preparedStatement.executeQuery();
    resultSet.next();
    return resultSet.getInt(1) == 0;
  }
}
