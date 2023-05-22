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
    while (true){
        const Mass m = hx.weight(3);

        cout    << "\x1B[2J\x1B[H"
                << "\t" << m.getValue() << '\n'
                << "\t" << m.toString(Mass::Unit::UG) << '\n'
                << "\t" << m.toString(Mass::Unit::MG) << '\n'
                << "\t" << m.toString(Mass::Unit::G) << '\n'
                << "\t" << m.toString(Mass::Unit::KG) << '\n'
                << "\t" << m.toString(Mass::Unit::TON) << '\n'
                << "\t" << m.toString(Mass::Unit::IMP_TON) << '\n'
                << "\t" << m.toString(Mass::Unit::US_TON) << '\n'
                << "\t" << m.toString(Mass::Unit::ST) << '\n'
                << "\t" << m.toString(Mass::Unit::LB) << '\n'
                << "\t" << m.toString(Mass::Unit::OZ) << '\n'
                << endl
        ;
    }

    return EXIT_SUCCESS;

}