# Java-Project

## Before you start

### Run config

In your IDE `settings->apperance & behavior -> path variables` set `PATH_TO_FX` as an absolute path pointing
to `javafx/lib` in this project.
Run config `Run->Edit Configuartions -> modify options -> add VM options`
input `--module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml,javafx.graphics`

### Email config

In `scr/config` create a file named `config.json`.
The contents of this file may look as follows:

```json
{
  "email": "dssckwabot@gmail.com",
  "password": "REDACTED",
  "sender": "Delta Szwadron Super Cool Komando Wilków Alfa Bot"
}
```
