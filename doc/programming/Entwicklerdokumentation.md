# View-Components

# Zentrieren einer Sicht

Innerhalb eines Vaadin-Divs kann man mit folgendem Code sehr <br>
simpel ein Vertical Layout zentrieren:

```Java
this.setWidth("100%");
final VerticalLayout layout = new VerticalLayout();
layout.setAlignItems(Alignment.CENTER);
```
Die Breite des Divs füllt also das gesamte Container-Element.</br>
Wenn die Breite lediglich 80% betragen würde, würde das Div
standardmäßig auf die linken 80% verteilt werden.

# Favicon
Die Favicon Dateien wurden mithilfe von [Diesem Tool in Figma](https://www.figma.com/community/file/914233657397286062)
erstellt. </br>
Das Favicon ist bei Webseiten ein Standard-Icon, welches in der Tab-Leiste angezeigt wird.
Dieses Icon wird auch als Browser-Icon und bei mobilen Geräten als Icon in der App verwendet.

# Einstellungen für Git
In der Konsole kann man festlegen, dass nur fast forward verwendet werden soll:
```console
git config --global pull.ff only
```
Dabei werden simple Merges automatisch durchgeführt bzw. die Commits sofern
kompatibel einfach "gezogen".

# Starten der Anwendung
1. Mit Maven den AcademicFlow Lifecycle ``clean`` ausführen
2. Mit Maven den AcademicFlow Lifecycle ``install`` ausführen
3. Innerhalb der IDE die Methode ``main()`` ausführen

# Aufbau der POM.xml
- Die POM.xml ist eine XML-Datei, die die Build-Konfiguration für ein Projekt
  spezifiziert.
- Oben innerhalb der Sektion ``<properties>`` werden die Versionsnummern der
  benötigten Pakete festgelegt.
  - Hier können auch andere Properties (Konfigurationen) hinzugefügt werden.
# Pipelines
  - Pipelines sind eine Art von Workflows, die eine Reihe von Aktionen ausführen.
## Vorgegebene Pipeline
- Jenkins auf
  [SEPP Jenkins](https://sepp-jenkins.inf.h-brs.de/job/Alda-innen/) ist der sogenannte Runner.
- SonarQube auf
  [SonarQube](https://sepp-sonar.inf.h-brs.de/dashboard?id=Alda-innen)
stellt die Qualität des Source-Codes sicher und zeigt diverse Metriken.
- Sofern die Pipeline erfolgreich abgeschlossen wurde, wird ein Tomcat deployment des in der Pipeline
  erstellten Warfiles auf dem [CD Host](http://sepp-test.inf.h-brs.de:8080/AldaInnen-0.0.1-SNAPSHOT/) gestartet.


# Finden von Elementen

```java
component.getChildren().forEach(child ->{
      log.info(String.valueOf(child));
    });
```