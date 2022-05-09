package pt.ua.deti.codespell;

import lombok.extern.log4j.Log4j2;
import pt.ua.deti.codespell.utils.ExecutionStatus;

@Log4j2
public class Main {

    private static ExecutionStatus executionStatus;

    public static void main( String[] args ) {

        try {
            remoteCodeExecutor();
        } catch (Exception e) {
            log.warn("Exception thrown while running remote code: " + e.getMessage());
            executionStatus = ExecutionStatus.RUNTIME_ERROR;
        }

    }

    /**
     * Remote Code Executor method
     *
     * This method will have the responsibility of running the remote code.
     */
    private static void remoteCodeExecutor() {
        // INJECT CODE HERE
    }

}
