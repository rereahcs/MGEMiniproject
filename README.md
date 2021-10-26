# MGEMiniproject #
## Idee ##
Für unser MGE Miniprojekt haben wir die Android App 'BudgeIt' entwickelt, mit der Ausgaben überwacht und ein Budget verwaltet werden kann.

Auf der Home-Screen sieht man die Ausgaben und Rest-Budget für den konfigurierten Zeitraum, das konfigurierte Gesamt-Budget und eine Auflistung der Ausgaben. Die Ausgaben können hierbei nach Kategorie gefiltert werden, wobei bei der Filterung auch die Ausgaben pro Kategorie angezeigt werden.

--> Screenshot HomeActivity
Hinweis: Screenshots mit scrcpy über 'AltGr + PrtSc' und dann Paste hier

Über das '+'-Icon auf dem Home-Screen können Ausgaben erfasst werden. Zudem kann man durch anklicken der einzelnen Ausgaben in der Liste auf dem Home-Screen bereits erfasste Ausgaben bearbeiten. Das Speichern einer Ausgabe ist nur verfügbar, wenn die notwendigen Felder ausgefüllt sind. Zudem wird das erfolgreiche Abspeichern mit einem Toast bestätigt. Das Löschen von Items ist nur für bereits erfasste Ausgaben verfügbar. Nach dem Löschen einer Ausgabe wird man auf den Home-Screen navigiert und es wird eine Snackbar angezeigt, die das Löschen bestätigt. In der Snackbar hat man zudem die Möglichkeit den Löschvorgang einer Ausgabe rückgängig zu machen.

--> Screenshot HomeActivity mit relevanten Stellen markiert
--> Screenshot neues Item erfassen
--> Screenshot bestehendes Item bearbeiten
--> Screenshot Toast
--> Screenshot Snackbar

Über das Menü am unteren Rand des Screens kann zu den Einstellungen, sowie zu der Statistik navigiert werden.

--> Screenshot mit Menü markiert

In den Einstellungen kann man das Budget, den Zeitraum, sowie die Währung einstellen. Die Einstellungen werden im Hintergrund über Preferences persistiert. Zudem müssen die Einstellungen nicht gespeichert werden - sie werden bei jeder Änderung direkt persistiert.

--> Screenshot Settings

Auf dem Screen der Statistik sieht man die Summe der Ausgaben pro Tag (visualisiert als Bar Chart). Der sichtbare Zeitraum auf der Statistik hängt hierbei von der Konfiguration in den Einstellungen ab.

--> Screenshot Stats

Die Ausgaben und die Kategorien werden mit Room persistiert.

--> Screenshot DB während App läuft

## Features ##
### Funktionalität ###
- Screen 1 (Home): Ausgaben und Restbudget
- Screen 2 (Settings): Einstellungen
- Screen 3 (Stats): Statistik
- Screen 4: (Item): CRUD Operationen sowie Anzeige für Ausgaben

### Persistenz ###
- Einstellungen werden als Preferences persistiert
- Ausgaben und Kategorien werden mit Room persistiert
- Offline-Fähigkeit ist gegeben, da die App nur lokale Funktionalität verwendet

### Unterschiedliche Styles ###
- Light Theme
- Dark Theme
Hinweis: Die Styles sind nicht direkt in der App konfigurierbar, sondern sie werden entsprechend der Einstellung im Betriebssystem (Dark Theme) verwendet.

### Integration von Drittkomponenten ###
- Material Design für das Design der App
- MPAndroidChart (https://github.com/PhilJay/MPAndroidChart) für die Statistik
- Room für die Persistenz von Ausgaben und Kategorien

### Erstellung eines App Widget (Home Screen) ###
Es wurde ein App Widget für die App erstellt:

--> Screenshot App Widget

### Verwendete, erwähnenswerte Widgets ###
- Snackbar
- Toast
- Spinner
- Floating Action Button
- Menü

### Android Jetpack Komponenten ###
- Material Design Komponenten (Snackbar, Spinner, FloatingActionButton, ButtomNavigationView, TextInputEditText
- Constraint Layout (alle Layouts)
- Recycler View (Ausgaben)
