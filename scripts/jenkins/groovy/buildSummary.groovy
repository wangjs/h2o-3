class BuildSummary {

    public static final String RESULT_SUCCESS = 'success'
    public static final String RESULT_FAILURE = 'failure'

    private final List<StageSummary> stageSummaries = []

    StageSummary addStageSummary(context, final String stageName) {
        context.echo "${this.toString()}"
        context.echo "${stageSummaries.toString()}"
        final StageSummary alreadyCreated = findForName(stageName)
        if (alreadyCreated != null) {
            throw new IllegalArgumentException("Stage Summary for %s already added".format(stageName))
        }
        def summary = new StageSummary(stageName)
        stageSummaries.add(summary)
        return summary
    }

    StageSummary setStageNode(final String stageName, final String nodeName) {
        final StageSummary summary = findOrThrowForName(stageName)
        summary.setNodeName(nodeName)
        return summary
    }

    StageSummary setStageResult(final String stageName, final String result) {
        final StageSummary summary = findOrThrowForName(stageName)
        summary.setResult(result)
        return summary
    }

    private StageSummary findForName(final String stageName) {
        return stageSummaries.find({it.stageName == stageName})
    }

    private StageSummary findOrThrowForName(final String stageName) {
        final StageSummary summary = findForName(stageName)
        if (summary == null) {
            throw new IllegalStateException("Cannot find StageSummary for %s".format(stageName))
        }
        return summary
    }

    @Override
    String toString() {
        return "BuildSummary{" +
                "stageSummaries=" + stageSummaries +
                '}'
    }

    class StageSummary {
        private final String stageName

        private String nodeName
        private String result

        private StageSummary(final String stageName) {
            this.nodeName = nodeName
            this.stageName = stageName
            this.result = result
        }

        String getNodeName() {
            return nodeName
        }

        String getStageName() {
            return stageName
        }

        String getResult() {
            return result
        }

        void setNodeName(String nodeName) {
            this.nodeName = nodeName
        }

        void setResult(String result) {
            this.result = result
        }

        @Override
        String toString() {
            return "StageSummary{" +
                    "stageName='" + stageName + '\'' +
                    ", nodeName='" + nodeName + '\'' +
                    ", result='" + result + '\'' +
                    '}'
        }
    }
}

class BuildSummaryFactory {
    BuildSummary create() {
        return new BuildSummary()
    }
}

return new BuildSummaryFactory()