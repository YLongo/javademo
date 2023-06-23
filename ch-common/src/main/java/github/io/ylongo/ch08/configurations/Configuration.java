package github.io.ylongo.ch08.configurations;

/**
 * We add the configuration interface as part of the refactoring process.
 */
public interface Configuration {
    /**
     * Getter method to get the SQL query to execute.
     *
     * @return
     */
    String getSQL(String sqlString);


}