# CmdFileManipulator

Программа для обработки текстовых файлов и сортировки данных по типам (целые числа, вещественные числа, строки).

- Java version: 24.0.1
- Maven version: 3.9.10
- Apache Commons CLI: 1.9.0
- Apache Maven JAR Plugin: 3.3.0
- Apache Maven Shade Plugin: 3.5.0

## Установка
1. Убедитесь, что у вас установлена Java, если нет то установите её по [ссылке](https://www.oracle.com/java/technologies/downloads/).
2. Убедитесь, что у вас установлен Maven, если нет то установите его по [ссылке](https://maven.apache.org/install.html).
3. Клонируй репозиторий:
   ```
   git clone https://github.com/KiseSaiyajin/cmdFileManipulator.git
   cd cmdFileManipulator
   ```
4. Скомпилируй проект:
   ```
   mvn clean package
   ```
## Зависимости
- Apache Commons CLI (встроена через Maven).
```xml
 
  <dependencies>
        <!-- Apache Commons CLI -->
        <dependency>
            <groupId>commons-cli</groupId>
            <artifactId>commons-cli</artifactId>
            <version>1.9.0</version>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <!-- Плагин для сборки JAR-файла -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.3.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>testProject.Main</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
            <!-- Плагин для включения зависимостей в JAR -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.5.0</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>testProject.Main</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
    
```
## Использование
Запусти программу с помощью:
```
java -jar .\target\cmdFileManipulator-1.0-SNAPSHOT.jar [опции] in1.txt in2.txt
```
  ### Опции
- `-o <путь>`: Путь для выходных файлов (по умолчанию текущая директория).
- `-p <префикс>`: Префикс для имён выходных файлов (по умолчанию без префикса).
- `-s`: Вывод краткой статистики.
- `-f`: Вывод полной статистики (включает максимум, минимум, сумму, среднее).
- `-a`: Режим добавления данных в существующие файлы (по умолчанию перезапись).

### Пример
```
java -jar .\target\cmdFileManipulator-1.0-SNAPSHOT.jar in1.txt in2.txt -s -o "C:\Users\MyPc\Downloads"
```
### Особенности
Использовал библиотеку Apache Commons CLI. С ней работа с коммандной строкой стала проще.
## Автор
Рафаэль Толстомиров

## Примечания
- Убедись, что входной файл существует.
- Выходные файлы будут созданы как `<префикс>int.txt`, `<префикс>float.txt`, `<префикс>string.txt`.
