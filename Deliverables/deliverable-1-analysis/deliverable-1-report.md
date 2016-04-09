---
title: Deliverable 1 – Analyse der Legacy Anwendung
author:
    - Simon Blum
    - Johannes Dillmann
    - Julian Keppel
    - Daniel Kraus
documentclass: scrartcl
urlcolor: blue
...

Die vorliegende Dokumentation befasst sich mit der bereitgestellten Legacy-Anwendung des Webshops. Es wird sowohl die Struktur und Architektur der Anwendung, sowie das Verhalten und die Kommunikation zwischen den Komponenten beleuchtet.

# Strukturelle Analyse #
## Architektur der Anwendung ##
Die gesamte Anwendung ist als monolitische Struktur in einem gemeinsamen Archiv gebündelt. Sie ist in mehrere Schichten eingeteilt, welche jeweils in Package-Ebene voneinander getrennt sind. Der View-Layer wird durch JSP-Files und dem Struts2-Framework implementiert. Von hier aus werden die Requests vom Browser an die jeweiligen Controller-Instanzen geleitet. Die Controller erzeugen die jeweils benötigten Manager-Instanzen aus dem BusinessLogic-Layer. Von hier aus gelangen die aufrufe zu den Datenzugriffsobjekten (DAO), welche die Verbindung zur Datenbank durch das JPA-Framework Hibernate kapseln. 

Die folgende Abbildung zeigt die Architektur und alle Hauptkomponenten im Überblick:

![Image](diagrams/ComponentDiagram.png?raw=true)

## Klassenstruktur ##
Die Struktur der Klassen bildet im wesentlichen das MVC (Model View Controller) Architekturmuster ab. Eine Ausnahme stellen dabei jedoch die Views dar, da diese als JSPs (Java Server Pages) vorliegen. Die Controller liegen im gleichnamigen Paket und stellen, im Kontext von Struts2, jeweils Actions dar. So existiert beispielsweise für das anlegen einer Kategorie die Klasse *AddCategoryAction* oder für das Abrufen von Details zu einem Produkt die Klasse *ProductDetailsAction*. Nach diesem Schema sind alle Aktionen auf einen bestimmten Controller abbildbar. 

Der Zugriff auf die Datenbank beziehungsweise die Model Schicht erfolgt über die Klassen im Paket *model*. Alle notwendigen Entitäten wie Benutzer, Kategorie oder Produkte sind als eigenständige Klassen gekapselt. Die Interaktion mit diesen Elementen geschieht, auf Ebene der Geschäftslogik, über diverse *Manager* Klassen. Diese Logik ist wiederum in ein spezifisches Interface, sowie eine zugehörige Implementierung geteilt. Für Produkte existiert dabei das Interface *ProductManager* und die zugehörige Implementierung *ProductManagerImpl* welche die Zugriffe entsprechend kapselt. Der Zugriff auf die Datenbank erfolgt über, jeweils zu den Elementen passenden, DAO Klassen. Die Klasse *GenericHibernateDAO* implementiert selbst das Interface *IGenericDAO* und stellt eine Basiskasse dar, welche durch entsprechende Spezialisierungen erweitert wird. Als Beispiel sei hierbei die Klasse *CategoryDAO* genannt, welche für den Zugriff auf *Category* Objekte aus der Datenbank zuständig ist. 

## Datenmodell ##
TODO

# Analyse des Verhaltens #
## Formale Spezifikation ##
TODO

## Ablauf eines Beispiel-Workflows ##
Anhand der Workflows "Produkt hinzufügen" wird exemplarisch die Kommunikation sowie das Verhalten der einzelnen Komponenten gezeigt. Es fällt auf, dass offensichtlich bei jeder Anfrage ein neuer ProductManager, CategoryManager sowie die dazugehörigen Datenzugriffsobjekte erstellt werden. 

Im folgenden Sequenzdiagramm ist der beispielhafte Workflow innerhalb der gesamten Anwendung abgebildet:

![Image](diagrams/SequenceDiagram_DddProduct.png?raw=true)
