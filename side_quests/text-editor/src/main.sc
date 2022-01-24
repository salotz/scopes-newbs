using import struct
using import String
using import Option
let char32 = (from (import UTF-8) let char32)


import C.string
import C.stdlib
import C.stdio

let C:unistd = (include "unistd.h")

# for ctype there are a bunch of macros so we need to make some
# wrappers around them so the compiler can read them properly
vvv bind C:ctype
include
    """"#include <ctype.h>
        typeof(iscntrl('a')) patched_iscntrl(char c) {
            return iscntrl(c);
        }

# locals
let termios = (import .termios)
using termios.macros

import .util

## Functions
fn read_char (input)

    C:unistd.extern.read
        C:unistd.define.STDIN_FILENO
        (& input)
        1

## Constants


fn main ()

    print "Welcome to skilo"
    print "Press any key"
    print "Press 'q' to quit"

    # turns rawmode on and shuts it down at the end of the surrounding function
    ~do_rawmode

    # set up the main loop
    local input : i8

    local terminate = false
    while (not terminate)

        # read input
        let read-result = (read_char input)

        # handle control characters
        let is_control_char = (C:ctype.extern.patched_iscntrl input)

        # echo the correct representation of the input to stdout
        if (is_control_char == 0)
            print (util.char_encode input)

        # elseif (is_control_char == 2)
        #     print (tostring input)


        # q for the terminate condition, do this first so we can always exit
        if (input == (char32 "q"))
            print  "terminating"
            terminate = true

    ;


main;

;
