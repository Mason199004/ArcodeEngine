./gradlew shadowjar
cp build/libs/ArcodeEngine-1.0-SNAPSHOT-all.jar
java -cp ArcodeEngine-1.0-SNAPSHOT-all.jar ArcodeEngine.Cabinet.Cabinet
