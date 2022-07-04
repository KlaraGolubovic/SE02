# Für selektiertes Szenario aus dem Utility Tree eine zugehörige Architekturentscheidung nach ATAM beschreiben

### Szenario : 

Ein Benutzer registriert sich

### Attribute

Usability

### Architectural Tactics (Decisions) :  

Die Daten, welche bei der Registrierung eingetragen wurden sind in der Datenbank gespeichert und es wurde eine E-Mail zur Bestätigung an den Benutzer geschickt.

### Implications for Implementation

Verwendung eines Frameworks wie z.B. Vaadin 


### Szenario :

Anstieg Zugriffszahlen

### Attribute

Skalierbarkeit

### Architectural Tactics (Decisions) :  

Verlagerung der komplexen Validierungen in Richtung des Browsers
(geringes Risiko, da es in gängigen Web-Frameworks implementiert ist)

### Reasoning

Durch die Technologie JavaScript wird die Serverlast bei hohen Zugriffszahlen in Richtung der Clients (Browser) verlagert.

Eine Erhöhung der Ressourcen (z.B. CPU und RAM) ist sowohl server- als auch
clientseitig nicht notwendig, was die Skalierbarkeit garantiert fördert

### Implications for Implementation
Verwendung eines Frameworks wie z.B. Vaadin

