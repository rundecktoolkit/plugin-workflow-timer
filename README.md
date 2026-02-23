<h1 align="center">Rundeck Timer Job Step Plugin</h1>

<p align="center">
  <strong>Simple bounded pause step for Rundeck workflows</strong>
</p>

## Overview

This plugin adds a **Timer Job Step** (`timer-job-step`) for Rundeck.

It pauses workflow execution for a selected duration from a predefined list.

### Guardrails

- Minimum pause: **10 seconds**
- Maximum pause: **10 minutes** (600 seconds)
- Any value outside this range is rejected

This keeps pause behavior predictable and avoids long-running execution holds.

## Compatibility

| Platform | Version |
| --- | --- |
| Rundeck Community | 5.x |
| Runbook Automation (Self-Hosted) | 5.x |

## Build

```bash
cd plugin-workflow-timer
./gradlew clean jar
```

If you do not have a Gradle wrapper in this repo, use your local Gradle install:

```bash
gradle clean jar
```

Artifact:

- `build/libs/timer-job-step-1.0.1.jar`

## Install

```bash
cp build/libs/timer-job-step-1.0.1.jar $RDECK_BASE/libext/
# restart Rundeck or reload plugins
```

## Job Step Configuration

| Parameter | Required | Default | Values |
| --- | --- | --- | --- |
| `pauseDuration` | Yes | `10` | `10,20,30,45,60,90,120,180,240,300,360,420,480,540,600` |

UI labels are shown as friendly durations (for example `1 minute`, `5 minutes`, `10 minutes`).

## Example Job

See [examples/timer-step-example.yaml](examples/timer-step-example.yaml).

## License

MIT
