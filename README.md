# Auto-sprint

Auto-sprint fuer Minecraft 1.21.11 (Fabric). Der Spieler sprintet automatisch, solange die Mod aktiv ist und genug Hunger vorhanden ist.

## Funktionen
- Hotkey zum Ein/Aus (Standard: Y)
- Statusanzeige im Action Bar beim Umschalten (AN/AUS)
- Zustand bleibt nach Neustart erhalten
- Sprint nur bei Hunger-Level >= 7

## Voraussetzungen
- Fabric Loader
- Fabric API
- Java 21

## Konfiguration
Die Datei wird im Fabric-Config-Ordner gespeichert:

```
config/auto-sprint.json
```

## Bauen

```
./gradlew build
```

## Hotkey aendern
In Minecraft unter Steuerung nach "Auto-sprint" suchen und den Toggle-Key anpassen.
