# Oups
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*

## BONUS – Qualité de code dans la pipeline CI (niveau avancé)
### Objectifs du bonus
* Comprendre ce qu’on appelle la qualité de code
* Découvrir des outils utilisés en entreprise pour :
    * imposer un style de code cohérent
    * détecter des bugs potentiels
* Ajouter un troisième job dans la pipeline CI
* Observer l’impact d’une règle de qualité sur la livraison

### Contexte
Jusqu’ici, la pipeline vérifie :
* que le code fonctionne (tests unitaires)
* que l’ensemble fonctionne (tests d’intégration)

En entreprise, ce n’est pas suffisant.

Un code peut :
* passer les tests
* mais être difficile à maintenir
* ou contenir des bugs subtils

C’est là qu’interviennent les outils de qualité statique.

### Étape 1 – Ajouter les outils de qualité au projet
Ajoutez dans le pom.xml les plugins :
* maven-checkstyle-plugin
* spotbugs-maven-plugin
```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-checkstyle-plugin</artifactId>
                <version>3.4.0</version>
                <configuration>
                    <configLocation>checkstyle.xml</configLocation>
                    <encoding>UTF-8</encoding>
                    <consoleOutput>true</consoleOutput>
                    <failsOnError>true</failsOnError>
                    <includeTestSourceDirectory>false</includeTestSourceDirectory>
                </configuration>
                <executions>
                    <execution>
                        <id>checkstyle</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>check</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <groupId>com.github.spotbugs</groupId>
                <artifactId>spotbugs-maven-plugin</artifactId>
                <version>4.8.6.4</version>
                <configuration>
                    <effort>Max</effort>
                    <threshold>Low</threshold>
                    <failOnError>true</failOnError>
                </configuration>
                <executions>
                    <execution>
                        <id>spotbugs</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>check</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

Ces plugins doivent :
* s’exécuter lors de la phase verify
* faire échouer le build en cas de problème

### Étape 2 – Ajouter les règles Checkstyle
Créez un fichier checkstyle.xml à la racine du projet.
```xml
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
        "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
        "https://checkstyle.org/dtds/configuration_1_3.dtd">

<module name="Checker">
    <module name="TreeWalker">

        <!-- Indentation / lisibilité -->
        <module name="NeedBraces"/>
        <module name="AvoidNestedBlocks"/>
        <module name="EmptyBlock">
            <property name="option" value="text"/>
        </module>

        <!-- Nommage basique -->
        <module name="TypeName"/>
        <module name="MethodName">
            <property name="format" value="^[a-z][a-zA-Z0-9_]*$"/>
        </module>
        <module name="ParameterName"/>
        <module name="LocalVariableName"/>

        <!-- Bonnes pratiques -->
        <!-- <module name="FinalParameters"/> -->
        <module name="UnusedImports"/>
        <module name="AvoidStarImport"/>
    </module>

    <!-- Longueur ligne (souple) -->
    <module name="LineLength">
        <property name="max" value="140"/>
        <property name="ignorePattern" value="^package|^import|http://|https://"/>
    </module>
</module>
```

Ce fichier définit :
* des règles de nommage
* des règles de lisibilité
* des règles de bonnes pratiques

*L’objectif n’est pas de “punir”, mais de standardiser le code.*

### Étape 3 – Tester la qualité en local
Avant la CI, vérifiez localement :
```bash
mvn verify -DskipTests
```
Résultat attendu
* le build échoue si une règle de qualité n’est pas respectée
* le build passe si le code est propre

### Étape 4 – Ajouter un job quality dans la pipeline CI
Modifiez votre pipeline CI pour ajouter un troisième job :
1. unit-tests
2. quality
3. integration-tests
   Règles de dépendance
* quality ne s’exécute que si les tests unitaires passent
* integration-tests ne s’exécute que si quality réussit

*La pipeline devient une chaîne de validation progressive.*