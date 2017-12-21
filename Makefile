.PHONY: all run clean
all: MapData.class MapGame.class MapGameController.class MoveChara.class  

%.class: %.java
	javac $^

run: all
	java MapGame

clean:
	-rm -f *.class
