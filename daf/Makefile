CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

OBJS =		daf.o

LIBS =

TARGET =	daf.exe

$(TARGET):	$(OBJS)
	$(CXX) -o $(TARGET) $(OBJS) $(LIBS)

all:	$(TARGET)

clean:
	rm -f $(OBJS) $(TARGET)
