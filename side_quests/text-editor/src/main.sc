using import struct
using import String
using import UTF-8

import C.string
import C.stdlib

# TODO: make this easier
# fn import_c_stdlib (header-name)

#     let header = (include header-name)

#     ..
#         header.extern
#         header.typedef
#         header.define
#         header.const
#         header.struct

# let C.termios = (import_c_stdlib "termios.h")
# let C.termios = (import_c_stdlib "unistd.h")


let C:termios = (include "termios.h")
let C:unistd = (include "unistd.h")

# the original termios struct so we can revert, definition
global orig_termios : C:termios.struct.termios

fn disable_term_rawmode ()

    print "Disabling Raw Mode"

    C:termios.extern.tcsetattr
        C:unistd.define.STDIN_FILENO
        C:termios.define.TCSAFLUSH
        (& orig_termios)


fn enable_term_rawmode ()

    print "Enabling Raw Mode"

    # collect the original termios structure
    C:termios.extern.tcgetattr
        C:unistd.define.STDIN_FILENO
        (& orig_termios)

    # NOTE: atexit doesn't seem to work since it is loaded weirdly

    # set the atexit handler to disable rawmode
    # (C.stdlib.atexit disable_term_rawmode)

    # start the rawmode termios with the original
    local raw_termios = orig_termios

    # then set the flags in raw_termios
    C:termios.extern.tcgetattr
        C:unistd.define.STDIN_FILENO
        (& raw_termios)

    # all the flags you want to set
    raw_termios.c_lflag =
        ~
            |
                (C:termios.define.ECHO as u32)
                (C:termios.define.ICANON as u32)

    C:termios.extern.tcsetattr
        C:unistd.define.STDIN_FILENO
        C:termios.define.TCSAFLUSH
        (& raw_termios)



fn read_char (input)

    C:unistd.extern.read
        C:unistd.define.STDIN_FILENO
        (& input)
        1

# fn rawstring_to_String (rstr)
#     (String rstr (C.string.strlen rstr))

## Constants


fn main ()

    enable_term_rawmode;
    defer disable_term_rawmode;

    local input : i8

    local read-result = (read_char input)

    local terminate = false
    while (
        (read-result == 1) and
        (not terminate)
    )

        if (input == (char32 "q"))
            print "terminating"
            terminate = true

        ### Read input for next iteration
        if (not terminate)
            read-result = (read_char input)


    # uses the atexit handler to make sure this works
    # disable_term_rawmode;

main;
