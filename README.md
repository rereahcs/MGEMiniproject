# MGEMiniproject #
## Idee ##
Für unser MGE Miniprojekt haben wir die Android App 'BudgeIt' entwickelt, mit der Ausgaben überwacht und ein Budget verwaltet werden kann.

Auf dem Home-Screen sieht man die Ausgaben und das Rest-Budget, das Gesamt-Budget und eine Auflistung der Ausgaben für den in den Einstellungen gewählten Zeitraum. Die Ausgaben können hierbei nach Kategorie gefiltert werden, wobei bei der Filterung auch das Budget pro Kategorie angezeigt wird.

![grafik](https://user-images.githubusercontent.com/91136383/138932890-94ea2cff-771b-441a-8b3f-605779451a28.png)

Über das '+'-Icon auf dem Home-Screen können Ausgaben erfasst werden. Durch Anklicken der einzelnen Ausgaben in der Liste auf dem Home-Screen können erfasste Ausgaben bearbeitet werden. Das Speichern einer Ausgabe ist nur verfügbar, wenn die notwendigen Felder ausgefüllt sind. Das Löschen von Items ist nur für bereits erfasste Ausgaben verfügbar.

![grafik](https://user-images.githubusercontent.com/91136383/138933547-f9647564-260f-4d74-8148-4cf5742b295e.png)
![grafik](https://user-images.githubusercontent.com/91136383/138933690-38bcca67-0232-4d57-9a39-8afb300cd7cf.png)
![grafik](https://user-images.githubusercontent.com/91136383/138933468-f29cbea0-3cdd-47ec-afd1-860a2288c449.png)

Das erfolgreiche Abspeichern einer Ausgabe wird mit einem Toast bestätigt.

![grafik](https://user-images.githubusercontent.com/91136383/138933906-f5b7e2d5-1d54-4fc7-a232-17479a216ad7.png)

Nach dem Löschen einer Ausgabe wird man auf den Home-Screen navigiert und es wird eine Snackbar angezeigt, die das Löschen bestätigt. In der Snackbar hat man zudem die Möglichkeit den Löschvorgang rückgängig zu machen. Eine Ausgabe kann sowohl auf dem Item-Screen über den Button 'Delete', sowie auch mit einem Rechts-Swipe über der Ausgabe im Home-Screen gelöscht werden.

![grafik](https://user-images.githubusercontent.com/91136383/138934006-aa8f8966-cb73-4949-9f63-02c80abe9d34.png)

Über das Menü am unteren Rand des Screens kann zu den Einstellungen, sowie zu der Statistik navigiert werden.

![grafik](https://user-images.githubusercontent.com/91136383/138934140-e120a5b9-55f6-496b-ad68-aeed8881083a.png)

In den Einstellungen kann man das Budget, den Zeitraum, sowie die Währung einstellen. Die Einstellungen werden im Hintergrund über Preferences persistiert. Zudem müssen die Einstellungen nicht gespeichert werden - sie werden bei jeder Änderung direkt persistiert.

![grafik](https://user-images.githubusercontent.com/91136383/138934715-cc613244-3f3e-4522-940b-15d8469b772d.png)

Auf dem Screen der Statistik sieht man die Summe der Ausgaben pro Tag (visualisiert als Bar Chart). Der sichtbare Zeitraum auf der Statistik hängt hierbei von der Konfiguration in den Einstellungen ab.

![grafik](https://user-images.githubusercontent.com/91136383/138936624-1ea871e9-3325-4407-bdd2-10af0f399461.png)
![grafik](https://user-images.githubusercontent.com/91136383/138934317-c910ac1d-d91e-4db9-8e70-7310b30f02b7.png)

Die Ausgaben und die Kategorien werden mit Room persistiert.

![grafik](https://user-images.githubusercontent.com/91136383/138934467-162017d0-ea9b-425f-a15a-6e93086a0557.png)

## Features ##
### Funktionalität ###
- Screen 1 (Home): Ausgaben und Rest-Budget
- Screen 2 (Settings): Einstellungen
- Screen 3 (Stats): Statistik
- Screen 4: (Item): CRUD Operationen sowie Anzeige von Ausgaben
- Undo einer Lösch-Operation für Ausgaben

### Persistenz ###
- Einstellungen werden als Preferences persistiert
- Ausgaben und Kategorien werden mit Room persistiert
- Offline-Fähigkeit ist gegeben, da die App nur lokale Funktionalität verwendet

### Unterschiedliche Styles ###
- Light Theme
- Dark Theme

Hinweis: Die Styles sind nicht direkt in der App konfigurierbar, sondern sie werden entsprechend der Einstellung im Betriebssystem (Dark Theme) verwendet.

![grafik](https://user-images.githubusercontent.com/91136383/138934949-cc9f72a8-9326-42af-a38e-122e22dea0c0.png)
![grafik](https://user-images.githubusercontent.com/91136383/138934965-f214d1a5-6239-4dd1-92ee-8534db9d5e1b.png)


### Integration von Drittkomponenten ###
- Material Design für das Design der App
- MPAndroidChart (https://github.com/PhilJay/MPAndroidChart) für die Statistik
- Room für die Persistenz von Ausgaben und Kategorien

### Erstellung eines App Widget (Home Screen) ###
Es wurde ein App Widget für die App erstellt:

![grafik](https://user-images.githubusercontent.com/91136383/138935204-a91c10ed-f086-410f-9e95-ec5caf48eb38.png)

### Verwendete, erwähnenswerte Widgets ###
- Snackbar
- Toast
- Spinner
- Floating Action Button
- BottomNavigationView - Menü

### Android Jetpack Komponenten ###
- Material Design Komponenten (Snackbar, Spinner, FloatingActionButton, ButtomNavigationView, TextInputEditText)
- Constraint Layout (alle Layouts)
- Recycler View (Ausgaben)
