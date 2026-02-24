<h1 align="center">Rundeck Timer Job Step Plugin</h1>

<p align="center">
  <strong>Add a bounded timer pause to Rundeck automation workflows</strong>
</p>

<p align="center">
  <a href="#installation">Installation</a> •
  <a href="#configuration">Configuration</a> •
  <a href="#usage">Usage</a> •
  <a href="#examples">Examples</a> •
  <a href="#support">Support</a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Rundeck-Community-5C9E3D?logo=rundeck&logoColor=white" alt="Rundeck Community"/>
  <img src="https://img.shields.io/badge/Runbook_Automation-Self_Hosted-0F1E57?logo=pagerduty&logoColor=white" alt="Runbook Automation Self-Hosted"/>
  <img src="https://img.shields.io/badge/License-MIT-blue" alt="MIT License"/>
</p>

---

## Overview

This plugin adds a **Timer Job Step** (`timer-job-step`) to Rundeck workflows.

It pauses execution for a fixed selected duration.

**Guardrails:**
- Minimum pause: **10 seconds**
- Maximum pause: **10 minutes**
- Values outside the supported list are rejected

## Compatibility

| Platform | Version |
|----------|---------|
| Rundeck Community | 4.x, 5.x |
| Runbook Automation (Self-Hosted) | 4.x, 5.x |

## Installation

Download the latest JAR from [Releases](https://github.com/rundecktoolkit/plugin-workflow-timer/releases) and install via the Rundeck UI:

1. Navigate to **System Menu** -> **Plugins** -> **Upload Plugin**
2. Select the downloaded JAR file
3. The plugin is available immediately (restart/reload may be required in some deployments)

## Configuration

### Plugin Parameters

| Parameter | Required | Default | Description |
|-----------|:--------:|---------|-------------|
| `pauseDuration` | ✓ | `10` | Pause duration in seconds (`10,20,30,45,60,90,120,180,240,300,360,420,480,540,600`) |

## Usage

1. Create or edit a Rundeck job
2. Add workflow step -> select **Timer Job Step**
3. Choose `pauseDuration`
4. Save and run

The step logs when the pause starts and when it completes.

## Examples

Import the ready-to-use [example job](./examples/timer-step-example.yaml) into your project.

## Build from Source

### Requirements

- Java 11+
- Gradle 7.x (wrapper included)

### Commands

```bash
./gradlew clean jar
```

Output: `build/libs/timer-job-step-1.0.1.jar`

## Support

- **Issues:** [GitHub Issues](https://github.com/rundecktoolkit/plugin-workflow-timer/issues)
- **Pull Requests:** Contributions welcome

> **Disclaimer:** This plugin is provided "as is" without warranty of any kind, express or implied. Use at your own risk.

## License

MIT License - see [LICENSE](./LICENSE) for details.

---

<p align="center">
  <sub>Part of <a href="https://github.com/rundecktoolkit">rundecktoolkit</a> — Community plugins for Rundeck</sub>
</p>
