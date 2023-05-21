#include <cstdlib>
#include <iostream>
#include <string>
#include "../include/common.h"

int main(int argc, char** argv) {

    using namespace std;
    using namespace HX711;

    const char* const err = "Usage: [DATA PIN] [CLOCK PIN] [REFERENCE UNIT] [OFFSET]";

    if(argc != 5) {
        cerr << err << endl;
        return EXIT_FAILURE;
    }

    const int dataPin = stoi(argv[1]);
    const int clockPin = stoi(argv[2]);
    const int refUnit = stoi(argv[3]);
    const int offset = stoi(argv[4]);

    SimpleHX711 hx(dataPin, clockPin, refUnit, offset);
    const Mass m = hx.weight(3);
    if(m.getValue() <= 5 && m.getValue() >= - 5){
        std::cout << "Test: ONEWEIGHT -> PASSED" << std::endl;
        return EXIT_SUCCESS;
 ;
    }
    else{
        std::cout << "Test: ONEWEIGHT -> FAILED" << std::endl;
        return EXIT_FAILURE;
    }

}