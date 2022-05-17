package pt.ua.deti.codespell.utils;


import lombok.Getter;

import java.util.UUID;

@Getter
public class CodeExecutionResult {

    private final UUID codeUniqueId;
    private final ExecutionStatus executionStatus;
    private final long time;
    private final int score;

    public static class Builder {
        private UUID codeUniqueId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        private ExecutionStatus executionStatus = ExecutionStatus.NONE;
        private long time = 0;
        private int score = 0;

        public Builder withCodeUniqueId(UUID codeUniqueId) {
            this.codeUniqueId = codeUniqueId;
            return this;
        }

        public Builder withExecutionStatus(ExecutionStatus executionStatus) {
            this.executionStatus = executionStatus;
            return this;
        }

        public Builder withTime(long time) {
            this.time = time;
            return this;
        }

        public Builder withScore(int score) {
            this.score = score;
            return this;
        }

        public CodeExecutionResult build() {
            return new CodeExecutionResult(this);
        }

    }

    protected CodeExecutionResult(Builder builder) {
        this.codeUniqueId = builder.codeUniqueId;
        this.executionStatus = builder.executionStatus;
        this.time = builder.time;
        this.score = builder.score;
    }

}
