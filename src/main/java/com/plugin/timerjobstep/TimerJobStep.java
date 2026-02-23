package com.plugin.timerjobstep;

import com.dtolabs.rundeck.core.execution.ExecutionListener;
import com.dtolabs.rundeck.core.execution.workflow.steps.StepException;
import com.dtolabs.rundeck.core.execution.workflow.steps.StepFailureReason;
import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.core.plugins.configuration.Description;
import com.dtolabs.rundeck.core.plugins.configuration.Describable;
import com.dtolabs.rundeck.core.plugins.configuration.Property;
import com.dtolabs.rundeck.core.plugins.configuration.StringRenderingConstants;
import com.dtolabs.rundeck.plugins.descriptions.PluginDescription;
import com.dtolabs.rundeck.plugins.step.PluginStepContext;
import com.dtolabs.rundeck.plugins.step.StepPlugin;
import com.dtolabs.rundeck.plugins.util.DescriptionBuilder;
import com.dtolabs.rundeck.plugins.util.PropertyBuilder;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Plugin(service = "WorkflowStep", name = "timer-job-step")
@PluginDescription(
    title = "Timer Job Step",
    description = "Pauses a workflow for a selected duration (10 seconds to 10 minutes)."
)
public class TimerJobStep implements StepPlugin, Describable {
    public static final String PROP_PAUSE_DURATION = "pauseDuration";

    private static final List<String> DURATION_VALUES = Arrays.asList(
        "10", "20", "30", "45",
        "60", "90", "120", "180", "240", "300", "360", "420", "480", "540", "600"
    );

    private static final Map<String, String> DURATION_LABELS = new LinkedHashMap<>();

    static {
        DURATION_LABELS.put("10", "10 seconds");
        DURATION_LABELS.put("20", "20 seconds");
        DURATION_LABELS.put("30", "30 seconds");
        DURATION_LABELS.put("45", "45 seconds");
        DURATION_LABELS.put("60", "1 minute");
        DURATION_LABELS.put("90", "1 minute 30 seconds");
        DURATION_LABELS.put("120", "2 minutes");
        DURATION_LABELS.put("180", "3 minutes");
        DURATION_LABELS.put("240", "4 minutes");
        DURATION_LABELS.put("300", "5 minutes");
        DURATION_LABELS.put("360", "6 minutes");
        DURATION_LABELS.put("420", "7 minutes");
        DURATION_LABELS.put("480", "8 minutes");
        DURATION_LABELS.put("540", "9 minutes");
        DURATION_LABELS.put("600", "10 minutes");
    }

    @Override
    public Description getDescription() {
        DescriptionBuilder builder = DescriptionBuilder.builder();
        builder.name("timer-job-step")
            .title("Timer Job Step")
            .description("Adds a bounded pause to a workflow. Keep this short to avoid holding execution resources for long periods.");

        builder.property(PropertyBuilder.builder()
            .type(Property.Type.Select)
            .name(PROP_PAUSE_DURATION)
            .title("Pause Duration")
            .description("How long to pause this job step. Maximum is 10 minutes.")
            .required(true)
            .defaultValue("10")
            .values(DURATION_VALUES)
            .labels(DURATION_LABELS)
            .renderingOption(StringRenderingConstants.GROUP_NAME, "Timer Configuration"));

        return builder.build();
    }

    @Override
    public void executeStep(final PluginStepContext context, final Map<String, Object> configuration) throws StepException {
        final ExecutionListener logger = context.getExecutionContext().getExecutionListener();

        final Object rawValue = configuration.get(PROP_PAUSE_DURATION);
        if (rawValue == null) {
            throw new StepException("Pause Duration is required.", StepFailureReason.ConfigurationFailure);
        }

        final String pauseValue = String.valueOf(rawValue).trim();
        if (!DURATION_VALUES.contains(pauseValue)) {
            throw new StepException(
                "Invalid Pause Duration value '" + pauseValue + "'. Allowed values are 10 seconds to 10 minutes.",
                StepFailureReason.ConfigurationFailure
            );
        }

        final int seconds;
        try {
            seconds = Integer.parseInt(pauseValue);
        } catch (NumberFormatException e) {
            throw new StepException("Pause Duration must be a number of seconds.", e, StepFailureReason.ConfigurationFailure);
        }

        if (seconds < 10 || seconds > 600) {
            throw new StepException("Pause Duration must be between 10 and 600 seconds.", StepFailureReason.ConfigurationFailure);
        }

        logger.log(2, "Timer Job Step: pausing workflow for " + seconds + " second(s)...");
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new StepException("Timer pause interrupted.", e, StepFailureReason.PluginFailed);
        }
        logger.log(2, "Timer Job Step: pause completed.");
    }
}
