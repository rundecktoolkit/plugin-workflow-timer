# Timer Job Step Configuration Reference

## Step Type

- `timer-job-step`

## Configuration Fields

### `pauseDuration`

- Type: `Select`
- Required: `true`
- Default: `10`
- Allowed values (seconds):
  - `10`, `20`, `30`, `45`
  - `60`, `90`, `120`, `180`, `240`, `300`, `360`, `420`, `480`, `540`, `600`

## Behavior

- Step validates configuration before sleeping.
- If the value is not in the allowed list, the step fails with `ConfigurationFailure`.
- If the worker thread is interrupted while sleeping, the step fails with `PluginFailed`.
